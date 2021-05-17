import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

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
    private String recipient = null;
    private JList<String> users;
    private SideBar side;
    private String username;
    private Network network;

    /**
     * Create a new ChatUI object.
     * @param username the username of the user
     * @param container the app container to interact with
     * @param network the network to connect to
     */
    public ChatUI(String username, AppContainer container, Network network)
    {
        this.username = username;
        this.network = network;

        setLayout(new BorderLayout());
        setFeel();

        messageBox = new JTextField(1);
        sendButton = new JButton("Send");
        chatBox = getText();
        chatBoxScroll = new JScrollPane(chatBox);
        side = new SideBar();
        exitButton = side.getExitButton();
        users = side.getUsers();

        sendButton.addActionListener(e -> {
            try
            {
                sendText();
            }
            catch (IOException | InterruptedException e1)
            {
                e1.printStackTrace();
            }
        });
        messageBox.addKeyListener(enterSendListener());
        exitButton.addActionListener(e -> container.getPreviousWindow());
        users.addListSelectionListener(chooseUser());

        add(sendButton, BorderLayout.EAST);
        add(chatBoxScroll, BorderLayout.CENTER);
        add(messageBox, BorderLayout.SOUTH);
        add(side, BorderLayout.WEST);
    }

    /**
     * Clears the message box and sends a message if there is a message to send.
     * @throws InterruptedException
     * @throws IOException
     */
    private void sendText() throws IOException, InterruptedException
    {
        String t = messageBox.getText();
        if (t.length() != 0 && recipient != null) {
            Message m = new Message(t, username, recipient);

            sb.append(m.toString() + "\n");
            chatBox.setText(sb.toString());
            messageBox.setText("");

            network.sendMessage(m);
        }
    }

    /**
     * Returns a ListSelectionListener that loads chats.
     * @return ListSelctionListener that loads chats.
     */
    private ListSelectionListener chooseUser()
    {
        return new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                String user = users.getSelectedValue();

                sb = new StringBuilder();
                ArrayList<Message> messages = network.getMessages().get(user);
                for (Message m: messages) {
                    sb.append(m.sender + ": " + m.msg + "\n");
                }
                chatBox.setText(sb.toString());
                messageBox.setText("");
            }
        };
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
                    try
                    {
                        sendText();
                    }
                    catch (IOException | InterruptedException e1)
                    {
                        e1.printStackTrace();
                    }
                }
            }
            public void keyReleased(KeyEvent e) {}
            public void keyTyped(KeyEvent e) {}
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
     * Updates the user list.
     */
    public void updateSidePanel() {
        side.updateUserList();
        revalidate();
    }

    /**
     *  An SideBar is a class that just contains an exit button that spans the top-left.
     *
     *  @author Justin Chang
     *  @version Apr 29, 2021
     */
    private class SideBar extends JPanel
    {
        private JButton exit;
        private JList<String> list;
        private GridBagConstraints constraint = new GridBagConstraints();

        /**
         * Create a new SideBar object.
         */
        public SideBar()
        {
            setFeel();
            setLayout(new GridBagLayout());
            exit = new JButton("Exit");

            list = new JList<String>();

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

        /**
         * Updates the list of users.
         */
        public void updateUserList() {
            ArrayList<Match> matches = network.getMatches();
            ArrayList<String> userList = new ArrayList<String>();
            if (matches != null)
            {
                for (Match m: matches)
                {
                    userList.add(m.otherUser(username));
                }
                list = new JList<String>(new Vector<String>(userList));
                revalidate();
            }
        }
    }
}
