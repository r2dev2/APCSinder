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
     *
     * @param args cli args: -s serverurl
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        System.out.println("Client program");

        String url = getServerURL(args);
        Network n = url == null
            ? new Network()
            : new Network(url);
        AppContainer a = new AppContainer(n);
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
