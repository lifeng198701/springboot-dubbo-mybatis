package com.lifeng.commons.util;

import java.util.EmptyStackException;
import java.util.LinkedList;

/**
 * Created by lifeng on 2018/5/19.
 */
public class LinkedStack<E> {
    private final LinkedList<E> list = new LinkedList();

    public LinkedStack() {
    }

    public int size() {
        return this.list.size();
    }

    public void clear() {
        this.list.clear();
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public E peek() {
        if(this.isEmpty()) {
            throw new EmptyStackException();
        } else {
            return this.list.getLast();
        }
    }

    public E pop() {
        if(this.isEmpty()) {
            throw new EmptyStackException();
        } else {
            return this.list.removeLast();
        }
    }

    public void push(E element) {
        this.list.addLast(element);
    }

    public E remove(int index) {
        E ret = this.list.remove(index);
        return ret;
    }

    public String toString() {
        return this.list.toString();
    }
}
