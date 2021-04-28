import javax.swing.JFrame;

/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Justin Chang
 *  @version Apr 24, 2021
 */
public class AppContainer
{
    private String username;
    private ChatUI chat;
    private PersonalitySetupUI personality;
    private JFrame personalityFrame;
    //implement matching UI.

    public AppContainer(String username) {
        this.username = username;
        personalityFrame = new JFrame();
        personalityFrame.setSize(800, 400);
        personalityFrame.setTitle("APCSinder setup - " + username);

        personality = new PersonalitySetupUI(username, true, this); //visible
        chat = new ChatUI(username, false, this); //invisible

        personality.add(chat);
        personalityFrame.add(personality);
        personalityFrame.setVisible(true);
    }

    public void personalityToMatching() {
        personality.toggleVisible(false);
        //make matching ui visible
        //TODO when matching ui is done
    }

    public void matchingToChat() {
        //make matching ui invisible
        chat.toggleVisible(true);
    }

    public void chatToMatching() {
        chat.toggleVisible(false);
        //make matching ui visible;
    }
}
