package com.yupi.springbootinit.model.vo;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.entity.Post;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * @author wang
 * @create 2023-2023-18-10:16
 */
@Data
public class SearchVo implements Serializable {

    private List<Picture> pictureList;

    private List<Post> postList;

    private List<UserVO> userList;

    private List<?> dataList;

    private static final long serialVersionUID = 1L;

}


class test implements Comparable<test>{

    @Override
    public int compareTo(@NotNull test o) {
        return 0;
    }
}
