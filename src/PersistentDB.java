import java.io.*;
import java.nio.file.*;

public abstract class PersistentDB<T>
{
    private String filename;
    private T defaultVal;

    public PersistentDB(String filename, T defaultVal)
    {
        this.filename = filename;
        this.defaultVal = defaultVal;
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
}
