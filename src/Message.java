import java.io.Serializable;

public class Message implements Serializable, Comparable<Message>
{
    public final String msg;
    public final String sender;
    public final String receiver;
    // Unix time of message
    public final long timestamp;

    public Message(String msg, String sender, String receiver, long timestamp)
    {
        this.msg = msg;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
    }

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
