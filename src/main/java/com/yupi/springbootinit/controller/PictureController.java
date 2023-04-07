package com.yupi.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.model.dto.picture.PictureQueryRequest;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author wang
 * @create 2023-2023-17-16:32
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {
    @Resource
    private PictureService pictureService;


    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPicturesByPicture(@RequestBody PictureQueryRequest pictureQueryRequest, HttpServletRequest request){
        long pageCurrent = pictureQueryRequest.getCurrent();
        long pageSize = pictureQueryRequest.getPageSize();
        String searchText = pictureQueryRequest.getSearchText();
        Page<Picture> pictures = pictureService.getPictures(searchText, pageCurrent, pageSize);
        System.out.println(1);
        return ResultUtils.success(pictures);
    }
}
