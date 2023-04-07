package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.entity.Picture;

/**
 * @author wang
 * @create 2023-2023-17-16:21
 */
public interface PictureService {
    Page<Picture> getPictures(String searchText,long pageNum,long pageSize);


}
