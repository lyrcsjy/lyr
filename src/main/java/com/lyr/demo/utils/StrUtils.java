package com.lyr.demo.utils;

import org.apache.commons.lang.ArrayUtils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @Author: lyr
 * @Description: 字符串转换工具
 * @Date: 2020/03/12 6:13 下午
 * @Version: 1.0
 **/
public class StrUtils {

    public static final Charset UTF8 = StandardCharsets.UTF_8;

    /**
     * bytes转Hex
     * @param bytes
     * @return
     */
    public static String bytesToHexStr(byte[] bytes){
        StringBuilder stringBuilder = new StringBuilder();
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 是否为空和空字符串
     * @param str
     * @return
     */
    public static boolean isBlank(String str){
        return str == null || "".equals(str);
    }

    /**
     * 是否不为空和空字符串
     * @param str
     * @return
     */
    public static boolean notBlank(String str){
        return !isBlank(str);
    }

    /**
     * 首字符转小写
     * @param str
     * @return
     */
    public static String firstToLowerCase(String str){
        return String.valueOf(str.toCharArray()[0]).toLowerCase() + str.substring(1);
    }

    /**
     * 首字符转大写
     * @param str
     * @return
     */
    public static String firstToUpperCase(String str){
        return String.valueOf(str.toCharArray()[0]).toUpperCase() + str.substring(1);
    }

    /**
     * 驼峰命名转下划线
     * @param str
     * @return
     */
    public static String camelCaseToUnderLine(String str,boolean isToUpperCase){
        StringBuilder buf = new StringBuilder(str);
        for (int i = 1; i < buf.length() - 1; i++) {
            if (Character.isLowerCase(buf.charAt(i - 1)) && Character.isUpperCase(buf.charAt(i))
                    && Character.isLowerCase(buf.charAt(i + 1))) {
                buf.insert(i++, '_');
            }
        }
        return isToUpperCase ? buf.toString().toUpperCase() : buf.toString().toLowerCase();
    }

    /**
     * 将对象/数组转换为utf8的字符串
     * @param obj
     * @return
     */
    public static String utf8Str(Object obj) {
        if (null == obj) {
            return null;
        } else if (obj instanceof String) {
            return (String)obj;
        } else if (obj instanceof byte[]) {
            byte[] data = (byte[])obj;
            return new String(data, UTF8);
        } else if (obj instanceof Byte[]) {
            Byte[] data =(Byte[])obj;
            byte[] bytes = new byte[data.length];
            for(int i = 0; i < data.length; ++i) {
                Byte dataByte = data[i];
                bytes[i] = null == dataByte ? -1 : dataByte;
            }
            return new String(bytes, UTF8);
        } else if (obj instanceof ByteBuffer) {
            return UTF8.decode((ByteBuffer)obj).toString();
        } else {
            return ArrayUtils.toString(obj);
        }
    }

    /**
     * 字符串格式化
     * eg: 将字符串中{}转换为对应的参数，如：测试{}测试，参数为abc，则格式化后的内容为：测试abc测试
     * @param strPattern
     * @param argArray
     * @return
     */
    public static String format(String strPattern, Object... argArray) {
        if (notBlank(strPattern) && argArray.length > 0) {
            int strPatternLength = strPattern.length();
            StringBuilder sb = new StringBuilder(strPatternLength + 50);
            int handledPosition = 0;

            for(int argIndex = 0; argIndex < argArray.length; ++argIndex) {
                int delimIndex = strPattern.indexOf("{}", handledPosition);
                if (delimIndex == -1) {
                    if (handledPosition == 0) {
                        return strPattern;
                    }

                    sb.append(strPattern, handledPosition, strPatternLength);
                    return sb.toString();
                }

                if (delimIndex > 0 && strPattern.charAt(delimIndex - 1) == '\\') {
                    if (delimIndex > 1 && strPattern.charAt(delimIndex - 2) == '\\') {
                        sb.append(strPattern, handledPosition, delimIndex - 1);
                        sb.append(utf8Str(argArray[argIndex]));
                        handledPosition = delimIndex + 2;
                    } else {
                        --argIndex;
                        sb.append(strPattern, handledPosition, delimIndex - 1);
                        sb.append('{');
                        handledPosition = delimIndex + 1;
                    }
                } else {
                    sb.append(strPattern, handledPosition, delimIndex);
                    sb.append(utf8Str(argArray[argIndex]));
                    handledPosition = delimIndex + 2;
                }
            }

            sb.append(strPattern, handledPosition, strPattern.length());
            return sb.toString();
        } else {
            return strPattern;
        }
    }

    /**
     * 是否为json字符串
     * @param str
     * @return
     */
    public static boolean isJsonStr(String str){
        return (str.startsWith("{") && str.endsWith("}")) || str.startsWith("[") && str.endsWith("]");
    }
}