package com.yupi.springbootinit.model.enums;

import cn.hutool.core.util.ObjectUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wang
 * @create 2023-2023-21-23:39
 */

public enum SearchTypeEnum {
    // 表示搜索类型为图片类型
    PICTURE("图片","picture"),
//    表示搜索类型为文章类型
    POST("文章","post"),
//    为用户类型
    USER("用户","user");


    /**
     *
     * @return 返回对应每一个枚举对应映射出来的值
     */
    public static List<String> getValues(){
        return Arrays.stream(values()).map(item->item.value).collect(Collectors.toList());
    }

    public static SearchTypeEnum getEnumByValue(String value){
        if(ObjectUtil.isEmpty(value)){
            return null;
        }else{
            for(SearchTypeEnum searchTypeEnum: values()){
                if(searchTypeEnum.value.equals(value)){
                    return searchTypeEnum;
                }
            }
            return null;
        }
    }

    private String text;
    private String value;
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private SearchTypeEnum(String searchText, String type) {
        this.text = searchText;
        this.value = type;
    }



}
