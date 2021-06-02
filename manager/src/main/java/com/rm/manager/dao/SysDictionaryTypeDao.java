package com.rm.manager.dao;

import com.rm.common.jooq.BasicDao;
import org.springframework.stereotype.Repository;
import com.rm.manager.entity.SysDictionaryTypeEntity;
import com.rm.manager.jooq.tables.SysDictionaryType;
import com.rm.manager.jooq.tables.records.SysDictionaryTypeRecord;

/**
*
*   字典类型表
*
*/
@Repository
public class SysDictionaryTypeDao extends BasicDao<SysDictionaryTypeRecord, SysDictionaryTypeEntity, SysDictionaryType> {

    protected SysDictionaryTypeDao() {
        super(SysDictionaryType.SYS_DICTIONARY_TYPE, SysDictionaryTypeEntity.class);
    }

}
