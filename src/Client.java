import java.io.*;
import java.util.*;
import javax.swing.JFrame;

public class Client
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("Client program");
        //String s = Serializer.serialize(new User("bruh"));
        //User user = Serializer.deserialize(s);
        //System.out.println(s);
        //System.out.println(user);

        User user = new User("Setup", new PersonalityType(true, true, true, true));
        Network n = new Network();
        AppContainer a = new AppContainer(user.username, n);

        // Network n = new Network("http://localhost:3000");
        // n.playground();
    }
}
