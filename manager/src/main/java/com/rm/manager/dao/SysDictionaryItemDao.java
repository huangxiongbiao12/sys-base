package com.rm.manager.dao;

import com.rm.common.jooq.BasicDao;
import org.springframework.stereotype.Repository;
import com.rm.manager.entity.SysDictionaryItemEntity;
import com.rm.manager.jooq.tables.SysDictionaryItem;
import com.rm.manager.jooq.tables.records.SysDictionaryItemRecord;

/**
*
*   字典项表
*
*/
@Repository
public class SysDictionaryItemDao extends BasicDao<SysDictionaryItemRecord, SysDictionaryItemEntity, SysDictionaryItem> {

    protected SysDictionaryItemDao() {
        super(SysDictionaryItem.SYS_DICTIONARY_ITEM, SysDictionaryItemEntity.class);
    }

}
