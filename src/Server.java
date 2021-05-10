import java.io.*;
import java.util.stream.*;
import java.util.*;
import java.util.function.*;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;

public class Server
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("Starting APCSinder server at http://localhost:8000.");
        var db = new UserDB("users.db");
        var mdb = new MessageDB("messages.db");

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/ping", t -> respondSingle(t, "pong"));

        server.createContext("/login", t -> {
            LoginAttempt login = getRequestBody(t, null);
            respondSingle(t, db.login(login.username, login.password));
        });

        server.createContext("/createuser", t -> {
            UserCreationAttempt attempt = getRequestBody(t, null);
            respondSingle(t, db.createUser(attempt) ? "success" : "fail");
        });

        server.createContext("/message", t -> {
            Message msg = getRequestBody(t, null);
            var token = getToken(t);
            denyIf(!db.authenticate(msg.sender, token), t);
            mdb.add(msg);
            respondSingle(t, "success");
        });

        server.createContext("/messages", handleGet(db, username ->
                    getMessages(username, db.getMatches(username), mdb)));

        server.createContext("/matches", handleGet(db, username ->
                    db.getUser(username).getMatches()));

        server.createContext("/potentialmatches", handleGet(db, db::getPotentialMatches));

        server.createContext("/acceptmatch", handleAcceptReject(true, db));
        server.createContext("/rejectmatch", handleAcceptReject(false, db));

        server.createContext("/listenmessages", createEventStream(mdb::subscribe, db));
        server.createContext("/listenpotentialmatches",
                createEventStream(db::subscribePotentialMatches, db));
        server.createContext("/listenmatches", createEventStream(db::subscribe, db));
        
        server.setExecutor(null);
        server.start();
    }

    private static <T extends Serializable> HttpHandler createEventStream(
            BiConsumer<String, BaseDB.Subscriber<T>> subscribe, UserDB db)
    {
        return t -> {
            var username = db.getUsername(getToken(t));
            denyIfNull(username, t);
            t.sendResponseHeaders(200, 0);
            subscribe.accept(username, msg -> {
                try {
                    respondOne(t, msg);
                    return true;
                }
                catch (IOException e) {
                    return false;
                }
            });
        };
    }

    private static HttpHandler handleAcceptReject(boolean accept, UserDB db)
    {
        return t -> {
            Match match = getRequestBody(t, null);
            var username = db.getUsername(getToken(t));
            denyIf(!match.hasUser(username), t);
            if (accept)
                db.accept(username, match.otherUser(username));
            else
                db.reject(username, match.otherUser(username));
            respondSingle(t, "success");
        };
    }

    private static HttpHandler handleGet(
            UserDB db, Function<String, Serializable> resourceGetter)
    {
        return t -> {
            var username = db.getUsername(getToken(t));
            denyIfNull(username, t);
            respondSingle(t, resourceGetter.apply(username));
        };
    }

    private static HashMap<String, ArrayList<Message>>
        getMessages(String username, Iterable<String> matches, MessageDB mdb)
    {
        var messages = new HashMap<String, ArrayList<Message>>();
        for (var other: matches) {
            messages.put(other, mdb.get(new Match(username, other)));
        }
        return messages;
    }

    private static void denyIf(boolean condition, HttpExchange t) throws IOException
    {
        if (!condition) return;
        respondSingle(t, "Not authenticated");
        throw new SecurityException("Not authenticated");
    }

    private static void denyIfNull(Object obj, HttpExchange t) throws IOException
    {
        denyIf(obj == null, t);
    }

    private static void respondSingle(HttpExchange t, String response) throws IOException
    {
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static void respondSingle(HttpExchange t, Serializable obj) throws IOException
    {
        respondSingle(t, Serializer.serialize(obj));
    }

    private static void respondOne(HttpExchange t, String response) throws IOException
    {
        var os = t.getResponseBody();
        os.write(response.getBytes());
        os.flush();
    }

    private static void respondOne(HttpExchange t, Serializable obj) throws IOException
    {
        respondOne(t, Serializer.serialize(obj) + "\n");
    }

    private static void close(HttpExchange t) throws IOException
    {
        t.getResponseBody().close();
    }

    private static String getRequestBody(HttpExchange t)
    {
        return new BufferedReader(new InputStreamReader(t.getRequestBody()))
            .lines()
            .collect(Collectors.joining("\n"));
    }

    private static <T extends Serializable> T getRequestBody(
            HttpExchange t, T defaultObj) throws IOException
    {
        String body = getRequestBody(t);
        T obj = Serializer.deserialize(body);
        return obj != null ? obj : defaultObj;
    }

    private static String getToken(HttpExchange t)
    {
        // Throw npe when no token
        return t.getRequestHeaders().getOrDefault("Token", null).get(0);
    }
}
