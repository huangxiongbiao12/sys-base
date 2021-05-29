package com.rm.common.service;

import java.util.List;

/**
 * @param <P>数据库实体
 */
public interface BaseService<P> {

    void insert(P p);

    void update(P p);

    void deleteById(String id);

    P getById(String id);

    List<P> getAll();

}
