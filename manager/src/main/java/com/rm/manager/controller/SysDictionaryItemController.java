package com.rm.manager.controller;

import com.rm.manager.entity.SysDictionaryItemEntity;
import com.rm.manager.service.SysDictionaryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.rm.common.jooq.Paging;
import com.rm.common.web.response.Page;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("sys-dictionary-item")
public class SysDictionaryItemController {

    @Autowired
    SysDictionaryItemService sysDictionaryItemService;


    @RequestMapping("save")
    public void save(SysDictionaryItemEntity entity) {
        sysDictionaryItemService.insert(entity);
    }

    @RequestMapping("delete")
    public void deleteById(String id) {
        sysDictionaryItemService.deleteById(id);
    }

    @RequestMapping("update")
    public void update(SysDictionaryItemEntity entity) {
        sysDictionaryItemService.update(entity);
    }

    @RequestMapping("get-by-id")
    public SysDictionaryItemEntity getById(String id) {
        return sysDictionaryItemService.getById(id);
    }

    @RequestMapping("list")
    public List<SysDictionaryItemEntity> list() {
        return sysDictionaryItemService.getAll();
    }

    @RequestMapping("list-condition-page")
    public Paging<SysDictionaryItemEntity> listConditionPage(@RequestBody Map map, Page page) {
        Paging<SysDictionaryItemEntity> paging = new Paging<>(page.getPage(), page.getSize());
        sysDictionaryItemService.listConditionPage(map, paging);
        return paging;
    }

}
