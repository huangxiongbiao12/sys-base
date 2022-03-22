package com.rm.security.utils;

import java.util.UUID;

/**
 * Created by Administrator on 2017/8/14.
 * ID生成器，如果有其它ID主键相关的生成策略，请在此类中扩展
 */
public class IdGenerator {

    /**
     * 生成无32位的UUID字符串(无'-')
     * @return
     *      32位无横杠的UUID字符串
     */
    public static String uuid(){
        UUID uuid = UUID.randomUUID();
        return digits(uuid.getMostSignificantBits() >> 32, 8) +
                digits(uuid.getMostSignificantBits() >> 16, 4) +
                digits(uuid.getMostSignificantBits(), 4) +
                digits(uuid.getLeastSignificantBits() >> 48, 4) +
                digits(uuid.getLeastSignificantBits(), 12);
    }

    /** Returns val represented by the specified number of hex digits. */
    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }
}
