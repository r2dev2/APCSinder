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
    // Fields ................................................................
    private Network network;
    private JButton match;
    private AppContainer container;
    private CardLayout c;

    /**
     * Create a new MatchingUI object.
     * @param container the app container to connect to.
     * @param network the network to connect to
     */
    public MatchingUI(AppContainer container, Network network) {
        this.network = network;
        this.container = container;
        c = new CardLayout();

        setLayout(c);
        setFeel();

        match = new JButton("Match!");
        match.setFont(new Font("Arial", Font.PLAIN, 48));
        match.addActionListener(e -> c.next(this));

        add(match);
        add(new Matcher());
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

    /**
     *  Creates a UI for the user to reject and accept other potential matches.
     *
     *  @author Justin Chang
     *  @version May 10, 2021
     */
    private class Matcher extends JPanel
    {
        public final JButton accept;
        public final JButton reject;
        private JTextField userDescription;
        private JTextField name;

        /**
         * Create a new Matcher object.
         */
        public Matcher() {
            setLayout(new BorderLayout());

            accept = new JButton("Accept");
            reject = new JButton("Reject");

            userDescription = new JTextField(1);
            userDescription.setEditable(false);

            name = new JTextField(1);
            name.setEditable(false);
            name.setFont(new Font("Arial", Font.PLAIN, 64));

            add(accept, BorderLayout.EAST);
            add(reject, BorderLayout.WEST);
            add(name, BorderLayout.CENTER);
            add(userDescription, BorderLayout.SOUTH);
        }
    }
}