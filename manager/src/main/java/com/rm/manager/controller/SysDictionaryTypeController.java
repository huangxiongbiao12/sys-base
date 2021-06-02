package com.rm.manager.controller;

import com.rm.manager.entity.SysDictionaryTypeEntity;
import com.rm.manager.service.SysDictionaryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.rm.common.jooq.Paging;
import com.rm.common.web.response.Page;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("sys-dictionary-type")
public class SysDictionaryTypeController {

    @Autowired
    SysDictionaryTypeService sysDictionaryTypeService;


    @RequestMapping("save")
    public void save(SysDictionaryTypeEntity entity) {
        sysDictionaryTypeService.insert(entity);
    }

    @RequestMapping("delete")
    public void deleteById(String id) {
        sysDictionaryTypeService.deleteById(id);
    }

    @RequestMapping("update")
    public void update(SysDictionaryTypeEntity entity) {
        sysDictionaryTypeService.update(entity);
    }

    @RequestMapping("get-by-id")
    public SysDictionaryTypeEntity getById(String id) {
        return sysDictionaryTypeService.getById(id);
    }

    @RequestMapping("list")
    public List<SysDictionaryTypeEntity> list() {
        return sysDictionaryTypeService.getAll();
    }

    @RequestMapping("list-condition-page")
    public Paging<SysDictionaryTypeEntity> listConditionPage(@RequestBody Map map, Page page) {
        Paging<SysDictionaryTypeEntity> paging = new Paging<>(page.getPage(), page.getSize());
        sysDictionaryTypeService.listConditionPage(map, paging);
        return paging;
    }

}
