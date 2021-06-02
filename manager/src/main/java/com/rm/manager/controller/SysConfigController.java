package com.rm.manager.controller;

import com.rm.manager.entity.SysConfigEntity;
import com.rm.manager.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.rm.common.jooq.Paging;
import com.rm.common.web.response.Page;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("sys-config")
public class SysConfigController {

    @Autowired
    SysConfigService sysConfigService;


    @RequestMapping("save")
    public void save(SysConfigEntity entity) {
        sysConfigService.insert(entity);
    }

    @RequestMapping("delete")
    public void deleteById(String id) {
        sysConfigService.deleteById(id);
    }

    @RequestMapping("update")
    public void update(SysConfigEntity entity) {
        sysConfigService.update(entity);
    }

    @RequestMapping("get-by-id")
    public SysConfigEntity getById(String id) {
        return sysConfigService.getById(id);
    }

    @RequestMapping("list")
    public List<SysConfigEntity> list() {
        return sysConfigService.getAll();
    }

    @RequestMapping("list-condition-page")
    public Paging<SysConfigEntity> listConditionPage(@RequestBody Map map, Page page) {
        Paging<SysConfigEntity> paging = new Paging<>(page.getPage(), page.getSize());
        sysConfigService.listConditionPage(map, paging);
        return paging;
    }

}
