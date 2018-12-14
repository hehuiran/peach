package me.jessyan.peach.shop.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by Administrator on 2017/11/9.
 */

public final class StringUtils {
    private static final String TAG = "StringUtils";

    private StringUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    public static String getRandomString(int length) {
        StringBuilder builder = new StringBuilder("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int range = builder.length();
        for (int i = 0; i < length; i++) {
            sb.append(builder.charAt(random.nextInt(range)));
        }
        return sb.toString();
    }

    public static boolean isEmpty(@NonNull CharSequence... charSequences) {
        for (CharSequence charSequence : charSequences) {
            if (TextUtils.isEmpty(charSequence)) {
                return true;
            }
        }
        return false;
    }

       public static String getShareUrl(String baseShareUrl, String inviteCode) {
        return baseShareUrl.concat(inviteCode).concat("#wechat_redirect");
    }


    public static String getShareUrl1(String baseShareUrl, String inviteCode) {
        return baseShareUrl.concat(inviteCode);
    }


    public static String filterDecimal(String decimal) {
        if (TextUtils.isEmpty(decimal)) {
            return "0";
        }
        String[] split = decimal.split("\\.");
        int length = split.length;
        if (length != 2) {
            return length > 2 ? "0" : split[0];
        }
        String decimalStr;
        int size = split[1].length();
        if (size >= 2) {
            decimalStr = split[1].substring(0, 2);
            if (Integer.parseInt(decimalStr) == 0) {
                decimalStr = "";
            } else if (decimalStr.contains("0")) {
                int index = decimalStr.indexOf("0");
                if (index == 1) {
                    decimalStr = decimalStr.substring(0, 1);
                }
            }
        } else {
            decimalStr = split[1];
            if (Integer.parseInt(decimalStr) == 0) {
                decimalStr = "";
            }
        }
        return TextUtils.isEmpty(decimalStr) ? split[0] : split[0] + "." + decimalStr;
    }


    public static String filterAccurateDecimal(String decimal) {
        if (TextUtils.isEmpty(decimal)) {
            return "0";
        }
        String[] split = decimal.split("\\.");
        int length = split.length;
        if (length != 2) {
            return length > 2 ? "0" : split[0];
        }
        String decimalStr;
        int size = split[1].length();
        if (size > 2) {
            boolean isAdd = false;
            StringBuilder builder = new StringBuilder();
            char[] chars = split[1].toCharArray();
            for (int i = chars.length - 1; i >= 0; i--) {
                int value = Character.getNumericValue(chars[i]);
                if (isAdd || value != 0) {
                    isAdd = true;
                    builder.append(value);
                }
            }
            decimalStr = builder.reverse().toString();
        } else {
            decimalStr = split[1];
            if (Integer.parseInt(decimalStr) == 0) {
                decimalStr = "";
            }
        }
        return TextUtils.isEmpty(decimalStr) ? split[0] : split[0] + "." + decimalStr;
    }

    public static int NumberFormat(String format) {
        int number;
        try {
            number = Integer.parseInt(format);
        } catch (NumberFormatException e) {
            number = 0;
        } catch (NullPointerException e) {
            number = 0;
        }
        return number;
    }

    public static String splitCouponMoney(String format) {
        String[] splits;
        String money;
        try {
            splits = format.split("减");
            money = splits[splits.length - 1].split("元")[0];
        } catch (IndexOutOfBoundsException e) {
            money = "0";
        } catch (NullPointerException e) {
            money = "0";
        }
        return money;
    }

    public static float floatFormat(String format) {
        float number;
        try {
            number = Float.parseFloat(format);
        } catch (NumberFormatException e) {
            number = 0f;
        } catch (NullPointerException e) {
            number = 0f;
        }
        return number;
    }

    public static long longNumberFormat(String format) {
        long number;
        try {
            number = Long.parseLong(format);
        } catch (NumberFormatException e) {
            number = 0L;
        } catch (NullPointerException e) {
            number = 0L;
        }
        return number;
    }

    public static String getUnitString(String count) {
        int countInt = NumberFormat(count);
        if (countInt < 10000) {
            return count;
        }

        String w = String.valueOf(countInt / 10000);
        String s = count.replaceAll(w, "");
        if (TextUtils.isEmpty(s)) {
            return w + "万";
        }
        StringBuilder builder = new StringBuilder();
        boolean isAdd = false;

        char[] chars = s.substring(0, s.length() > 2 ? 2 : s.length()).toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            int value = Character.getNumericValue(chars[i]);
            if (isAdd || value != 0) {
                isAdd = true;
                builder.append(value);
            }
        }
        String remainder = builder.length() == 0 ? "" : "." + builder.reverse().toString() + "万";
        return w + remainder;
    }

    public static String getUnitAccurateString(String count) {
        int countInt = NumberFormat(count);
        if (countInt < 10000) {
            return count;
        }

        StringBuilder builder = new StringBuilder();
        boolean isAdd = false;
        char[] chars = count.toCharArray();
        for (int i = chars.length - 1; i > 0; i--) {
            int value = Character.getNumericValue(chars[i]);
            if (isAdd || value != 0) {
                isAdd = true;
                builder.append(value);
            }
        }
        String remainder = builder.length() == 0 ? "" : "." + builder.reverse().toString() + "万";
        return (countInt / 10000) + remainder;
    }

    public static String filterEmoji(String source) {
        if (!containsEmoji(source)) {
            return source;//如果不包含，直接返回
        }
        //到这里铁定包含
        StringBuilder buf = null;

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (!isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }

                buf.append(codePoint);
            }
        }

        if (buf == null) {
            return source;//如果没有找到 emoji表情，则返回源字符串
        } else {
            if (buf.length() == len) {//这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }


    }

    public static String addComma(String str) {
        str = new StringBuilder(str).reverse().toString();     //先将字符串颠倒顺序
        if (str.equals("0")) {
            return str;
        }
        String str2 = "";
        for (int i = 0; i < str.length(); i++) {
            if (i * 3 + 3 > str.length()) {
                str2 += str.substring(i * 3, str.length());
                break;
            }
            str2 += str.substring(i * 3, i * 3 + 3) + ",";
        }
        if (str2.endsWith(",")) {
            str2 = str2.substring(0, str2.length() - 1);
        }
        //最后再将顺序反转过来
        String temp = new StringBuilder(str2).reverse().toString();
        //将最后的,去掉
        return temp.substring(0, temp.lastIndexOf(",")) + temp.substring(temp.lastIndexOf(",") + 1, temp.length());
    }

    // 判别是否包含Emoji表情
    private static boolean containsEmoji(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (isEmojiCharacter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }

    public static String stringToDouble(String d){
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(d);
    }

    public static String stringToDoubleString2(String num){
        String strNum = String.valueOf(num);
        int n = strNum.indexOf(".");
        if(n>0){
            //截取小数点后的数字
            String dotNum = strNum.substring(n+1);
            if("0".equals(dotNum)){
                //小数点后面为0
                return strNum+"0";
            }else{
                if(dotNum.length()==1){
                    //一位小数
                    return strNum +"0";
                }else{
                    //
                    return strNum.substring(0,n+3);
                }
            }
        }else{
            return strNum+".00";
        }
    }

    public static String doubleToString2(double num){
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }

    public static String doubleToString1(double num){
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.0").format(num);
    }

}
