package com.xiaodong.basetools.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiaodong.jin on 2019/05/10.
 * 功能描述：
 */

public class StringUtils {
    /**
     * 邮箱格式校验
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * 身份证格式校验
     *
     * @param code
     * @return
     */
    public static boolean isIdCardNo(String code) {
        if (TextUtils.isEmpty(code)) {
            return false;
        }
        String str = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(code);

        return m.matches();
    }

    /**
     * 判断输入文字是否包含汉字
     *
     * @param input
     * @return
     */
    public static boolean hasChinese(String input) {
        return input.matches(".*[\u4e00-\u9faf].*");
    }

    /**
     * 社会代码有效性校验（位数为7、9、15、18）
     *
     * @param code
     * @return
     */
    public static boolean isRegisterCode(String code) {
        if (TextUtils.isEmpty(code)) {
            return false;
        }
        int codeLength = code.length();

        return codeLength == 7 | codeLength == 9 | codeLength == 15 | codeLength == 18;
    }

    /**
     * 使用正则表达式来判断字符串中是否包含字母
     *
     * @param str 待检验的字符串
     * @return 返回是否包含
     * true: 包含字母 ;false 不包含字母
     */
    public static boolean isContainsLetter(String str) {
        String regex = ".*[a-zA-Z]+.*";
        Matcher m = Pattern.compile(regex).matcher(str);
        return m.matches();
    }

    /**
     * 是否为手机号
     *
     * @param phoneNum
     * @return
     */
    public static boolean isMobilePhone86(String phoneNum) {
        if (TextUtils.isEmpty(phoneNum)) {
            return false;
        }
        String patternStr = "^(\\+86|86)?1[0-9]{10}$";
        Pattern pattern = Pattern.compile(patternStr);
        try {
            phoneNum = phoneNum.replace(" ", "");
            phoneNum = phoneNum.replace("-", "").trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Matcher matcher = pattern.matcher(phoneNum);
        return matcher.matches();
    }

    public static boolean isHaveSpecialCharacter(String string) {
        if (string.contains(" ") || string.contains("/") || string.contains("-") || string.contains("_")) {
            return true;
        }
        return false;
    }

    /**
     * 密码格式校验
     *
     * @param password
     * @return
     */
    public static boolean isAccountPasswordValid(String password) {
        String patternStr = "((?=.*\\d)(?=.*\\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{8,16}$";
        return Pattern.matches(patternStr, password);
    }

    /**
     * 手机号 XXX XXXX XXXX
     *
     * @param phoneNum
     * @return
     */
    public static String getFilterPhoneNumber(String phoneNum) {
        String str1 = phoneNum.replaceAll(" ", "");
        String str2 = str1.replaceAll("-", "");
        String str3 = str2.replaceAll("_", "");
        String value = str3.replaceAll("\n", "");
        return value;
    }
    public static boolean containsSpecialCharacter(String phoneNum) {
        if (phoneNum.contains(" ") ||phoneNum.contains("-")||phoneNum.contains("_")||phoneNum.contains("\n")){
            return true;
        }
        return false;
    }

    /**
     * 手机号 XXX XXXX XXXX
     *
     * @param phoneNum
     * @return
     */
    public static String getDividePhoneNumber(String phoneNum) {
        if (TextUtils.isEmpty(phoneNum) || phoneNum.length() != 11) {
            return phoneNum;
        }
        StringBuilder sb = new StringBuilder(phoneNum);
        sb.insert(3, ' ');
        sb.insert(8, ' ');
        return sb.toString();
    }

    /**
     * 手机号 XXX****XXXX
     *
     * @param phoneNum
     * @return
     */
    public static String getSafePhoneNumber(String phoneNum) {
        if (TextUtils.isEmpty(phoneNum) || phoneNum.length() != 11) {
            return phoneNum;
        }
        return new StringBuilder(phoneNum).replace(3, 7, "****").toString();
    }

    /**
     * 格式化
     *
     * @param money 小数
     * @return
     */
    public static String formatMoney(float money) {
        DecimalFormat df = new DecimalFormat("0.00");
        String moneyTemp = df.format(money);
        String moneyInt = moneyTemp.substring(0, moneyTemp.length() - 3);
        String moneyDecimal = moneyTemp.substring(moneyTemp.length() - 3, moneyTemp.length());
        return formatMoney(moneyInt) + moneyDecimal;
    }

    /**
     * 格式化
     *
     * @param s 整数
     * @return
     */
    public static String formatMoney(String s) {
        if (!TextUtils.isEmpty(s)) {
            int length = s.length();
            if (s.startsWith("-")) {
                return "-" + formatMoney(s.substring(1, length));
            }
            int num = length / 3;
            if (length % 3 > 0) {
                num++;
            }
            String[] s1 = new String[num];
            for (int i = 0; i < num - 1; i++) {
                s1[num - i - 1] = s.substring(length - 3 * (i + 1), length - 3 * i);
            }
            s1[0] = s.substring(0, length - 3 * (num - 1));
            String sb = "";
            for (int i = 0; i < num; i++) {
                sb = sb + s1[i] + ",";
            }
            sb = sb.substring(0, sb.length() - 1);
            return sb;
        } else {
            return "";
        }
    }

//    public static CharSequence getHighLightText(String origin, int highlightStart, int highlightEnd) {
//        SpannableString spanStr = new SpannableString(origin);
//        spanStr.setSpan(new ForegroundColorSpan(MyApp.getAppContext().getResources().getColor(R.color.color_primary)),
//                highlightStart, highlightEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return spanStr;
//    }

    /**
     * 格式化文字
     * 标题字体16号，描述字体12号
     *
     * @return
     */
//    public static CharSequence getTitleAndInfoText(int titleRes, int infoRes) {
//        CharSequence title = MyApp.getAppContext().getResources().getText(titleRes);
//        CharSequence info = MyApp.getAppContext().getResources().getText(infoRes);
//        SpannableString spanStr = new SpannableString(title + "\n" + info);
//        spanStr.setSpan(new AbsoluteSizeSpan(MyApp.getAppContext().getResources().getDimensionPixelSize(R.dimen.large_text_size)),
//                0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spanStr.setSpan(new AbsoluteSizeSpan(MyApp.getAppContext().getResources().getDimensionPixelSize(R.dimen.tag_text_size)),
//                title.length() + 1, spanStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return spanStr;
//    }

    /**
     * byte数组转换为十六进制字符串
     *
     * @param b
     * @return
     */
    public static String bytesToHexString(byte[] b) {
        if (b.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < b.length; i++) {
            // 按位与运算，都是1时结果为1，其它为0，所以与全是1相与，结果还是原来的01串
            int value = b[i] & 0xFF;
            String hv = Integer.toHexString(value);
            if (hv.length() < 2) {
                sb.append(0);
            }

            sb.append(hv);
        }
        return sb.toString();
    }

    /**
     * 将字节数组转换为String
     *
     * @param b byte[]
     * @return String
     */
    public static String bytesToString(byte[] b) {
        StringBuffer result = new StringBuffer("");
        int length = b.length;
        for (int i = 0; i < length; i++) {
            result.append((char) (b[i]));
        }
        return result.toString();
    }

}
