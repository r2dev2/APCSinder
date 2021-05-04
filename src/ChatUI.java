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
    private JList<String> users; //change data type later if necessary
    private String username;
    private AppContainer container;
    private Network network;

    /**
     * Create a new ChatUI object.
     * @param u the username of the user
     * @param a the app container to interact with
     * @param network the network to connect to
     */
    public ChatUI(String u, AppContainer a, Network network)
    {
        username = u;
        container = a;
        this.network = network;

        setLayout(new BorderLayout());
        setFeel();

        messageBox = new JTextField(1);
        sendButton = new JButton("Send");
        chatBox = getText();
        chatBoxScroll = new JScrollPane(chatBox);
        ExitBar exit = new ExitBar();
        exitButton = exit.getExitButton();
        users = exit.getUsers();

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
        private JList<String> list;
        private GridBagConstraints constraint = new GridBagConstraints();

        /**
         * Create a new ExitBar object.
         */
        public ExitBar()
        {
            setFeel();
            setLayout(new GridBagLayout());
            exit = new JButton("Exit");

            String[] data = {"one", "two", "three", "four"};
            list = new JList<String>(data); //just test

            constraint.fill = GridBagConstraints.BOTH;
            constraint.gridx = 0;
            constraint.gridy = 0;
            constraint.insets = new Insets(5, 5, 5, 5);
            constraint.weightx = 0.5;
            constraint.gridwidth = 1;
            constraint.gridheight = 1;
            add(exit, constraint);

            constraint.fill = GridBagConstraints.BOTH;
            constraint.gridx = 0;
            constraint.gridy = 1;
            constraint.insets = new Insets(5, 5, 5, 5);
            constraint.weightx = 0.2;
            constraint.weighty = 0.9;
            constraint.gridwidth = 1;
            constraint.gridheight = 9;
            add(list, constraint);
        }

        /**
         * Returns the exit button.
         * @return a JButton that is the exit button.
         */
        public JButton getExitButton()
        {
            return exit;
        }

        /**
         * Returns the menu of users
         * @return the menu of users
         */
        public JList<String> getUsers()
        {
            return list;
        }
    }
}
