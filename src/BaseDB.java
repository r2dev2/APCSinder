import java.io.*;
import java.nio.file.*;
import java.util.HashMap;

/**
 * A base class for persistent and subscribable databases.
 *
 * @author Ronak Badhe
 * @version Mon May 10 09:16:31 PDT 2021
 */
public class BaseDB<T extends Serializable, E>
{
    private String filename;
    private T defaultVal;
    private HashMap<String, Subscriber<E>> subscribers;

    /**
     * Constructor.
     *
     * @param filename the file to persist to
     * @param defaultVal the value to return if pizzdec happens in loading
     */
    public BaseDB(String filename, T defaultVal)
    {
        this.filename = filename;
        this.defaultVal = defaultVal;
        this.subscribers = new HashMap<String, Subscriber<E>>();
    }

    /**
     * Subscribe to changes.
     *
     * @param id the id of the subscriber that will be notified
     * @param subscriber the callback for each event
     */
    public void subscribe(String id, Subscriber<E> subscriber)
    {
        subscribers.put(id, subscriber);
    }

    /**
     * Notifies the subscriber of a change.
     *
     * @param id the subscriber's id
     * @param item the change
     */
    protected void notifySubscriber(String id, E item)
    {
        if (!subscribers.containsKey(id)) return;
        var sub = subscribers.get(id);
        if (!sub.onItem(item))
            subscribers.remove(id);
    }

    /**
     * Load the object from the file.
     *
     * @return the object
     */
    protected T load()
    {
        T obj = null;
        try {
            String contents = Files.readString(Path.of(filename));
            obj = Serializer.deserialize(contents);
            // Throw npe if obj is null
            obj.hashCode();
            return obj;
        }
        catch (IOException | NullPointerException e) {
            return defaultVal;
        }
    }

    /**
     * Save the object to the file.
     *
     * @param obj the object to save
     */
    protected void save(T obj)
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
        /**
         * The callback to fire on each event.
         *
         * @param item the event
         * @return whether to continue subscribing
         */
        public boolean onItem(V item);
    }
}
