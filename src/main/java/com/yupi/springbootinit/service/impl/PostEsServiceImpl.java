package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.mapper.PostMapper;
import com.yupi.springbootinit.model.dto.post.PostEs;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.service.PostEsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wang
 * @create 2023-2023-29-22:59
 */
@Service
@Slf4j
public class PostEsServiceImpl extends ServiceImpl<PostMapper,Post> implements PostEsService {

    @Resource
    private PostEsServiceNewImpl postEsService;


    @Override
    public Page<Post> searchFromEs(PostQueryRequest postQueryRequest) {
        String searchText = postQueryRequest.getSearchText();
        long pageSize = postQueryRequest.getPageSize();
        long pageNum = postQueryRequest.getCurrent();

        SearchHits<PostEs> searchResult = postEsService.search(searchText);
        List<SearchHit<PostEs>> searchHits = searchResult.getSearchHits();

//        List<PostEs> postEses = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        List<PostEs> postEses = parseHighFieldsToPostEs(searchHits);
        List<Post> resourcePost = new ArrayList<>();
        if(postEses!=null && postEses.size()!=0){
            List<String> postEsIds = postEses.stream().map(PostEs::getId).collect(Collectors.toList());
            List<Post> posts = baseMapper.selectBatchIds(postEsIds);
            Map<Long, List<Post>> postsMap = posts.stream().collect(Collectors.groupingBy(Post::getId));

            for(PostEs postEs: postEses){
                long id = Long.parseLong(postEs.getId());
                List<Post> postList = new ArrayList<>();
                if((postList = postsMap.get(id))!=null){
                    Post post = postList.get(0);
                    post.setTitle(postEs.getTitle());
                    post.setContent(postEs.getContent());
                    resourcePost.add(post);
                }else{
                    postEsService.deleteById(postEs.getId());
                    log.info("删除了es中多余的但是mysql中已经删除的数据");
                }
            }
        }
        long start = (pageNum-1)*pageSize;
        if(start>resourcePost.size()){
            return new Page<Post>();
        }
        long end = Math.min(pageSize,resourcePost.size()-start);
        List<Post> pagePost = resourcePost.stream().skip(start).limit(end).collect(Collectors.toList());
        Page<Post> postPage = new Page<>(pageNum,pageSize);
        postPage.setRecords(pagePost);
        return postPage;
    }


    public List<PostEs> parseHighFieldsToPostEs(List<SearchHit<PostEs>> searchHits){

        List<PostEs> postEses = new ArrayList<>();
        for(SearchHit<PostEs> searchHit : searchHits){
            PostEs postEs = searchHit.getContent();
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
            List<String> title = highlightFields.get("title");

            StringBuilder highKeyBuilder = new StringBuilder("");
            if(title!=null) {
                for (String part : title) {
                    highKeyBuilder.append(part);
                }
                postEs.setTitle(highKeyBuilder.toString());
                highKeyBuilder.delete(0,highKeyBuilder.length()-1);
            }

            List<String> content = highlightFields.get("content");
            if(content!=null){
                for(String part:content){
                    highKeyBuilder.append(part);
                }
                postEs.setContent(highKeyBuilder.toString());
            }
            postEses.add(postEs);
        }
        return postEses;
    }

}
