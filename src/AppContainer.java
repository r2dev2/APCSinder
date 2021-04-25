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
    //implement matching UI.

    public AppContainer(String username) {
        this.username = username;
        setSize(1000, 600);
        setTitle("APCSinder chat - " + username);

        chat = new ChatUI(true, username); //visible

        add(chat);
        setVisible(true);
    }
    //TODO finish
}
