package com.rm.manager;

import com.rm.common.generator.Generator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ManagerApplicationTests {

    @Test
    void contextLoads() {
        Generator.generate();
    }

}
