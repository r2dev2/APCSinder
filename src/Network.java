import java.util.ArrayList;
import java.util.HashMap;
import java.net.http.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.*;
import java.util.stream.Stream;

/**
 * Networking for client.
 *
 * @author Ronak Badhe
 * @version May 8, 2021
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
     * @param name the user's username
     * @param password the user's password
     * @return the result of the login
     * @throws IOException possible exception
     * @throws InterruptedException possible exception
     */
    public LoginResult login(String name, String password) throws IOException, InterruptedException
    {
        String serialized = Serializer.serialize(new LoginAttempt(name, password));
        HttpRequest req = HttpRequest.newBuilder()
            .uri(endpoint("/login"))
            .POST(HttpRequest.BodyPublishers.ofString(serialized))
            .build();
        HttpResponse<String> r = client.send(req, HttpResponse.BodyHandlers.ofString());
        LoginResult res = Serializer.deserialize(r.body());
        if (res != null && res.success) {
            token = res.token;
            this.username = name;
        }
        return res;
    }

    /**
     * Checks whether the user is logged in.
     *
     * @return whether the user is logged in
     */
    public boolean isLoggedIn()
    {
        return token != null;
    }

    /**
     * Subscribe to new matches.
     *
     * @param onMatch the callback for when a new match is found
     */
    public void subscribeMatches(EventListener<Match> onMatch)
    {
        subscribeEvent(onMatch, "/listenmatches");
    }

    /**
     * Subscribe to potential matches to accept or reject.
     *
     * @param onPotMatch the callback for when a new potential match is recommended
     */
    public void subscribePotentialMatches(EventListener<Match> onPotMatch)
    {
        subscribeEvent(onPotMatch, "/listenpotentialmatches");
    }

    /**
     * Subscribe to new chat messages.
     *
     * @param onMessage the callback for when a new chat message is sent
     */
    public void subscribeMessage(EventListener<Message> onMessage)
    {
        subscribeEvent(onMessage, "/listenmessages");
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
        try {
            String serialized = Serializer.serialize(new UserCreationAttempt(user, password));
            HttpRequest req = HttpRequest.newBuilder()
                .uri(endpoint("/createuser"))
                .PUT(HttpRequest.BodyPublishers.ofString(serialized))
                .build();
            HttpResponse<String> r = client.send(req, HttpResponse.BodyHandlers.ofString());
            return r.body().equals("success");
        }
        catch (IOException | InterruptedException e) {
            return false;
        }
    }

    /**
     * Get the matches the user has made already.
     *
     * @return the matches the user has made already
     */
    public ArrayList<Match> getMatches()
    {
        return getResource("/matches", new ArrayList<Match>());
    }

    /**
     * Gets the potential matches already recommended to accept or reject.
     *
     * @return the potential matches already recommended to the user
     */
    public ArrayList<Match> getPotentialMatches()
    {
        var potMatch = new ArrayList<Match>();
        for (var pot: getResource("/potentialmatches", new ArrayList<String>())) {
            potMatch.add(new Match(username, pot));
        }
        return potMatch;
    }

    /**
     * Get the messages in the user's conversations
     *
     * @return map of other user to the message history with said user
     */
    public HashMap<String, ArrayList<Message>> getMessages()
    {
        return getResource("/messages", new HashMap<String, ArrayList<Message>>());
    }

    /**
     * Place a description of your method here.
     * @param name the username of the User
     * @return the user associated with this username
     */
    public User getUser(String name)
    {
        var req = buildGetRequestWithHeader("/user", "Username", name);
        return getResource(req, new User());
    }

    /**
     * Sends a message.
     *
     * @param msg the message to send
     * @throws IOException possible exception
     * @throws InterruptedException possible exception
     */
    public void sendMessage(Message msg) throws IOException, InterruptedException
    {
        postObjectAsync("/message", msg);
    }

    /**
     * Accepts a recommended match.
     *
     * @param match the recommended match to accept
     * @throws IOException possible exception
     * @throws InterruptedException possible exception
     */
    public void acceptMatch(Match match) throws IOException, InterruptedException
    {
        postObjectAsync("/acceptmatch", match);
    }

    /**
     * Rejects a recommended match.
     *
     * @param match the recommended match to reject
     * @throws IOException possible exception
     * @throws InterruptedException possible exception
     */
    public void rejectMatch(Match match) throws IOException, InterruptedException
    {
        postObjectAsync("/rejectmatch", match);
    }

    /**
     * Testing grounds for how to do this class.
     * @throws IOException possible exception
     * @throws InterruptedException possible exception
     */
    public void playground() throws IOException, InterruptedException
    {
        createUser(new User("bruh", new PersonalityType(), "I like turtles"), "moment");
        login("bruh", "moment");
        sendMessage(new Message("Hello there", "bruh", "Justin"));

        getMessages();
        getMatches();
        subscribeMessage(System.out::println);
        subscribeMatches(System.out::println);
        Thread.sleep(100000000);
    }

    public interface EventListener<T>
    {
        public void onEvent(T m);
    }

    private <T> void subscribeEvent(EventListener<T> onEvent, String end)
    {
        Thread t = new Thread(() -> {
            try {
                subscribeEventProcessing(onEvent, end);
            }
            catch (UncheckedIOException | IOException | InterruptedException e) {
                return;
            }
        });
        t.setDaemon(true);
        t.start();
    }

    private <T> void subscribeEventProcessing(EventListener<T> onEvent, String end) throws IOException, InterruptedException
    {
        HttpRequest req = buildGetRequest(end);
        HttpResponse<Stream<String>> stream = client.send(req, HttpResponse.BodyHandlers.ofLines());
        stream
            .body()
            .forEach((String line) -> {
                T m = Serializer.deserialize(line);
                if (m == null) {
                    return;
                }
                onEvent.onEvent(m);
            });
    }

    private <T> T getResource(HttpRequest req, T defaultObj)
    {
        try {
            var r = client.send(req, HttpResponse.BodyHandlers.ofString());

            T res = Serializer.deserialize(r.body());
            res.hashCode();
            return res;
        }
        catch (IOException | InterruptedException | NullPointerException e) {
            return defaultObj;
        }
    }

    private <T> T getResource(String end, T defaultObj)
    {
        return getResource(buildGetRequest(end), defaultObj);
    }

    private HttpRequest
    buildGetRequestWithHeader(String end, String key, String value)
    {
        return HttpRequest.newBuilder()
            .uri(endpoint(end))
            .setHeader(key, value)
            .GET()
            .build();
    }

    private HttpRequest buildGetRequest(String end)
    {
        return HttpRequest.newBuilder()
            .uri(endpoint(end))
            .setHeader("Token", token)
            .GET()
            .build();
    }

    private HttpRequest buildPostRequest(String end, Serializable obj)
    {
        String serialized = Serializer.serialize(obj);
        var builder = HttpRequest.newBuilder(endpoint(end))
            .uri(endpoint(end))
            .POST(HttpRequest.BodyPublishers.ofString(serialized));

        return addTokenIfExists(builder).build();
    }

    private HttpRequest.Builder addTokenIfExists(HttpRequest.Builder builder)
    {
        if (token == null) return builder;
        return builder.setHeader("Token", token);
    }

    private void postObjectAsync(String end, Serializable obj) throws IOException, InterruptedException
    {
        var req = buildPostRequest(end, obj);
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
}
