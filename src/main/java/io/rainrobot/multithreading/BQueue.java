package io.rainrobot.multithreading;

import java.util.Queue;

public class BQueue <T extends Object> {

    private Object maxLock = new Object();
    private Object minLock = new Object();

    private final int maxSize;
    private Queue<T> q;

    public BQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized void add(T e) {
        while (q.size() == maxSize) {
            try {
                maxLock.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                if(q.size() == maxSize) {
                    add(e);
                }
            }
        }
        q.add(e);
        minLock.notify();

    }

    public synchronized T pop() {
        while (q.size() == 0) {
            try {
                minLock.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                if(q.size() == 0) {
                    pop();
                }
            }
        }
        T poll = q.poll();
        maxLock.notify();
        return poll;
    }


    public synchronized int size() {
        return q.size();
    }
}
