public class PersonalityTest {
    private String username;
    private final String[] questions = {"Do you like spending time in large groups, where you get more energy (0),"
        + " or do you get more energy recharging by yourself, preferring social interactions to"
        + " be one-on-one (10)?", // E/I
        "Are you the one who normally takes the first step (0), or are you more deliberate and"
        + "better at reflecting than acting (10)?", // E/I
        "Do you like to interpret things literally, preferring solid facts (0), or do you like to spend more" +
        " time looking for meaning and possibilities, in a philosophical way (10)?", // S/N
        "Do you describe yourself as standard, usual, and conventional (0), or different, novel, and" +
        " unique (10)?", // S/N
        "Are you firm, logical, thinking, and questioning (0), or gentle, empathetic, feeling, and" +
        " accomodating (10)?", // T/F
        "Are you candid, straightforward, and frank (0), or kind, tactful, and encouraging (10)?", // T/F
        "Is keeping things organized and planned out a priority (0), or do you like to be more" +
        " spontaneous and flexible (10)?", // J/P
        "Would you rather have your life be more structured and have a sense of control (0), or do" +
        " you prefer to be more easygoing and go with the flow (10)?"}; // J/P;
    private int[] answers;
    private int index = 0;
    public PersonalityTest(String name) {
        username = name;
        answers = new int[8];
    }

    public String getQuestion() {
        return questions[index];
    }

    public boolean hasNextQuestion() {
        return index < 7;
    }

    public void answerQuestion(int answer) {
        answers[index] = answer;
    }

    public void nextQuestion() {
        index++;
    }

    public void finishTest() {
        //TODO
    }

    /**
     * personalityType.charAt(0) == 'E' if answers[0] + answers[1] <= 10,
     * 'I' otherwise
     * personalityType.charAt(1) == 'S' if answers[2] + answers[3] <= 10,
     * 'N' otherwise
     *      …and so on.
     *
     * Types that match well together:
     * ISTJ + ESTP
     * INTP + INTJ
     * ENFP + INFJ
     * ENTJ + ISTP
     * ISFP + ESFP
     */
    private String calculate() {
        //TODO algorithm needs fixing
        // sorry this conversion is quite brute force, trigger warning
        StringBuffer personType = new StringBuffer();
        if (answers[0] + answers[1] <= 10) {
            personType.append("E");
        }
        else {
            personType.append("I");
        }
        if (answers[2] + answers[3] <= 10) {
            personType.append("S");
        }
        else {
            personType.append("N");
        }
        if (answers[4] + answers[5] <= 10) {
            personType.append("T");
        }
        else {
            personType.append("F");
        }
        if (answers[6] + answers[7] <= 10) {
            personType.append("J");
        }
        else {
            personType.append("P");
        }

        return personType.toString();
    }

}
