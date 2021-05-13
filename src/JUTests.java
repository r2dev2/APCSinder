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
    @Test
    public void bruh()
    {

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
