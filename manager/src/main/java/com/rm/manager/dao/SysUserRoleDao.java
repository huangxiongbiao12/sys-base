package com.rm.manager.dao;

import com.rm.common.jooq.BasicDao;
import org.springframework.stereotype.Repository;
import com.rm.manager.entity.SysUserRoleEntity;
import com.rm.manager.jooq.tables.SysUserRole;
import com.rm.manager.jooq.tables.records.SysUserRoleRecord;

/**
*
*   用户角色配置表
*
*/
@Repository
public class SysUserRoleDao extends BasicDao<SysUserRoleRecord, SysUserRoleEntity, SysUserRole> {

    protected SysUserRoleDao() {
        super(SysUserRole.SYS_USER_ROLE, SysUserRoleEntity.class);
    }

}
