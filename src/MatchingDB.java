import java.util.*;

/**
 * A database for storing potential matches.
 *
 * @author Ronak Badhe
 * @version May 8, 2021
 */
public class MatchingDB extends BaseDB<HashMap<String, HashSet<String>>, Match>
{
    private HashMap<String, HashSet<String>> potential;

    /**
     * Constructor.
     *
     * @param filename the file to persist to, if null, don't persist
     */
    public MatchingDB(String filename)
    {
        super(filename, new HashMap<String, HashSet<String>>());
        potential = load();
    }

    /**
     * Default constructor.
     *
     * An in-memory MatchingDB.
     */
    public MatchingDB()
    {
        this(null);
    }

    /**
     * Adds a potential match.
     *
     * @param first the first user
     * @param second the second user
     */
    public void addPotential(String first, String second)
    {
        var pot = potential.getOrDefault(first, new HashSet<String>());
        if (pot.contains(second)) return;
        pot.add(second);
        potential.put(first, pot);
        notifySubscriber(first, new Match(first, second));
        addPotential(second, first);
        save(potential);
    }

    /**
     * Adds a potential match.
     *
     * @param m the match to add
     */
    public void addPotential(Match m)
    {
        addPotential(m.firstUser, m.secondUser);
    }

    /**
     * Removes a potential match.
     *
     * @param first the first user
     * @param second the second user
     */
    public void removePotential(String first, String second)
    {
        potential.getOrDefault(first, new HashSet<String>()).remove(second);
        potential.getOrDefault(second, new HashSet<String>()).remove(first);
        save(potential);
    }

    /**
     * Removes a potential match.
     *
     * @param m the match to remove
     */
    public void removePotential(Match m)
    {
        removePotential(m.firstUser, m.secondUser);
    }

    /**
     * Gets the potential matches that a user has.
     *
     * @param user the user to get the matches of
     * @return the potential matches of the user
     */
    public ArrayList<String> getPotential(String user)
    {
        return new ArrayList<>(potential.getOrDefault(user, new HashSet<>()));
    }
}
