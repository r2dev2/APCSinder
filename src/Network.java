import java.util.ArrayList;
import java.util.HashMap;

/**
 * Networking for client.
 */
public class Network
{
    private String url;
    private String username;
    private String token;

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
    public LoginResult login(String username, String password)
    {
        return null;
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
    public void sendMessage(Message msg)
    {
    }
}
