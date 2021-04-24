import java.io.Serializable;

public class Message implements Serializable
{
    public final String msg;
    public final String sender;
    public final String receiver;

    public Message(String msg, String sender, String receiver)
    {
        this.msg = msg;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String toString()
    {
        return String.format("%s: %s", sender, msg);
    }
}
