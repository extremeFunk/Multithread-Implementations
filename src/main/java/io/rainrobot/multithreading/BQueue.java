package io.rainrobot.multithreading;

import java.util.ArrayDeque;
import java.util.Queue;

public class BQueue <T extends Object> {

    private Queue<T> q;

    public BQueue() {
        synchronized (this) {
            q = new ArrayDeque<>();
        }
    }

    public boolean add(T e) {
        synchronized (this) {
            return q.add(e);
        }
    }

    public T pop() {
        synchronized (this) {
            return q.size() != 0 ? q.poll() : null;
        }
    }


    public T peek() {
        synchronized (this) {
            return q.size() != 0 ? q.peek() : null;
        }
    }

    public int size() {
        synchronized (this) {
            return q.size();
        }
    }
}
