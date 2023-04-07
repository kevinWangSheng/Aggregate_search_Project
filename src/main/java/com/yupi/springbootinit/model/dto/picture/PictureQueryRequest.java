package com.yupi.springbootinit.model.dto.picture;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author wang
 * @create 2023-2023-17-16:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PictureQueryRequest extends PageRequest implements Serializable {
    private String searchText;

    private static final long serialVersionUID = 1L;


}
