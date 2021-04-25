import java.io.Serializable;

public class Message implements Serializable, Comparable<Message>
{
    public final String msg;
    public final String sender;
    public final String receiver;
    // Unix time of message
    public final int timestamp;

    public Message(String msg, String sender, String receiver, int timestamp)
    {
        this.msg = msg;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
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
