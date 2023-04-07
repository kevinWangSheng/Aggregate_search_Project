package com.yupi.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.model.vo.SearchVo;
import com.yupi.springbootinit.model.vo.UserVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * @author wang
 * @create 2023-2023-22-22:22
 */
@Component
public class ConcurrcySearchDataSource {

    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private UserDataSource userDataSource;

    public SearchVo concurySearch(String searchText,long pageNum,long pageSize){
        SearchVo searchVo = new SearchVo();
        CompletableFuture<Page<Picture>> pictureSync = CompletableFuture.supplyAsync(() -> {
            Page<Picture> picturePage = pictureDataSource.doSearch(searchText, pageNum, pageSize);
            return picturePage;
        });
        CompletableFuture<Page<UserVO>> userSync = CompletableFuture.supplyAsync(() -> {
            Page<UserVO> userVOPage = userDataSource.doSearch(searchText,pageNum,pageSize);
            return userVOPage;
        });

        CompletableFuture<Page<Post>> postSync = CompletableFuture.supplyAsync(() -> {
            Page<Post> postPage = postDataSource.doSearch(searchText,pageNum,pageSize);
            return postPage;
        });

        CompletableFuture.allOf(pictureSync,userSync,postSync).join();
        Page<Picture> picturePage = null;
        Page<Post> postPage = null;
        Page<UserVO> userVOPage = null;
        try {
            picturePage = pictureSync.get();
            postPage = postSync.get();
            userVOPage = userSync.get();
        } catch (Exception e) {
            throw new BusinessException(1,"search error");
        }
        searchVo.setPictureList(picturePage.getRecords());
        searchVo.setPostList(postPage.getRecords());
        searchVo.setUserList(userVOPage.getRecords());

        return searchVo;
    }
}
