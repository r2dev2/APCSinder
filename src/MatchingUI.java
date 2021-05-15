import javax.swing.*;
import java.awt.*;
import java.io.IOException;
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
    private Matcher matcher;
    private CardLayout card;
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
        card = new CardLayout();

        setLayout(card);
        setFeel();

        prelimMatch prelim = new prelimMatch();

        prelim.toMatch.addActionListener(e -> card.next(this));
        prelim.toChat.addActionListener(e -> container.matchingToChat());

        matcher = new Matcher();
        matcher.accept.addActionListener(e -> acceptMatch());
        matcher.reject.addActionListener(e -> rejectMatch());
        matcher.toChat.addActionListener(e -> container.matchingToChat());

        match = findMatch();
        matcher.loadMatch(match);

        add(prelim);
        add(matcher);
    }

    private Match findMatch() {
        ArrayList<Match> matches = network.getPotentialMatches();
        int index = (int)(Math.random() * matches.size());
        Match m = matches.get(index);

        return m;
    }

    private void rejectMatch() {
        try
        {
            network.rejectMatch(match);
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }

        match = findMatch();
        matcher.loadMatch(match);
    }

    private void acceptMatch() {
        try
        {
            network.acceptMatch(match);
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
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
        public final JButton toChat;
        private JTextField userDescription;
        private JTextField name;

        /**
         * Create a new Matcher object.
         */
        public Matcher() {
            setLayout(new BorderLayout());

            accept = new JButton("Accept");
            reject = new JButton("Reject");
            toChat = new JButton("Go to chat");

            userDescription = new JTextField(1);
            userDescription.setEditable(false);

            name = new JTextField(1);
            name.setEditable(false);
            name.setFont(new Font("Arial", Font.PLAIN, 64));

            add(accept, BorderLayout.EAST);
            add(reject, BorderLayout.WEST);
            add(name, BorderLayout.CENTER);
            add(userDescription, BorderLayout.SOUTH);
            add(toChat, BorderLayout.NORTH);
        }

        private void loadMatch(Match m)
        {
            String personName = m.otherUser(username);
            name.setText(personName);
            User user = network.getUser(personName);
            userDescription.setText(user.description);
        }
    }

    /**
     *  The prelimMatch class allows the user to choose between chatting and matching.
     *
     *  @author Justin Chang
     *  @version May 15, 2021
     */
    private class prelimMatch extends JPanel
    {
        public final JButton toMatch;
        public final JButton toChat;

        public prelimMatch() {
            setLayout(new BorderLayout());

            toMatch = new JButton("Match!");
            toChat = new JButton("Chat!");

            toMatch.setFont(new Font("Arial", Font.PLAIN, 48));
            toChat.setFont(new Font("Arial", Font.PLAIN, 48));

            add(toMatch, BorderLayout.WEST);
            add(toChat, BorderLayout.EAST);
        }
    }
}