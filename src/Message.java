import java.io.Serializable;

/**
 *  Represents one message sent from one user to another user.
 *  Model class that holds the message, sender, receiver, and
 *  can be compared based on timestamp.
 *
 *  @author Ronak Badhe
 *  @version May 20, 2021
 */
public class Message implements Serializable, Comparable<Message>
{
    /**
     * The message
     */
    public final String msg;
    /**
     * The person sending the message
     */
    public final String sender;
    /**
     * The person receiving the message
     */
    public final String receiver;
    /**
     * Unix timestamp
     */
    public final long timestamp;

    /**
     * Create a new Message object.
     * @param msg the message to send
     * @param sender the person sending the message
     * @param receiver the person receiving the message
     * @param timestamp the timestamp of the message
     */
    public Message(String msg, String sender, String receiver, long timestamp)
    {
        this.msg = msg;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
    }

    /**
     * Create a new Message object.
     * @param msg the message to send
     * @param sender the person sending the message
     * @param receiver the person receiving the message
     */
    public Message(String msg, String sender, String receiver)
    {
        this(msg, sender, receiver, System.currentTimeMillis() / 1000L);
    }

    public String toString()
    {
        return String.format("%s: %s", sender, msg);
    }

    /**
     * Compare based on timestamp.
     *
     * @param other the other message
     * @return m1 &lt; m2 if it was more recent
     */
    public int compareTo(Message other)
    {
        return 0;
    }
}
