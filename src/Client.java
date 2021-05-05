import java.io.*;
import java.util.*;
import javax.swing.JFrame;

public class Client
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("Client program");
        String s = Serializer.serialize(new User("bruh",
                new PersonalityType(true, true, true, true)));
        User usr = Serializer.deserialize(s);
        System.out.println(s);
        System.out.println(usr);

        System.out.println("Login success: " + Serializer.serialize(
                    new LoginResult(true)));
        System.out.println("Login failure: " + Serializer.serialize(
                    new LoginResult(false)));


        User user = new User("Setup", new PersonalityType(true, true, true, true));
        // Network n = new Network();
        // AppContainer a = new AppContainer(user.username, n);

        String url = getServerURL(args);
        Network n = url == null
            ? new Network()
            : new Network(url);
        n.playground();
        Thread.sleep(100);
    }

    private static String getServerURL(String[] args)
    {
        boolean isURL = false;
        for (String arg: args) {
            if (isURL) {
                return arg;
            }
            isURL = arg.equals("-s");
        }
        return null;
    }
}
