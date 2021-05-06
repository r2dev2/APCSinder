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
    private HashMap<String, MessageSubscriber> subscribers;

    /**
     * Constructor.
     *
     * @param filename the filename to persist this messagedb to
     */
    public MessageDB(String filename)
    {
        this.filename = filename;
        subscribers = new HashMap<String, MessageSubscriber>();
        load();
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
     */
    public ArrayList<Message> get(Match match)
    {
        // TODO
        return null;
    }

    /**
     * Subscribe a user to new messages for the user.
     *
     * @param user the username
     * @param subscriber the callback for each new message
     */
    public void subscribe(String user, MessageSubscriber subscriber)
    {
        subscribers.put(user, subscriber);
    }

    private void load()
    {
    }

    private void save()
    {
        // TODO
    }

    private interface MessageSubscriber
    {
        /**
         * @param m the new message
         *
         * @return whether to keep subscribing
         */
        public boolean onMessage(Message m);
    }
}
