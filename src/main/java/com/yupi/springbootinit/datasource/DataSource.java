package com.yupi.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.ss.formula.functions.T;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wang
 * @create 2023-2023-22-20:53
 */
public interface DataSource {
    public Page doSearch(String searchText,long pageNum,long pageSize);


}
