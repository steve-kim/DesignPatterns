import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by steve-kim on 7/27/14.
 */
public class ThreadCount {
    public static void main(String[] args) {
        Semaphore a = new Semaphore(1);
        Semaphore b = new Semaphore(0);

        ExecutorService executors = Executors.newFixedThreadPool(2);

        printThread thread1 = new printThread(1, a, b);
        printThread thread2 = new printThread(2, b, a);

        executors.execute(thread1);
        executors.execute(thread2);
    }
    static class printThread implements Runnable {
        int counter;
        int limit = 100;
        Semaphore first;
        Semaphore second;

        public printThread(int counter, Semaphore first, Semaphore second) {
            this.counter = counter;
            this.first = first;
            this.second = second;
        }

        @Override
        public void run() {
            while (counter <= limit) {
                try {
                    first.acquire();
                    System.out.println(counter);
                    counter += 2;
                    second.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
