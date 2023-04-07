package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.dto.post.PostEs;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.service.impl.PostEsServiceNewImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author wang
 * @create 2023-2023-31-12:55
 */
@SpringBootTest
@Slf4j
public class PostEsServiceTest {

    @Resource
    private PostEsServiceNewImpl postEsService;

    @Resource
    private PostEsService postEsNewService;


    @Test
    public void findByTitle() {
        List<PostEs> ja = postEsService.findByTitle("ja");
        System.out.println(ja);
    }

    @Test
    public void findByContent() {
        List<PostEs> ja = postEsService.findByContent("ja");
        System.out.println(ja);
    }

    @Test
    public void findByTags() {
        postEsService.findByTags("java");
    }

    @Test
    public void findByUserId() {
        List<PostEs> byUserId = postEsService.findByUserId("1");
        System.out.println(byUserId);
    }

    @Test
    public void findByCreateTime() {
        List<PostEs> byCreateTime = postEsService.findByCreateTime("2020-08-06T08:16:22.000Z");
        System.out.println(byCreateTime);
    }

    @Test
    public void findByUpdateTime() {
        List<PostEs> byUpdateTime = postEsService.findByUpdateTime("2023-03-30T08:44:17.000Z");
        System.out.println(byUpdateTime);
    }

    @Test
    public void findByIsDelete() {
        List<PostEs> byIsDelete = postEsService.findByIsDelete("0");
        System.out.println(byIsDelete);
    }

    @Test
    public void queryTest(){
        SearchHits<PostEs> search = postEsService.search("ja");
        System.out.println(search);
    }


    @Test
    public void testPostServiceNewImpl(){
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText("c++");
        postQueryRequest.setPageSize(10);
        postQueryRequest.setCurrent(1);
        Page<Post> postPage = postEsNewService.searchFromEs(postQueryRequest);
        System.out.println(Arrays.toString(postPage.getRecords().toArray()));
    }
}
