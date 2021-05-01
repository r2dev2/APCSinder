import java.io.*;
import java.util.*;
import javax.swing.JFrame;

public class Client
{
    public static void main(String[] args)
    {
        System.out.println("Client program");
        //String s = Serializer.serialize(new User("bruh"));
        //User user = Serializer.deserialize(s);
        //System.out.println(s);
        //System.out.println(user);

        User user = new User("bruh");

        AppContainer a = new AppContainer(user.getUsername());
    }
}
