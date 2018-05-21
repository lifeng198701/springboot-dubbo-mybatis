package com.lifeng.commons.util;

import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by lifeng on 2018/5/19.
 */
public class StringUtil {
    public static final String ENC_UTF8 = "UTF-8";
    public static final String ENC_GBK = "GBK";
    public static final Charset GBK = Charset.forName("GBK");
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final String FMT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    public StringUtil() {
    }

    public static String convertString(String str, String defaults) {
        return str == null?defaults:str;
    }

    public static int convertInt(String str, int defaults) {
        if(str == null) {
            return defaults;
        } else {
            try {
                return Integer.parseInt(str);
            } catch (Exception var3) {
                return defaults;
            }
        }
    }

    public static long convertLong(String str, long defaults) {
        if(str == null) {
            return defaults;
        } else {
            try {
                return Long.parseLong(str);
            } catch (Exception var4) {
                return defaults;
            }
        }
    }

    public static double convertDouble(String str, double defaults) {
        if(str == null) {
            return defaults;
        } else {
            try {
                return Double.parseDouble(str);
            } catch (Exception var4) {
                return defaults;
            }
        }
    }

    public static short convertShort(String str, short defaults) {
        if(str == null) {
            return defaults;
        } else {
            try {
                return Short.parseShort(str);
            } catch (Exception var3) {
                return defaults;
            }
        }
    }

    public static float convertFloat(String str, float defaults) {
        if(str == null) {
            return defaults;
        } else {
            try {
                return Float.parseFloat(str);
            } catch (Exception var3) {
                return defaults;
            }
        }
    }

    public static boolean convertBoolean(String str, boolean defaults) {
        if(str == null) {
            return defaults;
        } else {
            try {
                return Boolean.parseBoolean(str);
            } catch (Exception var3) {
                return defaults;
            }
        }
    }

    public static String[] split(String line, String seperator) {
        if(line != null && seperator != null && seperator.length() != 0) {
            ArrayList<String> list = new ArrayList();
            int pos1 = 0;

            while(true) {
                int pos2 = line.indexOf(seperator, pos1);
                if(pos2 < 0) {
                    list.add(line.substring(pos1));

                    for(int i = list.size() - 1; i >= 0 && ((String)list.get(i)).length() == 0; --i) {
                        list.remove(i);
                    }

                    return (String[])list.toArray(new String[0]);
                }

                list.add(line.substring(pos1, pos2));
                pos1 = pos2 + seperator.length();
            }
        } else {
            return null;
        }
    }

    public static int[] splitInt(String line, String seperator, int def) {
        String[] ss = split(line, seperator);
        int[] r = new int[ss.length];

        for(int i = 0; i < r.length; ++i) {
            r[i] = convertInt(ss[i], def);
        }

        return r;
    }

    public static long[] splitLong(String line, String seperator, long def) {
        String[] ss = split(line, seperator);
        long[] r = new long[ss.length];

        for(int i = 0; i < r.length; ++i) {
            r[i] = convertLong(ss[i], def);
        }

        return r;
    }

