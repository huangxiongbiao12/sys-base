package com.rm.manager.controller;

import com.rm.manager.entity.SysOrganizationEntity;
import com.rm.manager.service.SysOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.rm.common.jooq.Paging;
import com.rm.common.web.response.Page;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("sys-organization")
public class SysOrganizationController {

    @Autowired
    SysOrganizationService sysOrganizationService;


    @RequestMapping("save")
    public void save(SysOrganizationEntity entity) {
        sysOrganizationService.insert(entity);
    }

    @RequestMapping("delete")
    public void deleteById(String id) {
        sysOrganizationService.deleteById(id);
    }

    @RequestMapping("update")
    public void update(SysOrganizationEntity entity) {
        sysOrganizationService.update(entity);
    }

    @RequestMapping("get-by-id")
    public SysOrganizationEntity getById(String id) {
        return sysOrganizationService.getById(id);
    }

    @RequestMapping("list")
    public List<SysOrganizationEntity> list() {
        return sysOrganizationService.getAll();
    }

    @RequestMapping("list-condition-page")
    public Paging<SysOrganizationEntity> listConditionPage(@RequestBody Map map, Page page) {
        Paging<SysOrganizationEntity> paging = new Paging<>(page.getPage(), page.getSize());
        sysOrganizationService.listConditionPage(map, paging);
        return paging;
    }

}
