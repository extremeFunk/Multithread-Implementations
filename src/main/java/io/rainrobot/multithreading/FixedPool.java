package io.rainrobot.multithreading;

public class FixedPool {

    private BQueue<Runnable> taskQ;
    private Thread[] threads;

    private static long INTERVALS = 200;

    public FixedPool(int size) {
        this.taskQ = new BQueue<>(size);
        threads = new Thread[size - 1];
        for (int i = 0; i < size; i++) {
            threads[i] = new Thread(new ThreadLoop(i));
            threads[i].start();
        }
    }

    class ThreadLoop implements Runnable {
        private final int threadId;

        public ThreadLoop(int threadId) {
            this.threadId = threadId;
        }

        @Override
        public void run() {
            while (true) {
                taskQ.pop().run();
            }
        }
    }


    public void submit(Runnable runnable) {
        taskQ.add(runnable);
    }

    public void stop() {
        for (Thread t : threads) {
            t.interrupt();
        }
    }
}
