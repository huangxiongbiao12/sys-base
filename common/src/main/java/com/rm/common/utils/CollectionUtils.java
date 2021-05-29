package com.rm.common.utils;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {

    /**
     * 判断集合是否为空
     *  
     */
    public static boolean isEmpty(Collection collection) {
        if (null == collection || collection.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 判断集合是否非空
     *  
     */
    public static boolean notEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断集合是否为空
     *  
     */
    public static boolean isEmpty(Object[] objects) {
        if (null == objects || objects.length == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断集合是否非空
     *  
     */
    public static boolean notEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    /**
     *  判断map是否为空
     *  
     */
    public static boolean isEmpty(Map map) {
        if (null == map || map.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     *  判断map是否非空
     */
    public static boolean notEmpty(Map map) {
        return !isEmpty(map);
    }

}
