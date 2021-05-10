import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  The UI end for setting up a user's personality traits.
 *  Guides the user through the personality questions. Has a PersonalityTest
 *  field to retrieve and store questions and answers. Is used by AppContainer.
 *
 *  @author Kevin Li
 *  @version Apr 25, 2021
 */

public class PersonalitySetupUI extends JPanel
{
    // Fields ................................................................
    private String username;
    private PersonalityTest test;
    private Network network;

    /**
     * Used to set up the formatting for each component of the JPanel.
     */
    private GridBagConstraints constraint = new GridBagConstraints();
    private JLabel welcome;
    private JLabel question;
    private JSlider slider;
    private JLabel answerLabel;
    private JButton nextButton;
    private AppContainer container;

    // Constructors ..........................................................
    /**
     * Create a new PersonalitySetupUI object.
     * Sets up instance variables and basic layout accordingly, then starts up the
     * rest of the UI.
     * @param name User name
     * @param a a reference to the AppContainer that uses this class
     * @param network the network to connect to
     * @param password user's password
     */
    public PersonalitySetupUI(String name, AppContainer a, Network ntwk, String pwd, String desc)
    {
        username = name;
        test = new PersonalityTest(name, ntwk, pwd, desc);
        container = a;
        this.network = ntwk;

        setLayout(new GridBagLayout());
        setFeel();

        start();
    }

    //Public  Methods ........................................................
    /**
     * Sets up the main components of the UI: a welcome message at the top, then the
     * question on the left, a slider on the right, a label with the current response on the
     * bottom left, and the next button on the bottom right.
     *
     */
    public void start()
    {
        welcome = new JLabel("<html><p>Welcome to APCSinder! Please take a quick personality"
            + " test before you can start matching.\nWe will ask you a series of 8 questions where"
            + " you will rate yourself on a scale of 0 to 10 on how close you identify with a"
            + " particular viewpoint. Please be as truthful as possible.</p></html>");
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.insets = new Insets(10, 10, 10, 10);
        constraint.weightx = 0.5;
        constraint.gridwidth = 2;
        add(welcome, constraint);

        question = new JLabel("<html><p>" + test.getQuestion() + "</p></html>");
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 0;
        constraint.gridy = 1;
        constraint.insets = new Insets(10, 10, 10, 10);
        constraint.weightx = 0.5;
        constraint.gridwidth = 1;
        add(question, constraint);

        slider = new JSlider(0, 10);
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 1;
        constraint.gridy = 1;
        constraint.insets = new Insets(10, 10, 10, 10);
        constraint.weightx = 0.3;
        constraint.gridwidth = 1;
        slider.setPaintTicks(true);
        slider.setPaintTrack(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(5);
        slider.setMinorTickSpacing(1);
        slider.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            answerLabel.setText("Your answer is: " + source.getValue());
        });
        add(slider, constraint);

        answerLabel = new JLabel();
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 0;
        constraint.gridy = 2;
        constraint.insets = new Insets(10, 10, 10, 10);
        constraint.weightx = 0.5;
        constraint.gridwidth = 1;
        answerLabel.setText("Your answer is: " + slider.getValue());
        add(answerLabel, constraint);

        nextButton = new JButton(" Next ");
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 1;
        constraint.gridy = 2;
        constraint.insets = new Insets(10, 10, 10, 10);
        constraint.weightx = 0.2;
        constraint.gridwidth = 1;
        nextButton.addActionListener(e -> {
            if (test.hasNextQuestion()) {
                next();
            }
            else {
                finish();
            }
        });
        add(nextButton, constraint);
    }

    /**
     * Refreshes the contents on the screen for the next question.
     */
    public void next()
    {
        test.answerQuestion(slider.getValue());
        test.nextQuestion();
        question.setText("<html><p>" + test.getQuestion() + "</p></html>");

        slider.setValue(5);

        if (!test.hasNextQuestion()) {
            nextButton.setText(" Finish ");
        }
    }

    /**
     * Closes up the personality setup and proceeds with matching (which is specified by
     * another class).
     */
    public void finish()
    {
        test.answerQuestion(slider.getValue());
        test.finishTest();
        container.personalityToMatching();
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
