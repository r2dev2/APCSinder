import java.util.Map;
import java.util.HashMap;

public class PersonalityType
{
    private final String type;
    private final static Map<String, String> preferredType =
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

    public PersonalityType getPreferredType()
    {
        return new PersonalityType(preferredType.getOrDefault(type, type));
    }

    public int hashCode()
    {
        return type.hashCode();
    }

    public boolean equals(Object other)
    {
        return type.equals(other);
    }

    public String toString()
    {
        return type;
    }

    private static Map<String, String> generatePreferredMap()
    {
        Map<String, String> preferred = new HashMap<String, String>();
        String[][] matches = {
            {"ISTJ", "ESTP"}, {"INTP", "INTJ"}, {"ENFP", "INFJ"},
            {"ENTJ", "INFJ"}, {"ISFP", "ESFP"}
        };
        for (String[] types: matches) {
            preferred.put(types[0], types[1]);
            preferred.put(types[1], types[0]);
        }
        return preferred;
    }
}
