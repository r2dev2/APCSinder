import java.awt.CardLayout;
import javax.swing.JFrame;

/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Justin Chang
 *  @version Apr 24, 2021
 */
public class AppContainer extends JFrame
{
    private String username;
    private ChatUI chat;
    private PersonalitySetupUI personality;
    private CardLayout c;
    //implement matching UI.

    public AppContainer(String username) {
        c = new CardLayout();

        setSize(800, 400);
        setLayout(c);
        setTitle("APCSinder setup - " + username);

        personality = new PersonalitySetupUI(username, this); //visible
        chat = new ChatUI(username, this); //invisible

        add(personality);
        //add matching ui HERE in between personality and chat
        add(chat);
        setVisible(true);
    }

    public void personalityToMatching() {
        c.next(getContentPane());
    }

    public void matchingToChat() {
        c.last(getContentPane());
    }

    public void chatToMatching() {
        //should only be called from the chat UI.
        c.previous(getContentPane());
    }
}
