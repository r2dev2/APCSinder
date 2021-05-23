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
     * Constructor that creates a new MessageDB object with a specific filename.
     *
     * @param filename the filename to persist this messagedb to
     */
    public MessageDB(String filename)
    {
        super(filename, new HashMap<>());
        messages = load();
    }

    /**
     * Create a new MessageDB object with a null filename.
     */
    public MessageDB()
    {
        this(null);
    }

    /**
     * Add a message to the databse.
     *
     * @param msg the message to add
     */
    public void add(Message msg)
    {
        addMsgToConversation(msg);
        save(messages);
        notifyOfMessage(msg);
    }

    private void addMsgToConversation(Message msg)
    {
        var key = new Match(msg.sender, msg.receiver);
        var conversation = messages.getOrDefault(key, new ArrayList<>());
        conversation.add(msg);
        messages.put(key, conversation);
    }

    private void notifyOfMessage(Message msg)
    {
        notifySubscriber(msg.sender, msg);
        notifySubscriber(msg.receiver, msg);
    }

    /**
     * Get the messages for a particular user pair.
     *
     * @param match the user pair
     * @return the list of messages for a particular user pair.
     */
    public ArrayList<Message> get(Match match)
    {
        return messages.get(match);
    }
}
