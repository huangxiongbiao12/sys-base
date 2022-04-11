package com.rm.generator.service;

import com.rm.generator.jooq.Paging;

import java.util.List;
import java.util.Map;

/**
 * @param <P>数据库实体
 */
public interface BaseService<P> {

    void insert(P p);

    String insertReturnId(P p);

    void update(P p);

    void deleteById(String id);

    P getById(String id);

    List<P> getAll();

    List<P> listConditionPage(Map map, Paging paging);

}
