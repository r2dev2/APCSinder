import java.io.*;
import java.util.*;

/**
 * Serializes stuff sanely.
 * Because in the "high level language" of java, one must write 20 lines and
 * create 3 objects to serialize a goddamn object.
 */
public class Serializer
{
    /**
     * Serializes an object.
     *
     * @param obj the object to serialize
     * @return the base64 encoded serialization of the object.
     */
    public static String serialize(Serializable obj)
    {
        ByteArrayOutputStream obs = null;
        ObjectOutputStream oos = null;
        try {
            obs = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(obs);
            oos.writeObject(obj);
            return Base64.getEncoder().encodeToString(obs.toByteArray());
        }
        catch (IOException e) {
            return "";
        }
        finally {
            // shh, java is a little dum and can't realize that these are final
            ByteArrayOutputStream b = obs;
            ObjectOutputStream o = oos;
            suppress(() -> {
                b.close();
                o.close();
            });
        }
    }

    /**
     * Deserializes an object.
     *
     * @param serialized the base64 encoded serialization of an object.
     * @return the deserialized object
     */
    public static <T> T deserialize(String serialized)
    {
        byte[] bytes = Base64.getDecoder().decode(serialized);
        ByteArrayInputStream ibs = null;
        ObjectInputStream ios = null;
        try {
            ibs = new ByteArrayInputStream(bytes);
            ios = new ObjectInputStream(ibs);
            return (T) ios.readObject();
        }
        catch (IOException e) {
            return null;
        }
        catch (ClassNotFoundException e) {
            return null;
        }
        finally {
            // shh, java is a little dum and can't realize that these are final
            ByteArrayInputStream b = ibs;
            ObjectInputStream o = ios;
            suppress(() -> {
                b.close();
                o.close();
            });
        }
    }

    private interface Callable
    {
        public void run() throws Exception;
    }

    /**
     * Suppress some function's exceptions.
     * 
     * @param c the stuff to suppress
     */
    private static void suppress(Callable c)
    {
        try {
            c.run();
        }
        catch (Exception e) {
            c = c;
        }
    }
}
