package com.rm.manager.controller;

import com.rm.manager.entity.SysRoleMenuEntity;
import com.rm.manager.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.rm.common.jooq.Paging;
import com.rm.common.web.response.Page;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("sys-role-menu")
public class SysRoleMenuController {

    @Autowired
    SysRoleMenuService sysRoleMenuService;


    @RequestMapping("save")
    public void save(SysRoleMenuEntity entity) {
        sysRoleMenuService.insert(entity);
    }

    @RequestMapping("delete")
    public void deleteById(String id) {
        sysRoleMenuService.deleteById(id);
    }

    @RequestMapping("update")
    public void update(SysRoleMenuEntity entity) {
        sysRoleMenuService.update(entity);
    }

    @RequestMapping("get-by-id")
    public SysRoleMenuEntity getById(String id) {
        return sysRoleMenuService.getById(id);
    }

    @RequestMapping("list")
    public List<SysRoleMenuEntity> list() {
        return sysRoleMenuService.getAll();
    }

    @RequestMapping("list-condition-page")
    public Paging<SysRoleMenuEntity> listConditionPage(@RequestBody Map map, Page page) {
        Paging<SysRoleMenuEntity> paging = new Paging<>(page.getPage(), page.getSize());
        sysRoleMenuService.listConditionPage(map, paging);
        return paging;
    }

}
