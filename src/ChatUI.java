import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;

public class ChatUI extends JFrame{
    private JScrollPane chatBoxScroll;
    private JTextArea chatBox;
    private JTextField messageBox;
    private JButton send;

    public ChatUI()
    {
        setTitle("APCSinder Chat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1000, 600);
        setFeel();

        messageBox = new JTextField(1);
        send = new JButton("Send");
        chatBox = getText();
        chatBoxScroll = new JScrollPane(chatBox);

        send.addActionListener(sendButtonListener());
        messageBox.addKeyListener(e -> sendText());

        add(send, BorderLayout.EAST);
        add(chatBoxScroll, BorderLayout.CENTER);
        add(messageBox, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void sendText()
    {
        String t = messageBox.getText();
        if (t.length() != 0) {
            String messageText = chatBox.getText();
            messageText += t + "\n";
            chatBox.setText(messageText);
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

    public static void main(String[] args)
    {
        ChatUI ui = new ChatUI();
    }
}
