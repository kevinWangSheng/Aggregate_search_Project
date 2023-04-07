package com.yupi.springbootinit.service;

import com.yupi.springbootinit.model.dto.post.PostEs;
import com.yupi.springbootinit.model.entity.Post;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
    一个使用es搜索数据的接口
    这里对应的数据是PostEs
 */
@Repository
public interface PostRepository extends ElasticsearchRepository<PostEs, String> {

    List<PostEs> findByTitle(String title);

    List<PostEs> findByContent(String content);

    List<PostEs> findByTags(String tags);

    List<PostEs> findByUserId(String userId);

    List<PostEs> findByCreateTime(String createTime);

    List<PostEs> findByUpdateTime(String updateTime);

    List<PostEs> findByIsDelete(String isDelete);

//    @Query("{\"multi_match\":{\"query\": \"?0\",\"fiedls\" :[\"title\",\"content\"]}}")
    @Query("{\"multi_match\": {\"query\": \"?0\",\"fields\": [\"title\", \"content\"]}}")
    @Highlight(fields = {
            @HighlightField(name = "title"),
            @HighlightField(name = "content")
    })
    SearchHits<PostEs> query(String searchTest);



}
