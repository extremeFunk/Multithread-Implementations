package io.rainrobot.multithreading;

public class FixedPool {

    BQueue<Task> taskQ;
    Thread[] thread;

    private static long INTERVALS = 200;

    public FixedPool(int size) {
        this.taskQ = new BQueue<>(size);
        thread = new Thread[size - 1];
        for (int i = 0; i < size; i++) {
            thread[i] = new Thread(new ThreadLoop(i));
            thread[i].start();
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
                Task task = taskQ.pop();
                task.run();
            }
        }
    }


    private void submit(Task task) {
        taskQ.add(task);
    }

}
