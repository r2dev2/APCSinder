import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *  The LoginUI class creates a login UI for the user to login.
 *
 *  @author Justin Chang
 *  @version May 15, 2021
 */
public class LoginUI extends JPanel
{
    private AppContainer container;
    private Network network;
    /**
     * Used to set up the formatting for each component of the JPanel.
     */
    private GridBagConstraints constraint = new GridBagConstraints();
    private JLabel welcome;
    private JLabel user;
    private JLabel pwd;
    private JLabel wrongPassword;
    private JTextField userInput;
    private JPasswordField pwdInput;
    private JButton loginButton;
    private JButton backButton;

    /**
     * Create a new LoginUI object.
     * Starts the UI from a specified container window.
     * @param a the container
     * @param network the network to connect to
     */
    public LoginUI(AppContainer a, Network network) {
        container = a;
        this.network = network;
        setLayout(new GridBagLayout());
        setFeel();
        start();
    }

    /**
     * Sets up the various elements of the UI (labels, text boxes,
     * next button)
     */
    public void start() {
        welcome = new JLabel("<html><p>Log in to your existing account. If "
            + "you're new, click the button on the bottom left.</p></html>");
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.insets = new Insets(20, 40, 20, 40);
        constraint.weightx = 0.5;
        constraint.gridwidth = 3;
        add(welcome, constraint);

        user = new JLabel("Username:");
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 0;
        constraint.gridy = 1;
        constraint.insets = new Insets(20, 40, 20, 20);
        constraint.weightx = 0.4;
        constraint.gridwidth = 1;
        add(user, constraint);

        userInput = new JTextField();
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 1;
        constraint.gridy = 1;
        constraint.insets = new Insets(20, 20, 20, 40);
        constraint.weightx = 0.5;
        constraint.gridwidth = 2;
        add(userInput, constraint);

        pwd = new JLabel("Password:");
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 0;
        constraint.gridy = 2;
        constraint.insets = new Insets(20, 40, 20, 20);
        constraint.weightx = 0.4;
        constraint.gridwidth = 1;
        add(pwd, constraint);

        pwdInput = new JPasswordField();
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 1;
        constraint.gridy = 2;
        constraint.insets = new Insets(20, 20, 20, 40);
        constraint.weightx = 0.5;
        constraint.gridwidth = 2;
        add(pwdInput, constraint);

        wrongPassword = new JLabel("");
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 0;
        constraint.gridy = 3;
        constraint.insets = new Insets(20, 40, 20, 20);
        constraint.weightx = 0.4;
        constraint.gridwidth = 1;
        add(wrongPassword, constraint);

        backButton = new JButton("New User?");
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 1;
        constraint.gridy = 3;
        constraint.insets = new Insets(20, 40, 20, 40);
        constraint.weightx = 0.2;
        constraint.gridwidth = 1;
        add(backButton, constraint);
        backButton.addActionListener(e -> {
            container.getNextWindow(); //go to CreateUserUI
        });

        loginButton = new JButton(" Login ");
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 2;
        constraint.gridy = 3;
        constraint.insets = new Insets(20, 40, 20, 40);
        constraint.weightx = 0.2;
        constraint.gridwidth = 1;
        loginButton.addActionListener(e -> {
            try
            {
                String password = new String(pwdInput.getPassword());
                LoginResult login = network.login(userInput.getText(), password);
                if (login.success)
                {
                    User u = network.getUser(userInput.getText());
                    container.completeSetup(u.username, password, u.description);
                    container.getNextWindow();
                    container.getNextWindow(); //jumps directly to Matching
                }
                else {
                    wrongPassword.setText("Wrong password!");
                }
            }
            catch (IOException | InterruptedException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        add(loginButton, constraint);
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
