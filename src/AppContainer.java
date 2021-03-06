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
    private ChatUI chat;
    private LoginUI login;
    private PersonalitySetupUI personality;
    private CreateUserUI userSetup;
    private MatchingUI matching;
    private CardLayout c;
    private Network n;

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
        setTitle("APCSinder");

        userSetup = new CreateUserUI(this);
        login = new LoginUI(this, n);

        add(login);
        add(userSetup);
        setVisible(true);
    }

    /**
     * Updates the name and password from CreateUserUI and refreshes the title.
     * Proceeds with opening up the next UI screens (personality, matching, chat).
     * @param name the username of the user
     * @param pwd password the password of the user
     * @param desc description the user's description
     */
    public void completeSetup(String name, String pwd, String desc) {
        username = name;
        setTitle("APCSinder - " + name);

        if (!n.isLoggedIn()) {
            personality = new PersonalitySetupUI(this);
            personality.onTestFinish(type ->
                    n.createUser(new User(name, type, desc), pwd));
            setSize(800, 400);

            add(personality);
            setVisible(true);
        }
        else {
            chat = new ChatUI(username, this, n);
            matching = new MatchingUI(username, this, n);
            setSize(800, 400);

            add(matching);
            add(chat);
            setVisible(true);
        }
    }

    /**
     * Goes back to the login screen once the user has finished completing
     * the personality test.
     */
    public void restartLogin() {
        setSize(500, 400);
        setLayout(c);
        setTitle("APCSinder - " + username);

        userSetup = new CreateUserUI(this);
        login = new LoginUI(this, n);

        add(login);
        add(userSetup);
        setVisible(true);
    }

    /**
     * Gets the next window arranged in this app container.
     */
    public void getNextWindow()
    {
        c.next(getContentPane());
    }

    /**
     * Swaps from the matching UI to the chat UI and loads the list of users on the chatUI's sidepanel.
     */
    public void matchingToChat()
    {
        chat.updateSidePanel();
        c.last(getContentPane());
    }

    /**
     * Gets the previous window arranged in this app container.
     */
    public void getPreviousWindow()
    {
        c.previous(getContentPane());
    }
}
