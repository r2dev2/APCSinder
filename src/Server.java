import java.io.IOException;
import java.io.Serializable;
import java.io.OutputStream;
import java.io.*;
import java.util.stream.*;
import java.util.*;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpPrincipal;
import com.sun.net.httpserver.Authenticator;

public class Server
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("Starting APCSinder server");
        var db = new UserDB("users.db");
        var mdb = new MessageDB("messages.db");

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

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
            if (!db.authenticate(msg.sender, token)) {
                respondSingle(t, "Not authenticated");
                return;
            }
            mdb.add(msg);
            respondSingle(t, "success");
        });

        server.createContext("/messages", t -> {
            var token = getToken(t);
            var username = db.getUsername(token);
            if (username == null) {
                respondSingle(t, "Not authenticated");
                return;
            }
            var record = db.getUser(username);
            var messages = new HashMap<String, ArrayList<Message>>();
            for (String other: record.matches) {
                messages.put(other, mdb.get(new Match(username, other)));
            }
            respondSingle(t, messages);
        });

        server.createContext("/matches", t -> {
            var token = getToken(t);
            var username = db.getUsername(token);
            if (username == null) {
                respondSingle(t, "Not authenticated");
                return;
            }
            var record = db.getUser(username);
            var matches = new ArrayList<Match>();
            for (String other: record.matches) {
                matches.add(new Match(username, other));
            }
            respondSingle(t, matches);
        });

        server.setExecutor(null);
        server.start();
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

    private static String getRequestBody(HttpExchange t)
    {
        return new BufferedReader(new InputStreamReader(t.getRequestBody()))
            .lines()
            .collect(Collectors.joining("\n"));
    }

    private static <T> T getRequestBody(HttpExchange t, T defaultObj) throws IOException
    {
        String body = getRequestBody(t);
        T obj = Serializer.deserialize(body);
        if (obj == null) {
            return defaultObj;
        }
        return obj;
    }

    private static String getToken(HttpExchange t)
    {
        // Throw npe when no token
        return t.getRequestHeaders().getOrDefault("Token", null).get(0);
    }
}
