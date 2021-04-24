import java.util.ArrayList;
import java.util.HashMap;

public class MessageDB
{
    private String filename;
    private HashMap<Match, ArrayList<Message>> messages;

    public MessageDB(String filename)
    {
        this.filename = filename;
    }

    public void add(Message msg)
    {
        // TODO
    }

    public ArrayList<Message> get(User user, String token)
    {
        // TODO
        return null;
    }

    private void load()
    {
        // TODO
    }
}
