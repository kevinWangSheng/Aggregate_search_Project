package com.yupi.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.google.common.base.Predicates;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author wang
 * @create 2023-2023-01-17:26
 */
@Component
@Slf4j
public class DataSourceStabilityInterface {

    @Resource
    private RegisterDataSource registerDataSource;
    public Page<T> retryCall(String searchText,String type,long pageNum,long pageSize){

        Retryer<Page<T>> retryer = null;
        Callable<Page<T>> callable = new Callable<Page<T>>() {
            public Page call() throws Exception {
                DataSource dataSource = registerDataSource.getDataSourceByType(type);
                Page page = dataSource.doSearch(searchText, pageNum, pageSize);
                return page;
            }
        };

        retryer = RetryerBuilder.<Page<T>>newBuilder()
                .retryIfResult(Predicates.<Page<T>>isNull())
                .retryIfExceptionOfType(IOException.class)
                .retryIfRuntimeException()
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .build();
        try {
            Page<T> page = retryer.call(callable);
            return page;
        } catch (RetryException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new Page<>(pageNum,pageSize);
    }
}
