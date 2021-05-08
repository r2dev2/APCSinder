import java.util.ArrayList;
import java.util.HashMap;

/**
 * A persistent database of messages to be used in the backend.
 *
 * @author Ronak Badhe
 * @version Sat Apr 24 15:48:52 PDT 2021
 */
public class MessageDB extends BaseDB<HashMap<Match, ArrayList<Message>>, Message>
{
    private HashMap<Match, ArrayList<Message>> messages;

    /**
     * Constructor.
     *
     * @param filename the filename to persist this messagedb to
     */
    public MessageDB(String filename)
    {
        super(filename, new HashMap<Match, ArrayList<Message>>());
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
        notifySubscriber(msg, msg.sender);
        notifySubscriber(msg, msg.receiver);
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
}
