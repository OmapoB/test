import org.junit.jupiter.api.Test;
import ru.omarov.test.Runner;

public class AppTest {

    @Test
    public void test() throws InterruptedException {
        long start = System.currentTimeMillis();
        Runner runner = new Runner();
        runner.start();
        long stop = System.currentTimeMillis();
        System.out.printf("Matches: %s\nTime: %s\n", Runner.matches.get(), stop - start);
    }
}
