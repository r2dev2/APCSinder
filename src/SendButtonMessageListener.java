package chatUI;

import java.awt.event.*;
import javax.swing.*;

public class SendButtonMessageListener implements ActionListener
{
    private JTextField message;
    private JTextArea chat;

    public SendButtonMessageListener(JTextField messageBox,
        JTextArea chatBox) {
        message = messageBox;
        chat = chatBox;
    }

    public void actionPerformed(ActionEvent e)
    {
        String t = message.getText();
        if (t.length() != 0) {
            String messageText = chat.getText();
            messageText += t + "\n";
            chat.setText(messageText);
            message.setText("");
        }
    }
}
