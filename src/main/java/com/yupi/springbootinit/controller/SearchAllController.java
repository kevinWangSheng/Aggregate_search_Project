package com.yupi.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.google.common.base.Predicates;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.datasource.*;
import com.yupi.springbootinit.model.dto.search.SearchBySelfRequest;
import com.yupi.springbootinit.model.enums.SearchTypeEnum;
import com.yupi.springbootinit.model.vo.SearchVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author wang
 * @create 2023-2023-18-10:12
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchAllController {

//    这类主要是使用使用并发进行查询
    @Resource
    private ConcurrcySearchDataSource concurrcySearchDataSource;

    @Resource
    private RegisterDataSource registerDataSource;

    @Resource
    private DataSourceStabilityInterface dataSourceStabilityInterface;

    @PostMapping("/all")
    public BaseResponse<SearchVo> getSearchAll(@RequestBody SearchBySelfRequest searchRequest, HttpServletRequest request){
        String searchText = searchRequest.getSearchText();
        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
//        isBleank判断该字符串是否为空或者空串，或者是否含有空格
//        ThrowUtils.throwIf(StringUtil.isBlank(type), ErrorCode.NOT_FOUND_ERROR);
        long pageNum = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();
        SearchVo searchVo = new SearchVo();
        DataSourceGetParamUtils.setRequest(request);
//        在DataSource传递参数工具中设置好要传递的request参数,方便后面有人需要这个参数调用
//        这里如果搜索类型不存在，就是没有匹配到对应的类型，就直接搜索出所有数据
        if(searchTypeEnum==null){
            searchVo = concurrcySearchDataSource.concurySearch(searchText, pageNum, pageSize);
        }else{

            Page<T> page = dataSourceStabilityInterface.retryCall(searchText, type, pageNum, pageSize);
//            这里使用注册的模式，就是这几个dataSource都是继承了DataSource，那么我们需要的是根据对应的type类型调用对应的接口进行查询，因此我们把这些接口疯转在了map中，在需要调用的时候直接getType取出对应的
//            dataSource，然后在doSearch就行了
//            DataSource dataSource = registerDataSource.getDataSourceByType(type);
//            Page page = dataSource.doSearch(searchText, pageNum, pageSize);
            searchVo.setDataList(page.getRecords());
        }
        return ResultUtils.success(searchVo);
    }
}
