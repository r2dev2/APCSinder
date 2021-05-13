import org.junit.*;
import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Justin Chang
 *  @version May 8, 2021
 */
public class JUTests
{
    private static String first = "Justin";
    private static String second = "Kevin";

    // Match.java
    @Test
    public void matchConstructor()
    {
        assertEquals(new Match(first, second).firstUser, "Justin");
        assertEquals(new Match(second, first).firstUser, "Justin");
    }

    @Test
    public void matchEquals()
    {
        assertEquals(new Match(first, second), new Match(second, first));
        assertNotEquals(new Match(first, second), new Match(first, first));

        assertEquals(new Match(first, second),
                     (Object) new Match(second, first));

        assertNotEquals(new Match(first, second),
                        (Object) new Match(first, first));
    }

    @Test
    public void matchHasUser()
    {
        assertTrue(new Match(first, second).hasUser(first));
        assertTrue(new Match(first, second).hasUser(second));
    }

    @Test
    public void matchOtherUser()
    {
        var m = new Match(first, second);
        assertEquals(m.otherUser(first), second);
        assertEquals(m.otherUser(second), first);
    }

    @Test
    public void matchHashCode()
    {
        assertEquals(new Match(first, second).hashCode(),
                     new Match(first, second).hashCode());
        assertEquals(new Match(first, second).hashCode(),
                     new Match(second, first).hashCode());
    }

    // BaseDB.java
    @Test
    public void baseDBSubscribe()
    {
        class State {
            int fired = 0;
            boolean continueSub = true;
        }
        var state = new State();
        var db = new BaseDB<String, String>(null, "nothin");
        db.subscribe("test", e -> {
            assertEquals(e, "event");
            state.fired++;
            return state.continueSub;
        });
        db.notifySubscriber("test", "event");
        assertEquals(state.fired, 1);
        state.continueSub = false;
        db.notifySubscriber("test", "event");
        assertEquals(state.fired, 2);
        db.notifySubscriber("test", "event");
        assertEquals(state.fired, 2);
    }

    // LoginResult.java
    @Test
    public void loginResultConstructor()
    {
        assertEquals(new LoginResult(true).success,
                new LoginResult(true).success);
        assertNotEquals(new LoginResult(true).success,
                new LoginResult(false).success);
    }

    @Test
    public void loginResultToken()
    {
        assertNotEquals(new LoginResult(true).token,
                new LoginResult(true).token);
    }

    // Serializer.java
    @Test
    public void serializerSerialize()
    {
        assertNotNull(Serializer.serialize(new Match("", "")));
    }

    @Test
    public void serializerDeserialize()
    {
        Match m =
            Serializer.deserialize(Serializer.serialize(new Match("", "")));
        assertNotNull(m.firstUser);
    }

    // MatchingDB.java
    @Test
    public void matchingDBAddPotential()
    {
        var db = new MatchingDB();
        db.addPotential(first, second);
        assertTrue(db.getPotential(first).contains(second));
        assertTrue(db.getPotential(second).contains(first));
    }

    @Test
    public void matchingDBRemovePotential()
    {
        var db = new MatchingDB();
        db.addPotential(first, second);
        db.removePotential(new Match(first, second));
        assertFalse(db.getPotential(first).contains(second));
        assertFalse(db.getPotential(second).contains(first));
    }

    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter(JUTests.class);
    }

    public static void main(String[] args)
    {
        org.junit.runner.JUnitCore.main("JUTests");
    }
}
