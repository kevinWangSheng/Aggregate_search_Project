package com.yupi.springbootinit.model.dto.search;

import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wang
 * @create 2023-2023-18-10:19
 */
@Data
public class SearchBySelfRequest extends PageRequest implements Serializable {
//    前端发送过来的搜索关键词
    private String searchText;

//  定义前端发送过来的搜索类型，根据该类型匹配对应的搜索接口
    private String type;

    private static final long serialVersionUID = 1L;
}
