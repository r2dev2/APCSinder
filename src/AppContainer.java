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
        personalityFrame.setSize(1000, 600);
        personalityFrame.setTitle("APCSinder setup - " + username);

        personality = new PersonalitySetupUI(username);
        //chat = new ChatUI(true, username); //visible
        //add(chat);
        personalityFrame.add(personality);
        personalityFrame.setVisible(true);
    }
    //TODO finish
}
