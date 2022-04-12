package com.rm.generator.erupt;

import com.rm.generator.jooq.BasicEntity;
import com.rm.generator.jooq.SnowflakeIdWorker;
import org.springframework.util.StringUtils;
import xyz.erupt.annotation.fun.DataProxy;

import java.time.LocalDateTime;

public class BaseEntityDataProxy implements DataProxy<BasicEntity> {

    @Override
    public void beforeAdd(BasicEntity entity) {
        if (StringUtils.isEmpty(entity.getId())) {
            entity.setId(SnowflakeIdWorker.generateId());
        }
        entity.setCreateDate(LocalDateTime.now());
    }

}
