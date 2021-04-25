import java.io.*;
import java.util.*;
import javax.swing.JFrame;

public class Client
{
    public static void main(String[] args)
    {
        System.out.println("Client program");
        String s = Serializer.serialize(new User("bruh"));
        User user = Serializer.deserialize(s);
        System.out.println(s);
        System.out.println(user);

        JFrame j = new JFrame();
        j.setSize(1000, 600);
        j.setTitle("APCSinder Chat");
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.add(new ChatUI(true, "bruh"));
        j.setVisible(true);
    }
}
