package com.lifeng.commons.util;

import java.io.Writer;

/**
 * Created by lifeng on 2018/5/19.
 */
public class NullWriter  extends Writer {
    NullWriter() {
    }

    public void close() {
    }

    public void flush() {
    }

    public void write(char[] cbuf, int off, int len) {
    }
}
