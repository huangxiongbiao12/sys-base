package com.rm.manager.dao;

import com.rm.common.jooq.BasicDao;
import org.springframework.stereotype.Repository;
import com.rm.manager.entity.SysOrganizationEntity;
import com.rm.manager.jooq.tables.SysOrganization;
import com.rm.manager.jooq.tables.records.SysOrganizationRecord;

/**
*
*   系统组织机构表
*
*/
@Repository
public class SysOrganizationDao extends BasicDao<SysOrganizationRecord, SysOrganizationEntity, SysOrganization> {

    protected SysOrganizationDao() {
        super(SysOrganization.SYS_ORGANIZATION, SysOrganizationEntity.class);
    }

}
