package io.rainrobot.multithreading;

public class FixedPool {

    BQueue<Task> taskQ;
    Thread[] thread;
    BQueue<Thread> threadQ;
    //for each thread[x] isThreadRunning[x] indicate if this
    //thread is in

    private static long INTERVALS = 200;

    public FixedPool(int size) {
        this.taskQ = new BQueue<>();
        thread = new Thread[size - 1];

        for (int i = 0; i < size; i++) {
            thread[i] = new Thread(new ThreadLoop(i));
            thread[i].run();
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
                if(task == null) {
                    try {
                        threadQ.add(thread[threadId]);
                        thread.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    task.run();
                }
            }
        }
    }

    public void run() {
        while (taskQ.size() > 0) {

        }
    }

    private void submit(Task task) {
        taskQ.add(task);
        synchronized (threadQ) {
            Thread trd = getAvailableThread();
            trd.notify();
        }
    }

    private Thread getAvailableThread() {
        while(true) {
            Thread t = threadQ.pop();
            if(t == null) {
                //no available thread
                //sleep and try again
                try {
                    Thread.sleep(INTERVALS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                return t;
            }

        }
    }
}
