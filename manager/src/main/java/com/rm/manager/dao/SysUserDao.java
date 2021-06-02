package com.rm.manager.dao;

import com.rm.common.jooq.BasicDao;
import org.springframework.stereotype.Repository;
import com.rm.manager.entity.SysUserEntity;
import com.rm.manager.jooq.tables.SysUser;
import com.rm.manager.jooq.tables.records.SysUserRecord;

/**
*
*   用户表
*
*/
@Repository
public class SysUserDao extends BasicDao<SysUserRecord, SysUserEntity, SysUser> {

    protected SysUserDao() {
        super(SysUser.SYS_USER, SysUserEntity.class);
    }

}
