package chatUI;

import javax.swing.*;
import java.awt.BorderLayout;

public class ChatUI extends JFrame{
    private JScrollPane chatBoxScroll;
    private JTextArea chatBox;
    private JTextField messageBox;
    private JButton send;

    public ChatUI() {
        setTitle("Hello World");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1000, 600);
        setFeel();

        messageBox = new JTextField(1);
        send = new JButton("Send");
        chatBox = getText();
        chatBoxScroll = new JScrollPane(chatBox);

        send.addActionListener(new SendButtonMessageListener(messageBox, chatBox));
        //Can't find a way to implement an enter listener

        add(send, BorderLayout.EAST);
        add(chatBoxScroll, BorderLayout.CENTER);
        add(messageBox, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void setFeel() {
        try {
            UIManager.setLookAndFeel (
                    "javax.swing.plaf.nimbus.NimbusLookAndFeel"
            );
        } catch (Exception e) {
            System.out.println("This is never supposed to get called");
        }
    }

    private JTextArea getText() {
        JTextArea t = new JTextArea();
        t.setEditable(false);
        t.setLineWrap(true);
        return t;
    }

    public static void main(String[] args) {
        ChatUI ui = new ChatUI();
    }
}
