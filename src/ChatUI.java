import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatUI extends JPanel{
    private JScrollPane chatBoxScroll;
    private JTextArea chatBox;
    private JTextField messageBox;
    private JButton send;
    private StringBuilder sb = new StringBuilder();
    private ExitBar exit;
    private JButton exitButton;

    public ChatUI(boolean t)
    {
        setLayout(new BorderLayout());
        setFeel();

        messageBox = new JTextField(1);
        send = new JButton("Send");
        chatBox = getText();
        chatBoxScroll = new JScrollPane(chatBox);
        exit = new ExitBar();
        exitButton = exit.getExitButton(); //currently does nothing

        send.addActionListener(e -> sendText());
        messageBox.addKeyListener(enterSendListener());

        add(send, BorderLayout.EAST);
        add(chatBoxScroll, BorderLayout.CENTER);
        add(messageBox, BorderLayout.SOUTH);
        add(exit, BorderLayout.NORTH);
        setVisible(t);
    }

    private void sendText()
    {
        String t = messageBox.getText();
        if (t.length() != 0) {
            sb.append(t + "\n");
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
        private JButton exitButton;

        public ExitBar() {
            setFeel();
            setLayout(new GridLayout(0, 9));
            exitButton = new JButton("Exit");

            add(exitButton);
        }

        public JButton getExitButton() {
            return exitButton;
        }
    }
}