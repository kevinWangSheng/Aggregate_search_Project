package com.yupi.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.service.PostEsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wang
 * @create 2023-2023-22-20:58
 */
@Component
public class PostDataSource implements DataSource{

    @Resource
    private PostEsService postEsService;

    @Override
    public Page doSearch(String searchText, long pageNum, long pageSize) {
        if(StringUtils.isBlank(searchText)){
            searchText = "c++";
        }
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        postQueryRequest.setPageSize(pageSize);
        postQueryRequest.setCurrent(pageNum);
        Page<Post> postPage = postEsService.searchFromEs(postQueryRequest);
        return postPage;
    }

}
