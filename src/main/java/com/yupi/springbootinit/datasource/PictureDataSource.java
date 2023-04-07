package com.yupi.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.service.PictureService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wang
 * @create 2023-2023-22-20:57
 */
@Component
public class PictureDataSource implements DataSource{

    @Resource
    private PictureService pictureService;

    @Override
    public Page doSearch(String searchText, long pageNum, long pageSize) {

        Page<Picture> pictures = pictureService.getPictures(searchText, pageNum, pageSize);
        return pictures;
    }



}
