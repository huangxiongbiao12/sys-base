package com.rm.common.utils;

import com.rm.common.web.response.ResponseEnum;

import java.util.Calendar;

public class IDcardUtils {

    /**
     * 中国公民身份证号码最小长度。
     */
    public final int CHINA_ID_MIN_LENGTH = 15;

    /**
     * 中国公民身份证号码最大长度。
     */
    public final int CHINA_ID_MAX_LENGTH = 18;

    /**
     * 根据身份编号获取年龄
     *
     * @param idCard 身份编号
     * @return 年龄
     */
    public static int getAge(String idCard) {
        int iAge = 0;
        Calendar cal = Calendar.getInstance();
        String year = idCard.substring(6, 10);
        int iCurrYear = cal.get(Calendar.YEAR);
        iAge = iCurrYear - Integer.valueOf(year);
        return iAge;
    }

    /**
     * 根据身份编号获取性别
     *
     * @param idCard 身份编号
     * @return 性别(M - 男 ， F - 女 ， N - 未知)
     */
    public static String getGender(String idCard) {
        String sGender = "未知";
        String sCardNum = idCard.substring(16, 17);
        if (Integer.parseInt(sCardNum) % 2 != 0) {
            sGender = "1";//男
        } else {
            sGender = "2";//女
        }
        return sGender;
    }

    /**
     * 根据身份编号获取生日
     *
     * @param idCard 身份编号
     * @return 生日(yyyyMMdd)
     */
    public static String getBirth(String idCard) {
        return idCard.substring(6, 14);
    }

    /**
     * 根据身份编号获取生日年
     *
     * @param idCard 身份编号
     * @return 生日(yyyy)
     */
    public static Short getYear(String idCard) {
        return Short.valueOf(idCard.substring(6, 10));
    }

    /**
     * 根据身份编号获取生日月
     *
     * @param idCard 身份编号
     * @return 生日(MM)
     */
    public static Short getMonth(String idCard) {
        return Short.valueOf(idCard.substring(10, 12));
    }

    /**
     * 根据身份编号获取生日天
     *
     * @param idCard 身份编号
     * @return 生日(dd)
     */
    public static Short getDate(String idCard) {
        return Short.valueOf(idCard.substring(12, 14));
    }

    /**
     * 根据身份编号获取身份
     *
     * @param idCard 身份编号
     * @return 省份
     */
    public static String getProvince(String idCard) {
        String sCardNum = idCard.substring(0, 2);
        if ("11".equals(sCardNum)) {
            return "北京市";
        } else if ("12".equals(sCardNum)) {
            return "天津市";
        } else if ("13".equals(sCardNum)) {
            return "河北省";
        } else if ("14".equals(sCardNum)) {
            return "山西省";
        } else if ("15".equals(sCardNum)) {
            return "内蒙古自治区";
        } else if ("21".equals(sCardNum)) {
            return "辽宁省";
        } else if ("22".equals(sCardNum)) {
            return "吉林省";
        } else if ("23".equals(sCardNum)) {
            return "黑龙江省";
        } else if ("31".equals(sCardNum)) {
            return "上海市";
        } else if ("32".equals(sCardNum)) {
            return "江苏省";
        } else if ("33".equals(sCardNum)) {
            return "浙江省";
        } else if ("34".equals(sCardNum)) {
            return "安徽省";
        } else if ("35".equals(sCardNum)) {
            return "福建省";
        } else if ("36".equals(sCardNum)) {
            return "江西省";
        } else if ("37".equals(sCardNum)) {
            return "山东省";
        } else if ("41".equals(sCardNum)) {
            return "河南省";
        } else if ("42".equals(sCardNum)) {
            return "湖北省";
        } else if ("43".equals(sCardNum)) {
            return "湖南省";
        } else if ("44".equals(sCardNum)) {
            return "广东省";
        } else if ("45".equals(sCardNum)) {
            return "广西壮族自治区";
        } else if ("46".equals(sCardNum)) {
            return "海南省";
        } else if ("50".equals(sCardNum)) {
            return "重庆市";
        } else if ("51".equals(sCardNum)) {
            return "四川省";
        } else if ("52".equals(sCardNum)) {
            return "贵州省";
        } else if ("53".equals(sCardNum)) {
            return "云南省";
        } else if ("54".equals(sCardNum)) {
            return "西藏自治区";
        } else if ("61".equals(sCardNum)) {
            return "陕西省";
        } else if ("62".equals(sCardNum)) {
            return "甘肃省";
        } else if ("63".equals(sCardNum)) {
            return "青海省";
        } else if ("64".equals(sCardNum)) {
            return "宁夏回族自治区";
        } else if ("65".equals(sCardNum)) {
            return "新疆维吾尔自治区";
        } else if ("71".equals(sCardNum)) {
            return "台湾省";
        } else if ("81".equals(sCardNum)) {
            return "香港特别行政区";
        } else if ("82".equals(sCardNum)) {
            return "澳门特别行政区";
        }
        throw ResponseEnum.SYSTEM_ERROR.newInstance("身份证格式不正确!");
    }


}
