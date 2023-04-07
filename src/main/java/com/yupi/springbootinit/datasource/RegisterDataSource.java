package com.yupi.springbootinit.datasource;

import com.yupi.springbootinit.model.enums.SearchTypeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wang
 * @create 2023-2023-22-21:24
 */
//这个类主要用来注册不同的DataSource类型
@Component
public class RegisterDataSource {

    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private UserDataSource userDataSource;

    private  Map<String,DataSource> typeDataSource;

    /**
     * 使用@PostConstruct，当依赖注入完成之后，会执行该方法进行对应的初始化
     */
    @PostConstruct
    public void init(){
//          对所有的DataSource类型根据对应的Menu的value进行对应的注册
        typeDataSource = new HashMap<String,DataSource>(){
            {
                put(SearchTypeEnum.PICTURE.getValue(),pictureDataSource);
                put(SearchTypeEnum.POST.getValue(), postDataSource);
                put(SearchTypeEnum.USER.getValue(), userDataSource);
            }
        };
    }

//    根据对应type类型返回对应的DataSource类型
    public DataSource getDataSourceByType(String type){
        if(typeDataSource==null){
            return null;
        }
        return typeDataSource.get(type);
    }
}
