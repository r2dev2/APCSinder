import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JLabel welcome;
    private JLabel question;
    private JSlider slider;
    private JLabel answerLabel;
    private JButton nextButton;

    //~ Constructors ..........................................................
    public PersonalitySetupUI(String name) {
        username = name;
        test = new PersonalityTest(name);
        setLayout(new GridBagLayout());

        setFeel();

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

        start();
    }

    private class SliderListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider)e.getSource();
            answerLabel.setText("Your answer is: " + source.getValue());
        }
    }

    private class NextButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (test.hasNextQuestion()) {
                next();
            }
            else {
                finish();
            }
        }
    }
    //~Public  Methods ........................................................
    public void start() {
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
        slider.addChangeListener(new SliderListener());
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
        nextButton.addActionListener(new NextButtonListener());
        add(nextButton, constraint);
    }

    public void next() {
        test.answerQuestion(slider.getValue());
        test.nextQuestion();
        question.setText("<html><p>" + test.getQuestion() + "</p></html>");

        slider.setValue(5);

        if (!test.hasNextQuestion()) {
            nextButton.setText(" Finish ");
        }
    }

    public void finish() {
        Window win = SwingUtilities.getWindowAncestor(this);
        win.dispose();
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
