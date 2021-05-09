/**
 *  The Client class opens up the UI frontend for the User.
 *
 *  @author Justin Chang
 *  @version May 8, 2021
 */
public class Client
{
    /**
     * Starts the client frontend.
     * @param args an String array that contains the URL.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        System.out.println("Client program");
        String s = Serializer.serialize(new User("bruh",
                new PersonalityType(true, true, true, true)));
        User usr = Serializer.deserialize(s);
        System.out.println(s);
        System.out.println(usr);

        System.out.println("Login success: " + Serializer.serialize(
                    new LoginResult(true)));
        System.out.println("Login failure: " + Serializer.serialize(
                    new LoginResult(false)));

        // Network n = new Network();
        // AppContainer a = new AppContainer("Setup", n);

        String url = getServerURL(args);
        Network n = url == null
            ? new Network()
            : new Network(url);
        n.playground();
        Thread.sleep(100);
    }

    /**
     * Returns the url.
     * @param args the String array that contains the URL.
     * @return the url
     */
    private static String getServerURL(String[] args)
    {
        boolean isURL = false;
        for (String arg: args) {
            if (isURL) {
                return arg;
            }
            isURL = arg.equals("-s");
        }
        return null;
    }
}
