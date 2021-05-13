/**
 *  Back-end class of the personality test.
 *  Holds the test questions, supplies the current question to the UI,
 *  stores answers, and calculates personality type from answers.
 *
 *  @author Kevin Li
 *  @version May 13, 2021
 */
public class PersonalityTest
{
    private final String[] questions = {"Do you like spending time in large "
        + "groups, where you get more energy (0),"
        + " or do you get more energy recharging by yourself, preferring "
        + "social interactions to be one-on-one (10)?",
        // E/I
        "Are you the one who normally takes the first step (0), or are you "
        + "more deliberate and better at reflecting than acting (10)?",
        // E/I
        "Do you like to interpret things literally, preferring solid facts "
        + "(0), or do you like to spend more time looking for meaning and "
        + "possibilities, in a philosophical way (10)?",
        // S/N
        "Do you describe yourself as standard, usual, and conventional (0), "
        + "or different, novel, and unique (10)?",
        // S/N
        "Are you firm, logical, thinking, and questioning (0), or gentle, "
        + "empathetic, feeling, and accomodating (10)?",
        // T/F
        "Are you candid, straightforward, and frank (0), or kind, tactful, "
        + "and encouraging (10)?",
        // T/F
        "Is keeping things organized and planned out a priority (0), or do "
        + "you like to be more spontaneous and flexible (10)?",
        // J/P
        "Would you rather have your life be more structured and have a sense "
        + "of control (0), or do you prefer to be more easygoing and go with"
        + "the flow (10)?"};
        // J/P
    private int[] answers;
    private int index = 0;

    /**
     * Create a new PersonalityTest object.
     */
    public PersonalityTest()
    {
        answers = new int[questions.length];
    }

    /**
     * Retrieves the current question based on the current index.
     * @return  the current question (String)
     */
    public String getQuestion()
    {
        return questions[index];
    }

    /**
     * Determines if there is another question left to answer.
     * @return  true if yes, false if not
     */
    public boolean hasNextQuestion()
    {
        return index < 7;
    }

    /**
     * Stores the numerical answer to a question in an array.
     * @param answer    answer to the current question
     */
    public void answerQuestion(int answer)
    {
        answers[index] = answer;
    }

    /**
     * Goes to the next question by incrementing the index.
     */
    public void nextQuestion()
    {
        index++;
    }

    /**
     * Calculates the personality from the answers, then creates
     * a new User with the given name and personality. Sends this
     * User to the network to register on the server end.
     */
    public PersonalityType finishTest()
    {
        return calculate();
    }

    /**
     * personalityType.charAt(0) == 'E' if answers[0] + answers[1] <= 10,
     * 'I' otherwise
     * personalityType.charAt(1) == 'S' if answers[2] + answers[3] <= 10,
     * 'N' otherwise
     *      …and so on.
     */
    private PersonalityType calculate()
    {
        return new PersonalityType(
            sumLessThan(0, 1, 10), sumLessThan(2, 3, 10),
            sumLessThan(4, 5, 10), sumLessThan(6, 7, 10)
        );
    }

    private boolean sumLessThan(int i, int j, int target)
    {
        return answers[i] + answers[j] <= target;
    }
}
