import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatUI extends JPanel{
    private JScrollPane chatBoxScroll;
    private JTextArea chatBox;
    private JTextField messageBox;
    private JButton sendButton;
    private StringBuilder sb = new StringBuilder();
    private JButton exitButton;
    private String username;
    private AppContainer container;

    public ChatUI(String u, boolean visible, AppContainer a)
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
        add(exit, BorderLayout.NORTH);
        setVisible(visible);
    }

    private void sendText()
    {
        String t = messageBox.getText();
        if (t.length() != 0) {
            sb.append(username + ": " + t + "\n");
            chatBox.setText(sb.toString());
            messageBox.setText("");
        }
    }

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

    private JTextArea getText()
    {
        JTextArea t = new JTextArea();
        t.setEditable(false);
        t.setLineWrap(true);
        return t;
    }

    private class ExitBar extends JPanel {
        private JButton exit;

        public ExitBar() {
            setFeel();
            setLayout(new GridLayout(0, 9));
            exit = new JButton("Exit");

            add(exit);
        }

        public JButton getExitButton() {
            return exit;
        }
    }
}
