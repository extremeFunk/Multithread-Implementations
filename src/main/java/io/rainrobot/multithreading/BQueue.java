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

    public boolean offer(T e) {
        synchronized (this) {
            return q.offer(e);
        }
    }

    public T remove() {
        synchronized (this) {
            return q.remove();
        }
    }

    public T poll() {
        synchronized (this) {
            return q.poll();
        }
    }

    public T element() {
        synchronized (this) {
            return q.element();
        }
    }

    public T peek() {
        synchronized (this) {
            return q.peek();
        }
    }

    public int size() {
        synchronized (this) {
            return q.size();
        }
    }
}
