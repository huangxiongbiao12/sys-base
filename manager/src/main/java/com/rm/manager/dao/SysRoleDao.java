package com.rm.manager.dao;

import com.rm.common.jooq.BasicDao;
import org.springframework.stereotype.Repository;
import com.rm.manager.entity.SysRoleEntity;
import com.rm.manager.jooq.tables.SysRole;
import com.rm.manager.jooq.tables.records.SysRoleRecord;

/**
*
*   系统角色表
*
*/
@Repository
public class SysRoleDao extends BasicDao<SysRoleRecord, SysRoleEntity, SysRole> {

    protected SysRoleDao() {
        super(SysRole.SYS_ROLE, SysRoleEntity.class);
    }

}
