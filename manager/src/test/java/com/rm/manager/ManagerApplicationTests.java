package com.rm.manager;

import com.rm.common.generator.Generator;
import com.rm.common.jooq.Paging;
//import com.rm.manager.service.ConfigService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ManagerApplicationTests {
//
//    @Autowired
//    ConfigService configService;

    @Test
    void contextLoads() {
        Generator.generate();
    }


    @Test
    void testDao() {
//        Map map = new HashMap();
//        map.put("key-like", "%_limit");
//        map.put("key", "water_level_limit");
//        Paging paging = new Paging<>(0, 10);
//        configService.listConditionPage(map, paging);
//        System.out.println(paging);
    }

}
