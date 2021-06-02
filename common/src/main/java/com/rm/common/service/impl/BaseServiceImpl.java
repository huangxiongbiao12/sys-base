package com.rm.common.service.impl;

import com.rm.common.jooq.BasicDao;
import com.rm.common.jooq.Paging;
import com.rm.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @param <P>   数据库实体
 * @param <DAO> 数据库dao
 */
public abstract class BaseServiceImpl<P, DAO extends BasicDao> implements BaseService<P> {

    @Autowired
    protected DAO dao;


    @Override
    public void insert(P p) {
        dao.insert(p);
    }

    @Override
    public void update(P p) {
        dao.update(p);
    }

    @Override
    public void deleteById(String id) {
        dao.deleteById(id);
    }

    @Override
    public P getById(String id) {
        return (P) dao.findById(id);
    }

    @Override
    public List<P> getAll() {
        return dao.findAll();
    }

    @Override
    public List<P> listConditionPage(Map map, Paging paging) {
        return dao.listConditionPage(map, paging);
    }

}
