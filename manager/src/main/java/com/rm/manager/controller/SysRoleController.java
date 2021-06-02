package com.rm.manager.controller;

import com.rm.manager.entity.SysRoleEntity;
import com.rm.manager.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.rm.common.jooq.Paging;
import com.rm.common.web.response.Page;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("sys-role")
public class SysRoleController {

    @Autowired
    SysRoleService sysRoleService;


    @RequestMapping("save")
    public void save(SysRoleEntity entity) {
        sysRoleService.insert(entity);
    }

    @RequestMapping("delete")
    public void deleteById(String id) {
        sysRoleService.deleteById(id);
    }

    @RequestMapping("update")
    public void update(SysRoleEntity entity) {
        sysRoleService.update(entity);
    }

    @RequestMapping("get-by-id")
    public SysRoleEntity getById(String id) {
        return sysRoleService.getById(id);
    }

    @RequestMapping("list")
    public List<SysRoleEntity> list() {
        return sysRoleService.getAll();
    }

    @RequestMapping("list-condition-page")
    public Paging<SysRoleEntity> listConditionPage(@RequestBody Map map, Page page) {
        Paging<SysRoleEntity> paging = new Paging<>(page.getPage(), page.getSize());
        sysRoleService.listConditionPage(map, paging);
        return paging;
    }

}
