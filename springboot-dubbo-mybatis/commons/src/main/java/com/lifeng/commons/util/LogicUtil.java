package com.lifeng.commons.util;

import java.util.Collection;
import java.util.Map;

/**
 * Created by lifeng on 2018/5/19.
 */
public class LogicUtil {
    private static int ZERO = 0;
    private static String EMPTY_STRING = "";

    public LogicUtil() {
    }

    public static boolean isNullOrEmpty(String subject) {
        return null == subject || EMPTY_STRING.equals(subject);
    }

    public static boolean isNotNullAndEmpty(String subject) {
        return !isNullOrEmpty(subject);
    }

    public static boolean isNullOrEmpty(Map map) {
        return null == map || ZERO == map.size();
    }

    public static boolean isNotNullAndEmpty(Map map) {
        return !isNullOrEmpty(map);
    }

    public static boolean isNullOrEmpty(Collection<?> collection) {
        return null == collection || ZERO == collection.size();
    }

    public static boolean isNotNullAndEmpty(Collection collection) {
        return !isNullOrEmpty(collection);
    }

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean isNotNull(Object Object) {
        return !isNull(Object);
    }

    public static boolean isNullOrEmpty(Object[] objects) {
        return null == objects || ZERO == objects.length;
    }

    public static boolean isNotNullAndEmpty(Object[] objects) {
        return !isNullOrEmpty(objects);
    }

    public static boolean isNullOrEmpty(byte[] array) {
        return null == array || ZERO == array.length;
    }

    public static boolean isNotNullAndEmpty(byte[] array) {
        return !isNullOrEmpty(array);
    }

    public static boolean isNullOrEmpty(short[] array) {
        return null == array || ZERO == array.length;
    }

    public static boolean isNotNullAndEmpty(short[] array) {
        return !isNullOrEmpty(array);
    }

    public static boolean isNullOrEmpty(int[] array) {
        return null == array || ZERO == array.length;
    }

    public static boolean isNotNullAndEmpty(int[] array) {
        return !isNullOrEmpty(array);
    }

    public static boolean isNotNullAndEmpty(long[] array) {
        return !isNullOrEmpty(array);
    }

    public static boolean isNullOrEmpty(long[] array) {
        return null == array || ZERO == array.length;
    }

    public static boolean isNullOrEmpty(char[] array) {
        return null == array || ZERO == array.length;
    }

    public static boolean isNotNullAndEmpty(char[] array) {
        return !isNullOrEmpty(array);
    }

    public static boolean isNullOrEmpty(float[] array) {
        return null == array || ZERO == array.length;
    }

    public static boolean isNotNullAndEmpty(float[] array) {
        return !isNullOrEmpty(array);
    }

    public static boolean isNullOrEmpty(double[] array) {
        return null == array || ZERO == array.length;
    }

    public static boolean isNotNullAndEmpty(double[] array) {
        return !isNullOrEmpty(array);
    }
}
