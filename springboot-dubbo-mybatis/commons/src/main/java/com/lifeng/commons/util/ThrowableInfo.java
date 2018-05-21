package com.lifeng.commons.util;

/**
 * Created by lifeng on 2018/5/19.
 */
public final class ThrowableInfo {
    private transient Throwable throwable;
    private String[] rep;

    public ThrowableInfo(Throwable throwable) {
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

    public String[] getThrowableStrArr() {
        if(this.throwable == null) {
            return null;
        } else if(this.rep != null) {
            return (String[])this.rep.clone();
        } else {
            ArrayWriter aw = new ArrayWriter();
            this.throwable.printStackTrace(aw);
            this.rep = aw.toStringArray();
            return this.rep;
        }
    }

    public String getThrowableStr() {
        String[] arr = this.getThrowableStrArr();
        if(arr == null) {
            return "";
        } else {
            StringBuilder strBuf = new StringBuilder();

            for(int i = 0; i < arr.length; ++i) {
                strBuf.append(arr[i]).append("\n");
            }

            return strBuf.toString();
        }
    }
}
