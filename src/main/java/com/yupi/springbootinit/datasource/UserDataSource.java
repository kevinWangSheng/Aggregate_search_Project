package com.yupi.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.dto.user.UserQueryRequest;
import com.yupi.springbootinit.model.vo.UserVO;
import com.yupi.springbootinit.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author wang
 * @create 2023-2023-22-20:58
 */
@Component
public class UserDataSource implements DataSource{

    @Resource
    private UserService userService;

    @Override
    public Page doSearch(String searchText, long pageNum, long pageSize) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        Page<UserVO> userVOPage = userService.queryUserByPage(userQueryRequest);
        return userVOPage;
    }
}
