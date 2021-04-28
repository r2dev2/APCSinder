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
    //implement matching UI.

    public AppContainer(String username) {
        setSize(800, 400);
        setTitle("APCSinder setup - " + username);

        personality = new PersonalitySetupUI(username, true, this); //visible
        chat = new ChatUI(username, false, this); //invisible

        add(chat);
        add(personality);
        setVisible(true);
    }

    public void personalityToMatching() {
        personality.setVisible(false);
        //make matching ui visible
        //TODO when matching ui is done
    }

    public void matchingToChat() {
        //make matching ui invisible
        chat.setVisible(true);
    }

    public void chatToMatching() {
        chat.setVisible(false);
        //make matching ui visible;
    }
}
