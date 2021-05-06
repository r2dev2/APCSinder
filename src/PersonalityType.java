import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

public class PersonalityType implements Serializable
{
    private final String type;
    private final static Map<String, String[]> preferredType =
        generatePreferredMap();

    public PersonalityType(boolean isE, boolean isS, boolean isT, boolean isJ)
    {
        type = String.format("%c%c%c%c",
            isE ? 'E' : 'I',
            isS ? 'S' : 'N',
            isT ? 'T' : 'F',
            isJ ? 'J' : 'P'
        );
    }

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

    public boolean equals(PersonalityType other)
    {
        return type.equals(other.type);
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
