import java.io.Serializable;

public class User implements Serializable {
    public final String username;

    public User(String username) {
        this.username = username;
    }

    public String toString() {
        return String.format("User(%s)", username);
    }
}
