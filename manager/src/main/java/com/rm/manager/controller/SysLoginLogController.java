package com.rm.manager.controller;

import com.rm.manager.entity.SysLoginLogEntity;
import com.rm.manager.service.SysLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.rm.common.jooq.Paging;
import com.rm.common.web.response.Page;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("sys-login-log")
public class SysLoginLogController {

    @Autowired
    SysLoginLogService sysLoginLogService;


    @RequestMapping("save")
    public void save(SysLoginLogEntity entity) {
        sysLoginLogService.insert(entity);
    }

    @RequestMapping("delete")
    public void deleteById(String id) {
        sysLoginLogService.deleteById(id);
    }

    @RequestMapping("update")
    public void update(SysLoginLogEntity entity) {
        sysLoginLogService.update(entity);
    }

    @RequestMapping("get-by-id")
    public SysLoginLogEntity getById(String id) {
        return sysLoginLogService.getById(id);
    }

    @RequestMapping("list")
    public List<SysLoginLogEntity> list() {
        return sysLoginLogService.getAll();
    }

    @RequestMapping("list-condition-page")
    public Paging<SysLoginLogEntity> listConditionPage(@RequestBody Map map, Page page) {
        Paging<SysLoginLogEntity> paging = new Paging<>(page.getPage(), page.getSize());
        sysLoginLogService.listConditionPage(map, paging);
        return paging;
    }

}
