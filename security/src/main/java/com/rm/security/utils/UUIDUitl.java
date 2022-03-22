package com.rm.security.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 类描述：生成随机数
 */
public class UUIDUitl {
    public static final String allCharStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+";
    public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String numberChar = "0123456789";
    public static final String specialChar = "!@#$%^&*()_+";

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateInteger(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateAllString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allCharStr.charAt(random.nextInt(allCharStr.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯字母字符串(只包含大小写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateMixString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(letterChar.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }

    /**
     * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }

    /**
     * 生成一个定长的纯0字符串
     *
     * @param length 字符串长度
     * @return 纯0字符串
     */
    public static String generateZeroString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num       数字
     * @param fixdlenth 字符串长度
     * @return 定长的字符串
     */
    public static String toFixdLengthString(long num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num       数字
     * @param fixdlenth 字符串长度
     * @return 定长的字符串
     */
    public static String toFixdLengthString(int num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }

    /**
     * 八位数字+字母+特殊字符随机密码生成
     *
     * @return
     */
    public static String generatePwdStr() {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            sb.append(allChar.charAt(random.nextInt(letterChar.length())));
            sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
        }
        for (int i = 0; i < 2; i++) {
            sb.append(specialChar.charAt(random.nextInt(specialChar.length())));
        }
        return sb.toString();
    }

    /**
     * 生成展示code
     * @param name 常量类中定义大的模块名称
     * @return MS+对应模块英文字母第一位大写+年月日时分秒+4位随机码
     */
    public static String getShowCode(String name){
        char[] charArray = name.toCharArray();
        StringBuffer sb = new StringBuffer();
        sb.append("MS");
        //拼音首字母
        try {

            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            // 设置大小写格式
            defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
            // 设置声调格式：
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            for (int i = 0; i < charArray.length; i++) {
                //匹配中文,非中文转换会转换成null
                if (Character.toString(charArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(charArray[i], defaultFormat);
                    if (hanyuPinyinStringArray != null) {
                        sb.append(hanyuPinyinStringArray[0].charAt(0));
                    }
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        //当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        sb.append(simpleDateFormat.format(new Date()));
        //四位随机数
        Random random = new Random();
        for(int i=0;i<4;i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 生成用户登陆账号
     * @param userName 用户姓名
     * @param idNumber 用户身份证号
     * @return 姓名拼音首字母+身份证号后六位
     */
    public static String getUserLoginAccount(String userName,String idNumber){
        char[] nameArray = userName.toCharArray();
        StringBuffer sb = new StringBuffer();
        //拼音首字母
        try {
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            // 设置大小写格式
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            // 设置声调格式：
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            for (int i = 0; i < nameArray.length; i++) {
                //匹配中文,非中文转换会转换成null
                if (Character.toString(nameArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(nameArray[i], defaultFormat);
                    if (hanyuPinyinStringArray != null) {
                        sb.append(hanyuPinyinStringArray[0].charAt(0));
                    }
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        //截取身份证后六位
        sb.append(idNumber.substring(idNumber.length() -6));
        return sb.toString();
    }
}
