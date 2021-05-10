import javax.swing.*;
import java.awt.*;

/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Kevin Li
 *  @version May 3, 2021
 */
public class MatchingUI extends JPanel
{
    // Fields ...............................................................
    private String username;
    private Network network;
    private AppContainer container;

    /**
     * Create a new MatchingUI object.
     * @param name User name
     * @param network the network to connect to
     */
    public MatchingUI(String name, Network network) { //add other stuff as needed
        username = name;
        this.network = network;
        setLayout(new BorderLayout());
        setFeel();
    }

    /**
     * Sets the feel of the UI.
     */
    private void setFeel()
    {
        try {
            UIManager.setLookAndFeel (
                    "javax.swing.plaf.nimbus.NimbusLookAndFeel"
            );
        } catch (Exception e) {
            System.out.println("This is never supposed to get called");
        }
    }
}