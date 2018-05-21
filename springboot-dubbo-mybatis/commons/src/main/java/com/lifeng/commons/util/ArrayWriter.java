package com.lifeng.commons.util;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by lifeng on 2018/5/19.
 */
public final class ArrayWriter extends PrintWriter {
    private final ArrayList<String> alist = new ArrayList();

    public ArrayWriter() {
        super(new NullWriter());
    }

    public void print(Object o) {
        this.alist.add(o.toString());
    }

    public void print(char[] chars) {
        this.alist.add(new String(chars));
    }

    public void print(String s) {
        this.alist.add(s);
    }

    public void println(Object o) {
        this.alist.add(o.toString());
    }

    public void println(char[] chars) {
        this.alist.add(new String(chars));
    }

    public void println(String s) {
        this.alist.add(s);
    }

    public void write(char[] chars) {
        this.alist.add(new String(chars));
    }

    public void write(char[] chars, int off, int len) {
        this.alist.add(new String(chars, off, len));
    }

    public void write(String s, int off, int len) {
        this.alist.add(s.substring(off, off + len));
    }

    public void write(String s) {
        this.alist.add(s);
    }

    public String[] toStringArray() {
        int len = this.alist.size();
        String[] result = new String[len];

        for(int i = 0; i < len; ++i) {
            result[i] = (String)this.alist.get(i);
        }

        return result;
    }


}
