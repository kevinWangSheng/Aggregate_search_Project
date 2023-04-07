package com.yupi.springbootinit.model.dto.post;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author wang
 * @create 2023-2023-31-12:46
 */
@Document(indexName = "post")
@Data
@Slf4j
public class PostEs {

    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;

    @Field(type = FieldType.Keyword)
    private String tags;

    @Field(type = FieldType.Keyword)
    private String userId;

    @Field(type = FieldType.Keyword)
    private String createTime;

    @Field(type = FieldType.Keyword)
    private String updateTime;

    @Field(type = FieldType.Keyword)
    private String isDelete;



}
