import java.awt.CardLayout;
import javax.swing.JFrame;

/**
 *  An AppContainer class is a class that holds the UIs and implements swapping functionality.
 *
 *  @author Justin Chang
 *  @version Apr 24, 2021
 */
public class AppContainer extends JFrame
{
    private String username;
    private String password;
    private ChatUI chat;
    private PersonalitySetupUI personality;
    private CreateUserUI userSetup;
    private CardLayout c;
    //implement matching UI.

    /**
     * Create a new AppContainer object.
     * @param username the username of the user
     */
    public AppContainer(String username)
    {
        c = new CardLayout();

        setSize(800, 400);
        setLayout(c);
        setTitle("APCSinder - " + username);

        userSetup = new CreateUserUI(this);
        personality = new PersonalitySetupUI(username, this); //visible
        chat = new ChatUI(username, this); //invisible

        add(userSetup);
        add(personality);
        //add matching ui HERE in between personality and chat
        add(chat);
        setVisible(true);
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
        c.last(getContentPane());
    }

    /**
     * Swaps from the chat UI to the matching UI.
     */
    public void chatToMatching()
    {
        //should only be called from the chat UI.
        c.previous(getContentPane());
    }

    public void completeSetup(String name, String pwd) {
        username = name;
        password = pwd;
    }
}