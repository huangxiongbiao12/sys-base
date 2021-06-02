package com.rm.manager.dao;

import com.rm.common.jooq.BasicDao;
import org.springframework.stereotype.Repository;
import com.rm.manager.entity.SysLoginLogEntity;
import com.rm.manager.jooq.tables.SysLoginLog;
import com.rm.manager.jooq.tables.records.SysLoginLogRecord;

/**
*
*   用户表
*
*/
@Repository
public class SysLoginLogDao extends BasicDao<SysLoginLogRecord, SysLoginLogEntity, SysLoginLog> {

    protected SysLoginLogDao() {
        super(SysLoginLog.SYS_LOGIN_LOG, SysLoginLogEntity.class);
    }

}
