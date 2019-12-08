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

    public synchronized boolean add(T e) {
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
        return q.add(e);

    }

    public synchronized T pop() {
        while (q.size() == 0) {
            try {
                maxLock.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                if(q.size() == 0) {
                    pop();
                }
            }
        }
        return q.poll();
    }


    public synchronized int size() {
        return q.size();
    }
}
