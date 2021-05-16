public class ServerClientIntegration
{
    private Process server;
    private Network network;
    private TestCase[] tests;

    public ServerClientIntegration() throws Exception
    {
        tests = new TestCase[] {
            new TestCase("create user", this::testCreateUser)
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
                System.out.printf("%s ✅\n", test.name);
                success++;
            }
            else {
                System.out.printf("%s ❌\n", test.name);
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

    private boolean testCreateUser() throws Exception
    {
        var reportedSuccess = network.createUser(new User(), "testing123");
        return reportedSuccess;
    }

    private void restartServer() throws Exception
    {
        if (server != null) {
            server.destroy();
        }
        server = new ProcessBuilder("java", "Server", "--memory").start();
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
                return false;
            }
        }
    }
}
