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
    private Network network;

    /**
     * Create a new MatchingUI object.
     * @param network the network to connect to
     */
    public MatchingUI(Network network) { //add other stuff as needed
        this.network = network;
    }
}