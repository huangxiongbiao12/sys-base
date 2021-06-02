package com.rm.manager.controller;

import com.rm.manager.entity.SysMenuEntity;
import com.rm.manager.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.rm.common.jooq.Paging;
import com.rm.common.web.response.Page;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("sys-menu")
public class SysMenuController {

    @Autowired
    SysMenuService sysMenuService;


    @RequestMapping("save")
    public void save(SysMenuEntity entity) {
        sysMenuService.insert(entity);
    }

    @RequestMapping("delete")
    public void deleteById(String id) {
        sysMenuService.deleteById(id);
    }

    @RequestMapping("update")
    public void update(SysMenuEntity entity) {
        sysMenuService.update(entity);
    }

    @RequestMapping("get-by-id")
    public SysMenuEntity getById(String id) {
        return sysMenuService.getById(id);
    }

    @RequestMapping("list")
    public List<SysMenuEntity> list() {
        return sysMenuService.getAll();
    }

    @RequestMapping("list-condition-page")
    public Paging<SysMenuEntity> listConditionPage(@RequestBody Map map, Page page) {
        Paging<SysMenuEntity> paging = new Paging<>(page.getPage(), page.getSize());
        sysMenuService.listConditionPage(map, paging);
        return paging;
    }

}
