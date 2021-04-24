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
    private User user;
    private ChatUI chat;
    //implement matching UI.

    public AppContainer(User user) {
        this.user = user;
        setSize(1000, 600);
        setTitle("APCSinder chat - " + user.toString());

        chat = new ChatUI(true, user); //visible

        add(chat);
        setVisible(true);
    }
    //TODO finish
}
