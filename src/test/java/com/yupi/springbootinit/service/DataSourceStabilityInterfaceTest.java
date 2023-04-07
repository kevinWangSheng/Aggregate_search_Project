package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.datasource.DataSourceStabilityInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author wang
 * @create 2023-2023-01-17:33
 */
@SpringBootTest
@Slf4j
public class DataSourceStabilityInterfaceTest {
    @Resource
    private DataSourceStabilityInterface dataSourceStabilityInterface;


    @Test
    public void testRetry(){
        Page<T> page = dataSourceStabilityInterface.retryCall("", "user", 1, 10);
        System.out.println(page.getRecords().toString());
    }
}
