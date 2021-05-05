import java.util.ArrayList;
import java.util.HashMap;
import java.net.http.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.*;

/**
 * Networking for client.
 */
public class Network
{
    private String url;
    private String username;
    private String token;
    private HttpClient client;

    /**
     * Constructor.
     *
     * @param url the url of the server
     * @param username the user to login
     */
    public Network(String url)
    {
        this.url = url;
        this.username = null;
        this.token = null;
        this.client = HttpClient.newBuilder().build();
    }

    /**
     * Default constructor.
     * Defaults to using the replit server
     */
    public Network()
    {
        this("https://apcsinder.r2dev2bb8.repl.co");
    }

    /**
     * Logs in the user to the server.
     * The username and login token are stored.
     *
     * @param username the user's username
     * @param password the user's password
     * @return the result of the login
     */
    public LoginResult login(String username, String password) throws IOException, InterruptedException
    {
        String serialized = Serializer.serialize(new LoginAttempt(username, password));
        HttpRequest req = HttpRequest.newBuilder()
            .uri(endpoint("/login"))
            .POST(HttpRequest.BodyPublishers.ofString(serialized))
            .build();
        HttpResponse<String> r = client.send(req, HttpResponse.BodyHandlers.ofString());
        LoginResult res = Serializer.deserialize(r.body());
        if (res != null && res.success) {
            token = res.token;
        }
        return res;
    }

    private interface EventListener<T>
    {
        public void onEvent(T m);
    }

    /**
     * Subscribe to new matches.
     *
     * @param onMatch the callback for when a new match is found
     */
    public void subscribeMatches(EventListener<Match> onMatch)
    {
    }

    /**
     * Subscribe to new chat messages.
     *
     * @param onMessage the callback for when a new chat message is sent
     */
    public void subscribeMessage(EventListener<Message> onMessage)
    {
    }

    /**
     * Creates a user.
     * The user will not be able to be changed once created
     *
     * @param user the user to create
     * @param password the user's password
     * @return whether the user was created or not
     */
    public boolean createUser(User user, String password)
    {
        //TODO
        return false;
    }

    /**
     * Get the matches the user has made already.
     *
     * @return the matches the user has made already
     */
    public ArrayList<Match> getMatches()
    {
        return null;
    }

    /**
     * Get the messages in the user's conversations
     *
     * @return map of other user to the message history with said user
     */
    public HashMap<String, ArrayList<Message>> getMessages()
    {
        return null;
    }

    /**
     * Sends a message.
     *
     * @param msg the message to send
     */
    public void sendMessage(Message msg) throws IOException, InterruptedException
    {
        String serialized = Serializer.serialize(msg);
        HttpRequest req = HttpRequest.newBuilder()
            .uri(endpoint("/message"))
            .POST(HttpRequest.BodyPublishers.ofString(serialized))
            .build();
        client.sendAsync(req, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Get the uri for an endpoint.
     * This overrides uri's built in validation so make sure it's a valid endpoint.
     * @param end the endpoint
     * @return the uri for the endpoint
     */
    private URI endpoint(String end)
    {
        try {
            return new URI(url + end);
        }
        catch (URISyntaxException e) {
            System.err.printf("Invalid endpoint for url: '%s', end: '%s'\n", url, end);
            System.exit(1);
            return null;
        }
    }

    /**
     * Testing grounds for how to do this class.
     */
    public void playground() throws IOException, InterruptedException
    {
        sendMessage(new Message("Hello there", "Kenobi", "Skywalker", 10));
        login("bruh", "moment");
    }
}
