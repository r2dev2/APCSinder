import java.util.ArrayList;
import java.util.HashMap;

/**
 * A persistent database of messages to be used in the backend.
 *
 * @author Ronak Badhe
 * @version Sat Apr 24 15:48:52 PDT 2021
 */
public class MessageDB extends PersistentDB<HashMap<Match, ArrayList<Message>>>
{
    private HashMap<Match, ArrayList<Message>> messages;
    private HashMap<String, MessageSubscriber> subscribers;

    /**
     * Constructor.
     *
     * @param filename the filename to persist this messagedb to
     */
    public MessageDB(String filename)
    {
        super(filename, new HashMap<Match, ArrayList<Message>>());
        subscribers = new HashMap<String, MessageSubscriber>();
        messages = load();
    }

    /**
     * Add a message to the databse.
     *
     * @param msg the message to add
     */
    public void add(Message msg)
    {
        var key = new Match(msg.sender, msg.receiver);
        var conversation = messages.getOrDefault(key, new ArrayList<Message>());
        conversation.add(msg);
        messages.put(key, conversation);
        save(messages);
        notifyUser(msg, msg.sender);
        notifyUser(msg, msg.receiver);
    }

    private void notifyUser(Message msg, String user)
    {
        if (!subscribers.containsKey(user)) return;
        var sub = subscribers.get(user);
        if (!sub.onMessage(msg))
            subscribers.remove(user);
    }

    /**
     * Get the messages for a particlar user pair.
     *
     * @param match the user pair
     */
    public ArrayList<Message> get(Match match)
    {
        return messages.get(match);
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

    public interface MessageSubscriber
    {
        /**
         * @param m the new message
         *
         * @return whether to keep subscribing
         */
        public boolean onMessage(Message m);
    }
}
