package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.entity.Post;
import org.springframework.stereotype.Repository;


public interface PostEsService {
    public Page<Post> searchFromEs(PostQueryRequest postQueryRequest);



}
