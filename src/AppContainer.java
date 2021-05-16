import java.awt.CardLayout;
import javax.swing.JFrame;

/**
 *  An AppContainer class is a class that holds the UIs and implements swapping functionality.
 *  The UIs are organized in the order CreateUserUI - LoginUI - PersonalitySetupUI - MatchingUI - ChatUI
 *
 *  @author Justin Chang
 *  @version Apr 24, 2021
 */
public class AppContainer extends JFrame
{
    private String username;
    private String password;
    private String description;
    private ChatUI chat;
    private LoginUI login;
    private PersonalitySetupUI personality;
    private CreateUserUI userSetup;
    private MatchingUI matching;
    private CardLayout c;
    private Network n;
    //implement matching UI.

    /**
     * Create a new AppContainer object.
     *
     * @param network the network client to use
     */
    public AppContainer(Network network)
    {
        c = new CardLayout();
        n = network;

        setSize(500, 400);
        setLayout(c);
        setTitle("APCSinder - " + username);

        userSetup = new CreateUserUI(this);
        login = new LoginUI(this, n);

        add(userSetup);
        add(login);
        setVisible(true);
    }

    /**
     * Updates the name and password from CreateUserUI and refreshes the title.
     * Proceeds with opening up the next UI screens (personality, matching, chat).
     * @param name username
     * @param pwd password
     * @param desc description
     */
    public void completeSetup(String name, String pwd, String desc) {
        username = name;
        password = pwd;
        description = desc;
        setTitle("APCSinder - " + username);
        personality = new PersonalitySetupUI(username, this, n, password);
        chat = new ChatUI(username, this, n);
        matching = new MatchingUI(username, this, n);
        setSize(800, 400);

        add(personality);
        add(matching);
        add(chat);
        setVisible(true);
    }

    /**
     * Swaps from the user setup UI to the personality UI.
     */
    public void setupToPersonality()
    {
        c.next(getContentPane());
    }
    /**
     * Swaps from the personality UI to the matching UI.
     */
    public void personalityToMatching()
    {
        c.next(getContentPane());
    }

    /**
     * Swaps from the matching UI to the chat UI.
     */
    public void matchingToChat()
    {
        chat.updateSidePanel();
        c.last(getContentPane());
    }

    /**
     * Swaps from the chat UI to the matching UI.
     */
    public void chatToMatching()
    {
        c.previous(getContentPane());
    }
}
