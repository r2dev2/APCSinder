import javax.swing.*;
import java.awt.*;

/**
 *  Write a one-sentence summary of your class here
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it
 *
 *  @author Kevin Li
 *  @version May 3, 2021
 */
public class CreateUserUI extends JPanel
{
    private AppContainer container;
    /**
     * Used to set up the formatting for each component of the JPanel.
     */
    private GridBagConstraints constraint = new GridBagConstraints();
    private JLabel welcome;
    private JLabel user;
    private JLabel pwd;
    private JTextField userInput;
    private JTextField pwdInput;
    private JButton nextButton;

    /**
     * Create a new CreateUserUI object.
     * Starts the UI from a specified container window.
     * @param a the container
     */
    public CreateUserUI(AppContainer a) {
        container = a;
        setLayout(new GridBagLayout());
        setFeel();
        start();
    }

    /**
     * Sets up the various elements of the UI (labels, text boxes,
     * next button)
     */
    public void start() {
        welcome = new JLabel("Create a new user");
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.insets = new Insets(20, 20, 20, 20);
        constraint.weightx = 0.5;
        constraint.gridwidth = 3;
        add(welcome, constraint);

        user = new JLabel("Username:");
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 0;
        constraint.gridy = 1;
        constraint.insets = new Insets(20, 20, 20, 20);
        constraint.weightx = 0.4;
        constraint.gridwidth = 1;
        add(user, constraint);

        userInput = new JTextField();
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 1;
        constraint.gridy = 1;
        constraint.insets = new Insets(20, 20, 20, 20);
        constraint.weightx = 0.3;
        constraint.gridwidth = 2;
        add(userInput, constraint);

        pwd = new JLabel("Password:");
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 0;
        constraint.gridy = 2;
        constraint.insets = new Insets(20, 20, 20, 20);
        constraint.weightx = 0.4;
        constraint.gridwidth = 1;
        add(pwd, constraint);

        pwdInput = new JTextField();
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 1;
        constraint.gridy = 2;
        constraint.insets = new Insets(20, 20, 20, 20);
        constraint.weightx = 0.3;
        constraint.gridwidth = 2;
        add(pwdInput, constraint);

        nextButton = new JButton(" Next ");
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 2;
        constraint.gridy = 3;
        constraint.insets = new Insets(20, 20, 20, 20);
        constraint.weightx = 0.1;
        constraint.gridwidth = 1;
        nextButton.addActionListener(e -> {
            container.completeSetup(userInput.getText(), pwdInput.getText());
            container.setupToPersonality();
        });
        add(nextButton, constraint);
    }

    /**
     * Sets the feel of the UI.
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
}