    public static String join(String separator, Collection c) {
        if(c.isEmpty()) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            Iterator i = c.iterator();
            sb.append(i.next());

            while(i.hasNext()) {
                sb.append(separator);
                sb.append(i.next());
            }

            return sb.toString();
        }
    }

    public static String join(String separator, String[] s) {
        return joinArray(separator, (Object[])s);
    }

    public static String joinArray(String separator, Object[] s) {
        if(s != null && s.length != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(s[0]);

            for(int i = 1; i < s.length; ++i) {
                if(s[i] != null) {
                    sb.append(separator);
                    sb.append(s[i].toString());
                }
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    public static String joinArray(String separator, int[] s) {
        if(s != null && s.length != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(s[0]);

            for(int i = 1; i < s.length; ++i) {
                sb.append(separator);
                sb.append(s[i]);
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    public static String joinArray(String separator, long[] s) {
        if(s != null && s.length != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(s[0]);

            for(int i = 1; i < s.length; ++i) {
                sb.append(separator);
                sb.append(s[i]);
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    public static String join(String separator, Object... c) {
        return joinArray(separator, c);
    }

    public static String replaceAll(String s, String src, String dest) {
        if(s != null && src != null && dest != null && src.length() != 0) {
            int pos = s.indexOf(src);
            if(pos < 0) {
                return s;
            } else {
                int capacity = dest.length() > src.length()?s.length() * 2:s.length();
                StringBuilder sb = new StringBuilder(capacity);

                int writen;
                for(writen = 0; pos >= 0; pos = s.indexOf(src, writen)) {
                    sb.append(s, writen, pos);
                    sb.append(dest);
                    writen = pos + src.length();
                }

                sb.append(s, writen, s.length());
                return sb.toString();
            }
        } else {
            return s;
        }
    }

    public static String replaceFirst(String s, String src, String dest) {
        if(s != null && src != null && dest != null && src.length() != 0) {
            int pos = s.indexOf(src);
            if(pos < 0) {
                return s;
            } else {
                StringBuilder sb = new StringBuilder(s.length() - src.length() + dest.length());
                sb.append(s, 0, pos);
                sb.append(dest);
                sb.append(s, pos + src.length(), s.length());
                return sb.toString();
            }
        } else {
            return s;
        }
    }

    public static boolean isEmpty(String s) {
        return s == null?true:s.trim().isEmpty();
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static String trim(String s) {
        return s == null?null:s.trim();
    }

    public static String removeAll(String s, String src) {
        return replaceAll(s, src, "");
    }

    public static String abbreviate(String src, int maxlen, String replacement) {
        if(src == null) {
            return "";
        } else {
            if(replacement == null) {
                replacement = "";
            }

            StringBuffer dest = new StringBuffer();

            try {
                maxlen -= computeDisplayLen(replacement);
                if(maxlen < 0) {
                    return src;
                } else {
                    int i;
                    for(i = 0; i < src.length() && maxlen > 0; ++i) {
                        char c = src.charAt(i);
                        if(c >= 0 && c <= 255) {
                            --maxlen;
                        } else {
                            maxlen -= 2;
                        }

                        if(maxlen >= 0) {
                            dest.append(c);
                        }
                    }

                    if(i < src.length() - 1) {
                        dest.append(replacement);
                    }

                    return dest.toString();
                }
            } catch (Throwable var6) {
                LoggerFactory.getLogger("stringutil").error("abbreviate error: ", var6);
                return src;
            }
        }
    }

    public static String abbreviate(String src, int maxlen) {
        return abbreviate(src, maxlen, "");
    }

    public static String toShort(String str, int maxLen, String replacement) {
        if(str == null) {
            return "";
        } else if(str.length() <= maxLen) {
            return str;
        } else {
            StringBuilder dest = new StringBuilder();
            double len = 0.0D;

            for(int i = 0; i < str.length(); ++i) {
                char c = str.charAt(i);
                if(c >= 0 && c <= 255) {
                    len += 0.5D;
                } else {
                    ++len;
                }

                if(len > (double)maxLen) {
                    return dest.toString() + replacement;
                }

                dest.append(c);
            }

            return dest.toString();
        }
    }

    public static String toShort(String str, int maxLen) {
        return toShort(str, maxLen, "...");
    }

    public static int computeDisplayLen(String s) {
        int len = 0;
        if(s == null) {
            return len;
        } else {
            for(int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                if(c >= 0 && c <= 255) {
                    ++len;
                } else {
                    len += 2;
                }
            }

            return len;
        }
    }

    public static byte[] getUTF8Bytes(String s) {
        return s != null && s.length() >= 0?s.getBytes(UTF_8):null;
    }

    public static byte[] getGBKBytes(String s) {
        return s != null && s.length() >= 0?s.getBytes(GBK):null;
    }

    public static String getUTF8String(byte[] b) {
        return b != null?new String(b, UTF_8):null;
    }

    public static String getGBKString(byte[] b) {
        return b != null?new String(b, GBK):null;
    }

    public static String urlEncodeGBK(String s) {
        if(s != null && s.length() > 0) {
            try {
                return URLEncoder.encode(s, "GBK");
            } catch (UnsupportedEncodingException var2) {
                ;
            }
        }

        return s;
    }

    public static String urlEncodeUTF8(String s) {
        if(s != null && s.length() > 0) {
            try {
                return URLEncoder.encode(s, "UTF-8");
            } catch (UnsupportedEncodingException var2) {
                ;
            }
        }

        return s;
    }

    public static String urlDecodeGBK(String s) {
        if(s != null && s.length() > 0) {
            try {
                return URLDecoder.decode(s, "GBK");
            } catch (UnsupportedEncodingException var2) {
                ;
            }
        }

        return s;
    }

    public static String urlDecodeUTF8(String s) {
        if(s != null && s.length() > 0) {
            try {
                return URLDecoder.decode(s, "UTF-8");
            } catch (UnsupportedEncodingException var2) {
                ;
            }
        }

        return s;
    }

    public static char getChar(int i) {
        return (char)(64 + i);
    }

    public static String charsFillSeats(Long character, int size) {
        StringBuffer sb = new StringBuffer();
        int num = Integer.parseInt(character + "");
        sb.append(num);

        for(int i = String.valueOf(num).length(); i < size; ++i) {
            sb.insert(0, "0");
        }

        return sb.toString();
    }

    public static String removeRepeatedBlankChar(String s) {
        return s != null && s.length() != 0?s.replaceAll("\\s+", " "):s;
    }

    public static void rTrim(StringBuffer targetStrBuf, String subStr) {
        while(targetStrBuf.lastIndexOf(" ") > -1 && targetStrBuf.lastIndexOf(" ") == targetStrBuf.length() - 1 || targetStrBuf.lastIndexOf("   ") > -1 && targetStrBuf.lastIndexOf("   ") == targetStrBuf.length() - 1) {
            targetStrBuf.delete(targetStrBuf.length() - 1, targetStrBuf.length());
        }

        while(targetStrBuf.lastIndexOf(subStr) > 0 && targetStrBuf.lastIndexOf(subStr) == targetStrBuf.length() - subStr.length()) {
            targetStrBuf.delete(targetStrBuf.lastIndexOf(subStr), targetStrBuf.length());
        }

    }

    public static String concatStringArray(String[] arr, String spliter, boolean ignoreBlankStringInArr) {
        if(arr != null && spliter != null) {
            StringBuilder sb = new StringBuilder("");
            boolean flag = false;

            for(int i = 0; i < arr.length; ++i) {
                if(isNotEmpty(arr[i]) || !ignoreBlankStringInArr) {
                    if(flag) {
                        sb.append(spliter);
                    } else {
                        flag = true;
                    }

                    sb.append(arr[i]);
                }
            }

            return sb.toString();
        } else {
            throw new IllegalArgumentException("Invalid argument.");
        }
    }

    public static int patternMatch(String text, String pattern) {
        if(text != null && pattern != null) {
            StringUtil.KMP kmp = new StringUtil.KMP(pattern);
            int pos = kmp.match(text);
            if(pos == text.length()) {
                pos = -1;
            }

            return pos;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    public static boolean isChinese(String str) {
        if(null == str) {
            return false;
        } else {
            char[] ch = str.toCharArray();

            for(int i = 0; i < ch.length; ++i) {
                char c = ch[i];
                if(!isChinese(c)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static final boolean isTextResponse(String contentType) {
        if(contentType == null) {
            return false;
        } else {
            contentType = contentType.toLowerCase();
            return contentType.contains("wml") || contentType.contains("html") || contentType.contains("text") || contentType.contains("xml") || contentType.contains("json") || contentType.contains("javascript");
        }
    }

    public static String toString(Throwable e) {
        ThrowableInfo t = new ThrowableInfo(e);
        return t.getThrowableStr();
    }

    private static class KMP {
        private final String pattern;
        private final int[] next;

        public KMP(String pattern) {
            this.pattern = pattern;
            int M = pattern.length();
            this.next = new int[M];
            int j = -1;

            for(int i = 0; i < M; ++i) {
                if(i == 0) {
                    this.next[i] = -1;
                } else if(pattern.charAt(i) != pattern.charAt(j)) {
                    this.next[i] = j;
                } else {
                    this.next[i] = this.next[j];
                }

                while(j >= 0 && pattern.charAt(i) != pattern.charAt(j)) {
                    j = this.next[j];
                }

                ++j;
            }

        }

        public int match(String text) {
            int M = this.pattern.length();
            int N = text.length();
            int i = 0;

            int j;
            for(j = 0; i < N && j < M; ++i) {
                while(j >= 0 && text.charAt(i) != this.pattern.charAt(j)) {
                    j = this.next[j];
                }

                ++j;
            }

            return j == M?i - M:N;
        }
    }
}
