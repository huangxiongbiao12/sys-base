package com.alibaba.easyexcel.test.core.style;

import com.alibaba.easyexcel.test.core.simple.SimpleDataListener;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiaju Zhuang
 */
public class StyleDataListener extends AnalysisEventListener<StyleData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDataListener.class);
    List<StyleData> list = new ArrayList<StyleData>();

    @Override
    public void invoke(StyleData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assert.assertEquals(list.size(), 2);
        Assert.assertEquals(list.get(0).getString(), "字符串0");
        Assert.assertEquals(list.get(1).getString(), "字符串1");
        LOGGER.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
