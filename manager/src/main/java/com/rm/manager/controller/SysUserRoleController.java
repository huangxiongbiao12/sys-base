package com.rm.manager.controller;

import com.rm.manager.entity.SysUserRoleEntity;
import com.rm.manager.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.rm.common.jooq.Paging;
import com.rm.common.web.response.Page;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("sys-user-role")
public class SysUserRoleController {

    @Autowired
    SysUserRoleService sysUserRoleService;


    @RequestMapping("save")
    public void save(SysUserRoleEntity entity) {
        sysUserRoleService.insert(entity);
    }

    @RequestMapping("delete")
    public void deleteById(String id) {
        sysUserRoleService.deleteById(id);
    }

    @RequestMapping("update")
    public void update(SysUserRoleEntity entity) {
        sysUserRoleService.update(entity);
    }

    @RequestMapping("get-by-id")
    public SysUserRoleEntity getById(String id) {
        return sysUserRoleService.getById(id);
    }

    @RequestMapping("list")
    public List<SysUserRoleEntity> list() {
        return sysUserRoleService.getAll();
    }

    @RequestMapping("list-condition-page")
    public Paging<SysUserRoleEntity> listConditionPage(@RequestBody Map map, Page page) {
        Paging<SysUserRoleEntity> paging = new Paging<>(page.getPage(), page.getSize());
        sysUserRoleService.listConditionPage(map, paging);
        return paging;
    }

}
