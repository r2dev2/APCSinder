import java.util.*;

public class MatchingDB extends BaseDB<HashMap<String, HashSet<String>>, Match>
{
    HashMap<String, HashSet<String>> potential;

    public MatchingDB(String filename)
    {
        super(filename, new HashMap<String, HashSet<String>>());
        potential = load();
    }

    public void addPotential(String first, String second)
    {
        var pot = potential.getOrDefault(first, new HashSet<String>());
        if (pot.contains(second)) return;
        pot.add(second);
        potential.put(first, pot);
        notifySubscriber(new Match(first, second), first);
        addPotential(second, first);
        save(potential);
    }

    public void addPotential(Match m)
    {
        addPotential(m.firstUser, m.secondUser);
    }

    public void removePotential(String first, String second)
    {
        potential.getOrDefault(first, new HashSet<String>()).remove(second);
        potential.getOrDefault(second, new HashSet<String>()).remove(first);
        save(potential);
    }

    public void removePotential(Match m)
    {
        removePotential(m.firstUser, m.secondUser);
    }

    public ArrayList<String> getPotential(String user)
    {
        return new ArrayList<>(potential.getOrDefault(user, new HashSet<>()));
    }
}
