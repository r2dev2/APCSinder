import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
    private JButton startMatch;
    private Matcher matcher;
    private CardLayout card;
    private AppContainer container;
    private Match match;

    /**
     * Create a new MatchingUI object.
     * @param username the username of this user.
     * @param container the app container to connect to.
     * @param network the network to connect to
     */
    public MatchingUI(String username, AppContainer container, Network network) {
        this.network = network;
        this.username = username;
        this.container = container;
        card = new CardLayout();

        setLayout(card);
        setFeel();

        startMatch = new JButton("Match!");
        startMatch.setFont(new Font("Arial", Font.PLAIN, 48));
        startMatch.addActionListener(e -> card.next(this));

        matcher = new Matcher();
        matcher.accept.addActionListener(e -> acceptMatch());
        matcher.reject.addActionListener(e -> findMatch());

        findMatch();
        add(startMatch);
        add(matcher);
    }

    private void findMatch() {
        ArrayList<Match> matches = network.getPotentialMatches();
        int index = (int)(Math.random() * matches.size());
        Match m = matches.remove(index);
        matcher.loadMatch(m);
        match = m;
    }

    private void acceptMatch() {
        //network.acceptMatch()
        container.matchingToChat();
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

        private void loadMatch(Match m)
        {
            String personName = m.otherUser(username);
            name.setText(personName);
            //TODO finish once network has a get user method.
        }
    }
}