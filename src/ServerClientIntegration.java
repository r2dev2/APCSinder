public class ServerClientIntegration
{
    private Process server;
    private Network network;
    private Network network2;
    private TestCase[] tests;
    private static String first = "Justin";
    private static String other = "Kevin";
    private static PersonalityType type = new PersonalityType();
    private static PersonalityType otherType =
        new PersonalityType(true, false, true, true);
    private static String pass = "testing123";

    public ServerClientIntegration() throws Exception
    {
        tests = new TestCase[] {
            new TestCase("create user", this::testCreateUser),
            new TestCase("login user", this::testUserLogin),
            new TestCase("get user info", this::testGetUser),
            new TestCase("sub recommended matches", this::testPotentialMatchSubscribe),
            new TestCase("sub matching", this::testMatchSubscribe),
            new TestCase("sub messages", this::testMessageSubscribe),
        };
    }

    public void run() throws Exception
    {
        int success = 0;
        int fail = 0;
        for (var test: tests)
        {
            beforeTest();
            if (test.success()) {
                System.out.printf("%30s ✅\n", test.name);
                success++;
            }
            else {
                System.out.printf("%30s ❌\n", test.name);
                fail++;
            }
        }
        System.out.printf("Ran %d tests: %d pass, %d fail\n",
                success + fail, success, fail);
    }

    public void cleanUp()
    {
        server.destroy();
    }


    // Test
    private boolean testCreateUser() throws Exception
    {
        return network.createUser(new User(), pass);
    }

    private boolean testUserLogin() throws Exception
    {
        if (!network.createUser(new User(first, type), pass)) return false;
        return network.login(first, pass).success;
    }

    private boolean testGetUser() throws Exception
    {
        var user = new User(first, type);
        if (!network.createUser(new User(first, type), pass)) return false;
        if (!network.login(first, pass).success) return false;
        return network.getUser(first).description.equals(user.description);
    }

    private boolean testPotentialMatchSubscribe() throws Exception
    {
        class State {
            int fired = 0;
        }
        var s = new State();
        network.createUser(new User(first, type), pass);
        network.login(first, pass);
        network.subscribePotentialMatches(m -> s.fired++);
        Thread.sleep(50);
        network2.createUser(new User(other, otherType), pass);
        Thread.sleep(50);
        return s.fired == 1;
    }

    private boolean testMatchSubscribe() throws Exception
    {
        class State {
            int fired = 0;
        }
        var s = new State();
        network.createUser(new User(first, type), pass);
        network.login(first, pass);
        network2.createUser(new User(other, otherType), pass);
        network2.login(other, pass);
        network.subscribeMatches(m -> s.fired++);
        Thread.sleep(10);
        network.acceptMatch(new Match(first, other));
        network2.acceptMatch(new Match(first, other));
        Thread.sleep(10);
        return s.fired == 1;
    }

    private boolean testMessageSubscribe() throws Exception
    {
        class State {
            int fired = 0;
        }
        var s = new State();
        network.createUser(new User(first, type), pass);
        network.login(first, pass);
        network2.createUser(new User(other, otherType), pass);
        network2.login(other, pass);
        network.subscribeMatches(m -> {
            try {
                network.sendMessage(new Message("bruh", first, other));
            }
            catch (Exception e) {
                return;
            }
        });
        network.subscribeMessage(m -> s.fired += m.msg.equals("bruh") ? 1 : 0);
        network2.subscribeMessage(m -> s.fired += m.msg.equals("bruh") ? 1 : 0);
        Thread.sleep(10);
        network.acceptMatch(new Match(first, other));
        network2.acceptMatch(new Match(first, other));
        Thread.sleep(50);
        return s.fired == 2;
    }

    // End Test

    private void getServerOut()
    {
        var s = new java.util.Scanner(server.getInputStream());
        while (s.hasNextLine()) {
            System.out.println(s.nextLine());
        }
    }

    private void restartServer() throws Exception
    {
        if (server != null) {
            server.destroyForcibly();
            server.waitFor();
        }
        server = new ProcessBuilder("java", "Server", "--memory").start();
        new java.util.Scanner(server.getInputStream()).nextLine();
        Thread.sleep(50);
    }

    private void beforeTest() throws Exception
    {
        restartServer();
        network = new Network("http://localhost:8000");
        network2 = new Network("http://localhost:8000");
    }

    public static void main(String[] args) throws Exception
    {
        var integrationTest = new ServerClientIntegration();
        integrationTest.run();
        integrationTest.cleanUp();
    }

    private interface Test {
        public boolean run() throws Exception;
    }

    private static class TestCase
    {
        private final String name;
        public final Test test;

        public TestCase(String name, Test test)
        {
            this.name = name;
            this.test = test;
        }

        public boolean success()
        {
            try {
                return test.run();
            }
            catch (Exception e) {
                System.err.printf("%s error: %s", name, e);
                return false;
            }
        }
    }
}
