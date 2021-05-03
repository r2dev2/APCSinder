import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *  A ChatUI is a class that allows two matched people to talk to each other.
 *
 *  @author Justin Chang
 *  @version Apr 29, 2021
 */
public class ChatUI extends JPanel
{
    private JScrollPane chatBoxScroll;
    private JTextArea chatBox;
    private JTextField messageBox;
    private JButton sendButton;
    private StringBuilder sb = new StringBuilder();
    private JButton exitButton;
    private String username;
    private AppContainer container;

    /**
     * Create a new ChatUI object.
     * @param u the username of the user
     * @param a the app container to interact with
     */
    public ChatUI(String u, AppContainer a)
    {
        username = u;
        container = a;

        setLayout(new BorderLayout());
        setFeel();

        messageBox = new JTextField(1);
        sendButton = new JButton("Send");
        chatBox = getText();
        chatBoxScroll = new JScrollPane(chatBox);
        ExitBar exit = new ExitBar();
        exitButton = exit.getExitButton(); //currently does nothing

        sendButton.addActionListener(e -> sendText());
        messageBox.addKeyListener(enterSendListener());
        exitButton.addActionListener(e -> container.chatToMatching());

        add(sendButton, BorderLayout.EAST);
        add(chatBoxScroll, BorderLayout.CENTER);
        add(messageBox, BorderLayout.SOUTH);
        add(exit, BorderLayout.WEST);
    }

    /**
     * Clears the message box and sends a message if there is a message to send.
     */
    private void sendText()
    {
        String t = messageBox.getText();
        if (t.length() != 0) {
            sb.append(username + ": " + t + "\n");
            chatBox.setText(sb.toString());
            messageBox.setText("");
        }
    }

    /**
     * Returns a new KeyListener that sends a message if enter is pressed.
     * @return a KeyListener that sends a message if enter is pressed.
     */
    private KeyListener enterSendListener()
    {
        return new KeyListener()
        {
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendText();
                }
            }
            public void keyReleased(KeyEvent e) { }
            public void keyTyped(KeyEvent e) { }
        };
    }

    /**
     * Sets the feel of the container.
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
     * Returns the message area.
     * @return a JTextArea that displays all the chats.
     */
    private JTextArea getText()
    {
        JTextArea t = new JTextArea();
        t.setEditable(false);
        t.setLineWrap(true);
        return t;
    }

    /**
     *  An ExitBar is a class that just contains an exit button that spans the top-left.
     *
     *  @author Justin Chang
     *  @version Apr 29, 2021
     */
    private class ExitBar extends JPanel
    {
        private JButton exit;

        /**
         * Create a new ExitBar object.
         */
        public ExitBar()
        {
            setFeel();
            setLayout(new GridLayout(9, 0));
            exit = new JButton("Exit");

            add(exit);
        }

        /**
         * Returns the exit button.
         * @return a JButton that is the exit button.
         */
        public JButton getExitButton()
        {
            return exit;
        }
    }
}
