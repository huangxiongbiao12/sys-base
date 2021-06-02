package com.rm.manager.dao;

import com.rm.common.jooq.BasicDao;
import org.springframework.stereotype.Repository;
import com.rm.manager.entity.SysMenuEntity;
import com.rm.manager.jooq.tables.SysMenu;
import com.rm.manager.jooq.tables.records.SysMenuRecord;

/**
*
*   系统权限表(菜单、按钮)
*
*/
@Repository
public class SysMenuDao extends BasicDao<SysMenuRecord, SysMenuEntity, SysMenu> {

    protected SysMenuDao() {
        super(SysMenu.SYS_MENU, SysMenuEntity.class);
    }

}
