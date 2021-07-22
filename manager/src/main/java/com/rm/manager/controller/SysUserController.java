package com.rm.manager.controller;

import com.rm.common.web.security.annotation.Disauth;
import com.rm.manager.entity.SysUserEntity;
import com.rm.manager.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.rm.common.jooq.Paging;
import com.rm.common.web.response.Page;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("sys-user")
public class SysUserController {

    @Autowired
    SysUserService sysUserService;


    @RequestMapping("save")
    public void save(SysUserEntity entity) {
        sysUserService.insert(entity);
    }

    @RequestMapping("delete")
    public void deleteById(String id) {
        sysUserService.deleteById(id);
    }

    @RequestMapping("update")
    public void update(SysUserEntity entity) {
        sysUserService.update(entity);
    }

    @RequestMapping("get-by-id")
    public SysUserEntity getById(String id) {
        return sysUserService.getById(id);
    }

    @RequestMapping("list")
    public List<SysUserEntity> list() {
        return sysUserService.getAll();
    }

    @RequestMapping("list-condition-page")
    public Paging<SysUserEntity> listConditionPage(@RequestBody Map map, Page page) {
        Paging<SysUserEntity> paging = new Paging<>(page.getPage(), page.getSize());
        sysUserService.listConditionPage(map, paging);
        return paging;
    }

}
