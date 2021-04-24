import java.util.ArrayList;
import java.util.HashMap;

/**
 * A persistent database of messages to be used in the backend.
 *
 * @author Ronak Badhe
 * @version Sat Apr 24 15:48:52 PDT 2021
 */
public class MessageDB
{
    private String filename;
    private HashMap<Match, ArrayList<Message>> messages;
    private UserDB users;

    /**
     * Constructor.
     *
     * @param filename the filename to persist this messagedb to
     * @param users the userdb to use for authentication
     */
    public MessageDB(String filename, UserDB users)
    {
        this.filename = filename;
        this.users = users;
    }

    /**
     * Add a message to the databse.
     *
     * @param msg the message to add
     */
    public void add(Message msg)
    {
        // TODO
    }

    /**
     * Get the messages for a particlar user pair.
     *
     * @param match the user pair
     * @param token the login token of one of the users
     */
    public ArrayList<Message> get(Match match, String token)
    {
        // TODO
        return null;
    }

    private void load()
    {
        // TODO
    }

    private void save()
    {
        // TODO
    }
}
