package com.rm.common.jooq;

import org.jooq.Condition;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;

public class ConditionUtils {

    public static final String ORDER = "order-";
    public static final String SPILT = "-";

    /**
     * 解析条件
     *
     * @param map
     * @return
     */
    public static Condition parseMap(Map map) {
        StringBuilder sqlBuilder = new StringBuilder();
        Set<String> keys = map.keySet();
        String orderKey = null;
        for (String key : keys) {
            // 排序跳过
            if (key.startsWith(ORDER)) {
                orderKey = key;
                continue;
            }
            String[] cons = key.split(SPILT);
            if (cons.length >= 1) {
                sqlBuilder.append("`" + cons[0] + "`");
                if (cons.length >= 2) {
                    sqlBuilder.append(Compare.getValue(cons[1]));
                } else {
                    sqlBuilder.append(Compare.EQ.getValue());
                }
                sqlBuilder.append("'" + map.get(key) + "' and ");
            }
        }
        sqlBuilder.replace(sqlBuilder.length()-5, sqlBuilder.length()-1, "");
//        // order- 开头加字段名排序  1 升序 0 降序
//        if (StringUtils.hasLength(orderKey)) {
//            String asc = map.get(orderKey).toString();
//            sqlBuilder.append(" ORDER BY ");
//            sqlBuilder.append("`" + orderKey.split(SPILT)[1] + "`");
//            sqlBuilder.append(asc.equals("1") ? " asc " : " desc ");
//        }
        return DSL.condition(sqlBuilder.toString());
    }

}
