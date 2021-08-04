package com.rm.common.jooq;

import com.rm.common.utils.ReflectUtil;
import com.rm.common.web.response.ResponseEnum;
import org.jooq.*;
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
    public static SelectConditionStep parseMap(DSLContext dslContext, Map map, Table t) {
        StringBuilder sqlBuilder = new StringBuilder();
        OrderField orderField = null;
        if (map != null) {
            Set<String> keys = map.keySet();
            String orderKey = null;
            for (String key : keys) {
                checkInjSql(key);
                checkInjSql(map.get(key).toString());
                // 排序跳过
                if (key.startsWith(ORDER)) {
                    orderKey = key;
                    continue;
                }
                String[] cons = key.split(SPILT);
                if (cons.length >= 1 && map.get(key) != null && StringUtils.hasLength(map.get(key).toString())) {
                    sqlBuilder.append("`" + cons[0] + "`");
                    if (cons.length >= 2) {
                        sqlBuilder.append(Compare.getValue(cons[1]));
                    } else {
                        sqlBuilder.append(Compare.EQ.getValue());
                    }
                    sqlBuilder.append("'" + map.get(key) + "' and ");
                }
            }
            if (sqlBuilder.length() > 5) {
                sqlBuilder.replace(sqlBuilder.length() - 5, sqlBuilder.length() - 1, "");
            }
//        // order- 开头加字段名排序  1 升序 0 降序
            if (StringUtils.hasLength(orderKey)) {
                String asc = map.get(orderKey).toString();
                TableField tableField = ReflectUtil.getValueByField(orderKey.split(SPILT)[1].toUpperCase(), t);
                if (asc.equals("1")) {
                    orderField = tableField.asc();
                } else {
                    orderField = tableField.desc();
                }
            }
        }
        if (!StringUtils.hasLength(sqlBuilder.toString())) {
            sqlBuilder.append(" 1 = 1 ");
        }
        SelectConditionStep selectConditionStep = dslContext.select().from(t).where(DSL.condition(sqlBuilder.toString()));
        if (orderField != null) {
            selectConditionStep.orderBy(orderField);
        }
        return selectConditionStep;
    }


    /**
     * 检查sql注入
     *
     * @param str
     * @return
     */
    public static void checkInjSql(String str) {
        String flagStr = "`|'|and |and(|or |or(|exec|insert|select|delete|update|truncate |truncate(|char|declare |;|,";
        //这里的东西还可以自己添加
        String[] flags = flagStr.split("\\|");
        for (int i = 0; i < flags.length; i++) {
            // 含有特殊字符的处理
            if (str.contains(flags[i])) {
                throw ResponseEnum.SYSTEM_ERROR.newInstance("存在sql注入的风险: " + str);

            }
        }
    }


}
