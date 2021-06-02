package com.rm.manager.dao;

import com.rm.common.jooq.BasicDao;
import org.springframework.stereotype.Repository;
import com.rm.manager.entity.SysConfigEntity;
import com.rm.manager.jooq.tables.SysConfig;
import com.rm.manager.jooq.tables.records.SysConfigRecord;

/**
*
*   系统配置表
*
*/
@Repository
public class SysConfigDao extends BasicDao<SysConfigRecord, SysConfigEntity, SysConfig> {

    protected SysConfigDao() {
        super(SysConfig.SYS_CONFIG, SysConfigEntity.class);
    }

}
