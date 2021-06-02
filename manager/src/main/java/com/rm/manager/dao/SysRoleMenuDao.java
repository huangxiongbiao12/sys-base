package com.rm.manager.dao;

import com.rm.common.jooq.BasicDao;
import org.springframework.stereotype.Repository;
import com.rm.manager.entity.SysRoleMenuEntity;
import com.rm.manager.jooq.tables.SysRoleMenu;
import com.rm.manager.jooq.tables.records.SysRoleMenuRecord;

/**
*
*   角色菜单配置表
*
*/
@Repository
public class SysRoleMenuDao extends BasicDao<SysRoleMenuRecord, SysRoleMenuEntity, SysRoleMenu> {

    protected SysRoleMenuDao() {
        super(SysRoleMenu.SYS_ROLE_MENU, SysRoleMenuEntity.class);
    }

}
