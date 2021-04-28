import javax.swing.*;
import java.awt.*;

/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Kevin Li
 *  @version Apr 25, 2021
 */

public class PersonalitySetupUI extends JPanel
{
    //~ Fields ................................................................
    private String username;
    private PersonalityTest test;
    GridBagConstraints constraint = new GridBagConstraints();

    //~ Constructors ..........................................................
    public PersonalitySetupUI(String name) {
        username = name;
        test = new PersonalityTest(name);
        setLayout(new GridBagLayout());

        setFeel();

        JLabel welcome = new JLabel("<html><p>Welcome to APCSinder! Please take a quick personality"
            + " test before you can start matching.\nWe will ask you a series of 8 questions where"
            + " you will rate yourself on a scale of 0 to 10 on how close you identify with a"
            + " particular viewpoint. Please be as truthful as possible.</p></html>");
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.insets = new Insets(10, 10, 10, 10);
        constraint.weightx = 0.5;
        add(welcome, constraint);

        start();
    }
    //~Public  Methods ........................................................
    public void start() {
        test.startTest();
        JLabel question = new JLabel("<html><p>" + test.getQuestion() + "</p></html>");
        constraint.fill = GridBagConstraints.HORIZONTAL;
        constraint.gridx = 0;
        constraint.gridy = 1;
        constraint.insets = new Insets(10, 10, 10, 10);
        constraint.weightx = 0.5;
        add(question, constraint);
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
}
