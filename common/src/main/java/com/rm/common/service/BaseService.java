package com.rm.common.service;

import com.rm.common.jooq.Paging;

import java.util.List;
import java.util.Map;

/**
 * @param <P>数据库实体
 */
public interface BaseService<P> {

    String insert(P p);

    void update(P p);

    void deleteById(String id);

    P getById(String id);

    List<P> getAll();

    List<P> listConditionPage(Map map, Paging paging);

}
