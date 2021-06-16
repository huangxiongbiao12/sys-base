package com.rm.common.jooq;

import org.jooq.*;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class BasicDao<R extends UpdatableRecord<R>, P extends BasicEntity, Tab extends Table<R>>
        extends DAOImpl<R, P, String> {

    protected final Tab t;

    protected BasicDao(Tab table, Class<P> type) {
        super(table, type, null);
        t = table;
    }

    @Override
    public String getId(P p) {
        return p.getId();
    }

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Override
    @Autowired
    public void setConfiguration(Configuration configuration) {
        super.setConfiguration(configuration);
    }

    @Override
    public void insert(P object) {
        if (StringUtils.isEmpty(object.getId())) {
            object.setId(SnowflakeIdWorker.generateId());
        }
        super.insert(object);
    }

    /**
     * 根据条件组装分页查询
     *
     * @param map
     * @param paging
     * @return
     */
    public List<P> listConditionPage(Map map, Paging paging) {
        SelectConditionStep<Record> selectConditionStep = ConditionUtils.parseMap(this.ctx(), map, t);
        return selectByPageAndCount(selectConditionStep, paging, getType());
    }


    /**
     * 分页查询
     *
     * @param clause   查询语句
     * @param page     分页对象
     * @param hasCount 是否查询总数
     * @param <T>      返回集合泛型
     * @return
     */
    public <T> List<T> selectByPage(SelectConditionStep<Record> clause, Paging<T> page, boolean hasCount, Class<T> dtoClass) {
        if (hasCount) {
            page.setTotal(this.ctx().fetchCount(clause));
        }
        List<T> results = null;
        if (page.getPageSize() != 0) {
            results = clause.limit(page.getOffset(), page.getPageSize())
                    .fetchInto(dtoClass);
        } else {
            results = clause.fetchInto(dtoClass);
        }
        page.setVal(results);
        return results;
    }

    /**
     * 查询分页 不包含总数
     *
     * @param clause
     * @param page
     * @param <T>
     * @return
     */
    public <T> List<T> selectByPageNoCount(SelectConditionStep<Record> clause, Paging<T> page, Class<T> dtoClass) {
        return selectByPage(clause, page, false, dtoClass);
    }

    /**
     * 查询分页包含总数
     *
     * @param clause
     * @param page
     * @param <T>
     * @return
     */
    public <T> List<T> selectByPageAndCount(SelectConditionStep<Record> clause, Paging<T> page, Class<T> dtoClass) {
        return selectByPage(clause, page, true, dtoClass);
    }

}
