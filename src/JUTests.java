import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Ronak Badhe
 *  @version May 8, 2021
 */
public class JUTests
{
    private static String first = "Justin";
    private static String second = "Kevin";
    private static PersonalityType type = new PersonalityType();
    private static PersonalityType otherType =
        new PersonalityType(true, false, true, true);

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

    // MessageDB.java
    @Test
    public void messageDBAdd()
    {
        var db = new MessageDB();
        var m = new Message("bruh", first, second);
        db.add(m);
        assertTrue(db.get(new Match(first, second)).contains(m));
    }

    @Test
    public void messageDBSubscribe()
    {
        class State {
            int fired = 0;
        }
        var state = new State();
        var db = new MessageDB();
        db.subscribe(first, m -> {
            state.fired++;
            assertEquals(m.msg, "bruh");
            return true;
        });
        db.add(new Message("bruh", first, second));
        assertEquals(state.fired, 1);
        db.add(new Message("bruh", second, first));
        assertEquals(state.fired, 2);
    }

    // UserDB.java
    @Test
    public void userDBCreateUser()
    {
        var db = new UserDB();
        assertTrue(db.createUser(new UserCreationAttempt(
                        new User(first, new PersonalityType()), second)));
        assertFalse(db.createUser(new UserCreationAttempt(
                        new User(first, new PersonalityType()), first)));
    }

    @Test
    public void userDBLogin()
    {
        var db = new UserDB();
        db.createUser(new UserCreationAttempt(
                    new User(first, new PersonalityType()), second));
        assertFalse(db.login(first, first).success);
        assertFalse(db.login(second, first).success);
        assertTrue(db.login(first, second).success);
    }

    @Test
    public void userDBAuthenticate()
    {
        var db = new UserDB();
        db.createUser(new UserCreationAttempt(
                    new User(first, new PersonalityType()), second));
        var token = db.login(first, second).token;
        assertTrue(db.authenticate(first, token));
        assertFalse(db.authenticate(second, token));
        assertFalse(db.authenticate(first, "null"));
        assertFalse(db.authenticate(first, null));
    }

    @Test
    public void userDBSubscribePotentialMatches()
    {
        class State {
            int fired = 0;
        }
        var state = new State();
        var db = new UserDB();
        db.createUser(new UserCreationAttempt(
                    new User(first, type), second));
        db.subscribePotentialMatches(first, match -> {
            state.fired++;
            return true;
        });
        db.createUser(new UserCreationAttempt(
                    new User(second, otherType), second));
        assertEquals(state.fired, 1);
    }

    @Test
    public void userDBAccept()
    {
        class State {
            int fired = 0;
        }
        var state = new State();
        var db = new UserDB();
        db.createUser(new UserCreationAttempt(
                    new User(first, type), second));
        db.createUser(new UserCreationAttempt(
                    new User(second, otherType), second));
        db.subscribe(first, match -> {
            assertTrue(match.hasUser(first));
            state.fired++;
            return true;
        });
        db.subscribe(second, match -> {
            assertTrue(match.hasUser(second));
            state.fired++;
            return true;
        });

        db.accept(first, second);
        assertEquals(state.fired, 0);
        db.accept(first, second);
        assertEquals(state.fired, 0);
        db.accept(second, first);
        assertEquals(state.fired, 2);
        db.accept(first, second);
        db.accept(second, first);
        assertEquals(state.fired, 2);
    }

    @Test
    public void userDBReject()
    {
        class State {
            int fired = 0;
        }
        var state = new State();
        var db = new UserDB();
        db.createUser(new UserCreationAttempt(
                    new User(first, type), second));
        db.createUser(new UserCreationAttempt(
                    new User(second, otherType), second));
        db.subscribe(first, match -> {
            assertTrue(match.hasUser(first));
            state.fired++;
            return true;
        });
        db.subscribe(second, match -> {
            assertTrue(match.hasUser(second));
            state.fired++;
            return true;
        });

        db.accept(first, second);
        db.accept(first, second);
        db.reject(second, first);
        assertEquals(state.fired, 0);
        assertFalse(db.getPotentialMatches(second).contains(first));
    }

    private boolean contains(Object[] arr, Object obj)
    {
        for (var item: arr) {
            if (obj.equals(item)) {
                return true;
            }
        }
        return false;
    }

    // PersonalityType.java
    @Test
    public void personalityTypeGetPreferredTypes()
    {
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                for (int k = 0; k < 2; ++k) {
                    for (int l = 0; l < 2; ++l) {
                        var type = new PersonalityType(i == 0, j == 0,
                                k == 0, l == 0);
                        for (var p: type.getPreferredTypes()) {
                            var preferred = p.getPreferredTypes();
                            // if one type prefers the other
                            // the other should also prefer the type
                            var assertion = String.format(
                                "PersonalityType %s expected to contain %s in %s",
                                p, type, Arrays.toString(preferred)
                            );
                            assertTrue(assertion,
                                    contains(preferred, type));
                        }
                    }
                }
            }
        }
    }

    @Test
    public void personalityTypeHashCode()
    {
        assertEquals(type.hashCode(), new PersonalityType().hashCode());
        assertNotEquals(type.hashCode(), otherType.hashCode());
    }

    @Test
    public void personalityTypeEquals()
    {
        assertEquals(type, new PersonalityType());
        assertEquals(type, (Object) new PersonalityType());
        assertNotEquals(type, otherType);
        assertNotEquals(type, (Object) otherType);
    }

    // PersonalityTest.java
    @Test
    public void personalityTestGetQuestion()
    {
        var test = new PersonalityTest();
        var questions = new HashSet<String>();
        int totalNum = 0;
        while (test.hasNextQuestion()) {
            questions.add(test.getQuestion());
            test.nextQuestion();
            totalNum++;
        }
        assertEquals(totalNum, questions.size());
    }

    @Test
    public void personalityTestAnswerQuestion()
    {
        var test = new PersonalityTest();
        var test2 = new PersonalityTest();
        test.answerQuestion(10);
        test.nextQuestion();
        test.answerQuestion(10);
        assertNotEquals(test.finishTest(), test2.finishTest());
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
