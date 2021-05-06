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
        // HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        // server.createContext("/test", t -> {
        //     respondSingle(t, "Hello there\n");
        // });
        // server.createContext("/login", t -> {
        //     LoginAttempt login = getRequestBody(t, null);
        //     System.out.println(login);
        //     respondSingle(t, new LoginResult(true));
        // });
        // server.setExecutor(null);
        // server.start();
        var mdb = new MessageDB(null);
        mdb.subscribe("bruh", m -> {
            System.out.println(m);
            return false;
        });
        mdb.add(new Message("hello there", "bruh", "justin"));
        mdb.add(new Message("hello there", "bruh", "justin"));
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
