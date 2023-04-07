package com.yupi.springbootinit.service.impl;

import com.yupi.springbootinit.model.dto.post.PostEs;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.service.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostEsServiceNewImpl {

    @Autowired
    private PostRepository postRepository;

    public List<PostEs> findByTitle(String title) {
        return postRepository.findByTitle(title);
    }

    public List<PostEs> findByContent(String content) {
        return postRepository.findByContent(content);
    }

    public List<PostEs> findByTags(String tags) {
        return postRepository.findByTags(tags);
    }

    public List<PostEs> findByUserId(String userId) {
        return postRepository.findByUserId(userId);
    }

    public List<PostEs> findByCreateTime(String createTime) {
        return postRepository.findByCreateTime(createTime);
    }

    public List<PostEs> findByUpdateTime(String updateTime) {
        return postRepository.findByUpdateTime(updateTime);
    }

    public List<PostEs> findByIsDelete(String isDelete) {
        return postRepository.findByIsDelete(isDelete);
    }

    public SearchHits<PostEs> search(String searchText){
        return postRepository.query(searchText);
    }

    public void deleteById(String id){
        postRepository.deleteById(id);
    }
}
