package io.rainrobot.multithreading;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;


public class BQueueTest {

    @Test
    public void simpleRemove() {
        BQueue<Integer> bq = new BQueue<>();

        bq.add(1);

        assertEquals(1, bq.poll().intValue());

        assertNull(bq.poll());
    }

    @Test
    public void safeAccessToQ() throws Exception {
        ExecutorService ex = Executors.newCachedThreadPool();
        for (int l = 0; l < 3; l++) {
            BQueue<Integer> q = populateQWith100Elements(ex);
            assertEquals(100, q.size());
        }
    }

    private BQueue<Integer> populateQWith100Elements(ExecutorService ex) throws InterruptedException {
        BQueue<Integer> bq = new BQueue<>();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 2; j++) {
                ex.execute(() -> bq.add(1));
            }
            for (int k = 0; k < 1; k++) {
                ex.execute(() -> bq.remove());
            }
        }
        sleep(1000);
        return bq;
    }
}