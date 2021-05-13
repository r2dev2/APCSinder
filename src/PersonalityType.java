import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

/**
 *  Calculates and holds a Myers-Briggs personality type, as well as
 *  a static Map to hold ideal matches for each personality type.
 *
 *  @author Ronak Badhe
 *  @version May 13, 2021
 */
public class PersonalityType implements Serializable
{
    private final String type;
    private final static Map<String, String[]> preferredType =
        generatePreferredMap();

    /**
     * Create a new PersonalityType object.
     * @param isE   extroversion T/F
     * @param isS   sensing T/F
     * @param isT   thinking T/F
     * @param isJ   judging T/F
     */
    public PersonalityType(boolean isE, boolean isS, boolean isT, boolean isJ)
    {
        type = String.format("%c%c%c%c",
            isE ? 'E' : 'I',
            isS ? 'S' : 'N',
            isT ? 'T' : 'F',
            isJ ? 'J' : 'P'
        );
    }

    /**
     * Create a new PersonalityType object with generic personality INFP.
     */
    public PersonalityType()
    {
        this(false, false, false, false);
    }

    /**
     * String constructor.
     *
     * private to make sure the string is a valid personality type
     *
     * @param type the personality type
     */
    private PersonalityType(String type)
    {
        this.type = type;
    }

    /**
     * Generates an array of ideally matching PersonalityTypes.
     * @return  array of PersonalityTypes
     */
    public PersonalityType[] getPreferredTypes()
    {
        String[] stringTypes = preferredType.get(type);
        PersonalityType[] preferredTypes = new PersonalityType[stringTypes.length];
        for (int i = 0; i < stringTypes.length; i++) {
            preferredTypes[i] = new PersonalityType(stringTypes[i]);
        }
        return preferredTypes;
    }

    public int hashCode()
    {
        return type.hashCode();
    }

    /**
     * Determines if PersonalityTypes are equal
     * @param other other personality type
     * @return  true if they have the same type, false otherwise
     */
    public boolean equals(PersonalityType other)
    {
        return type.equals(other.type);
    }

    public boolean equals(Object other)
    {
        try {
            return equals((PersonalityType) other);
        }
        catch (ClassCastException e) {
            return false;
        }
    }

    public String toString()
    {
        return type;
    }

    private static Map<String, String[]> generatePreferredMap()
    {
        Map<String, String[]> preferred = new HashMap<String, String[]>();
        String[] types = {
            "INFP", "ENFP", "INFJ", "ENFJ",
            "INTJ", "ENTJ", "INTP", "ENTP",
            "ISFP", "ESFP", "ISTP", "ESTP",
            "ISFJ", "ESFJ", "ISTJ", "ESTJ"};
        String[][] respectiveMatches = {
            {"ENFJ", "ENTJ"}, {"INFJ", "INTJ"}, {"ENFP", "ENTP"}, {"ENFJ", "ISFP"},
            {"ENFP", "ENTP"}, {"INFP", "INTP"}, {"ENTJ", "ESTJ"}, {"INFJ", "INTJ"},
            {"ENFJ", "ESFJ", "ESTJ"}, {"ISFJ", "ISTJ"}, {"ESFJ", "ESTJ"}, {"ISFJ", "ISTJ"},
            {"ESFP", "ESTP"}, {"ISFP", "ISTP"}, {"ESFP", "ESTP"}, {"INTP", "ISFP", "ISTP"}
        };
        for (int i = 0; i < types.length; i++) {
            preferred.put(types[i], respectiveMatches[i]);
        }
        return preferred;
    }
}
