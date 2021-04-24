import java.io.Serializable;

public class Match implements Serializable
{
    public final String firstUser;
    public final String secondUser;
    public final float score;

    public Match(String firstUser, String secondUser, float score)
    {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.score = score;
    }

    public String toString()
    {
        return String.format("Match(%s, %s, %3f)", firstUser, secondUser, score);
    }
}
