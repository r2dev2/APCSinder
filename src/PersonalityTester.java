import javax.swing.JFrame;

public class PersonalityTester extends JFrame {
    private String username;
    private PersonalitySetupUI personality;

    public PersonalityTester(String username) {
        this.username = username;
        setSize(1000, 600);
        setTitle("APCSinder Personality - " + username);

        personality = new PersonalitySetupUI(username);

        add(personality);
        setVisible(true);
    }

    public static void main(String[] args) {

    }
}