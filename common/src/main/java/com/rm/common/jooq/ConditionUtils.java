package com.rm.common.jooq;

import org.jooq.Condition;
import org.jooq.impl.DSL;

import java.util.Map;
import java.util.Set;

public class ConditionUtils {

    /**
     * 解析条件
     *
     * @param map
     * @return
     */
    public static Condition parseMap(Map map) {
        StringBuilder sqlBuilder = new StringBuilder();
        Set<String> keys = map.keySet();
        int i = 0;
        for (String key : keys) {
            i++;
            String[] cons = key.split("-");
            if (cons.length >= 1) {
                sqlBuilder.append("`" + cons[0] + "`");
                if (cons.length >= 2) {
                    sqlBuilder.append(Compare.getValue(cons[1]));
                } else {
                    sqlBuilder.append(Compare.EQ.getValue());
                }
                sqlBuilder.append("'" + map.get(key) + "'");
                if (i < keys.size()) {
                    sqlBuilder.append(" and ");
                }
            }
        }
        return DSL.condition(sqlBuilder.toString());
    }

}
