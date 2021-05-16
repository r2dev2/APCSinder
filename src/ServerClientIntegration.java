public class ServerClientIntegration
{
    private Process server;
    private Network network;
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
                System.out.printf("%20s ✅\n", test.name);
                success++;
            }
            else {
                System.out.printf("%20s ❌\n", test.name);
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

    // End Test

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
