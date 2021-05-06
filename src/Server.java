import java.io.IOException;
import java.io.Serializable;
import java.io.OutputStream;
import java.io.*;
import java.util.stream.*;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

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
}
