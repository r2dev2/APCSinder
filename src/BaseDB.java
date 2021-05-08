import java.io.*;
import java.nio.file.*;
import java.util.HashMap;

/**
 * A base class for persistent and subscribable databases.
 */
public abstract class BaseDB<T, E>
{
    private String filename;
    private T defaultVal;
    private HashMap<String, Subscriber<E>> subscribers;

    public BaseDB(String filename, T defaultVal)
    {
        this.filename = filename;
        this.defaultVal = defaultVal;
        this.subscribers = new HashMap<String, Subscriber<E>>();
    }

    public void subscribe(String id, Subscriber<E> subscriber)
    {
        subscribers.put(id, subscriber);
    }

    protected void notifySubscriber(E item, String id)
    {
        if (!subscribers.containsKey(id)) return;
        var sub = subscribers.get(id);
        if (!sub.onItem(item))
            subscribers.remove(id);
    }

    protected T load()
    {
        T obj = null;
        try {
            String contents = Files.readString(Path.of(filename));
            obj = Serializer.deserialize(contents);
            obj.hashCode();
            return obj;
        }
        catch (IOException | NullPointerException e) {
            return defaultVal;
        }
    }

    protected void save(Serializable obj)
    {
        try {
            String serialized = Serializer.serialize(obj);
            Files.write(Paths.get(filename), serialized.getBytes(), StandardOpenOption.CREATE);
        }
        catch (IOException | NullPointerException e) {
            return;
        }
    }

    public interface Subscriber<V>
    {
        public boolean onItem(V item);
    }
}
