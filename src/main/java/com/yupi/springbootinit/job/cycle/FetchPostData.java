package com.yupi.springbootinit.job.cycle;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yupi.springbootinit.esdao.PostEsDao;
import com.yupi.springbootinit.mapper.PostMapper;
import com.yupi.springbootinit.model.dto.post.PostEsDTO;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 增量同步帖子到 es
 *
 * @author wang
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class FetchPostData implements CommandLineRunner {

    @Resource
    private PostService postService;


    /**
     * 每分钟执行一次
     */
    @Override
    public void run(String ... args) throws IOException {
        // Set the URL of the search results page
        String keyword = "JVM";
        String searchUrl = "https://so.csdn.net/api/v3/search?q="+keyword;
//                "&t=all&p=1&s=0&tm=0&lv=-1&ft=0&l=&u=&ct=-1&pnt=-1&ry=-1&ss=-1&dct=-1&vco=-1&cc=-1&sc=-1&akt=-1&art=-1&ca=-1&prs=&pre=&ecc=-1&ebc=-1&ia=1&dId=&cl=-1&scl=-1&tcl=-1&platform=pc&ab_test_code_overlap=&ab_test_random_code=";

        // Request the search results page using Jsoup
        Document doc = Jsoup.connect(searchUrl).ignoreContentType(true).get();

        // Extract links to the articles from the search results page
        String body = doc.body().text();

        Map<String,Object> jsonMap = new HashMap<>();
        jsonMap = JSONUtil.toBean(body, jsonMap.getClass());

//        all the post result in it
        JSONArray result_vos = (JSONArray) jsonMap.get("result_vos");

        List<Post> postList = new ArrayList<>();
        for (Object result_vo : result_vos) {
            JSONObject jsonObject = (JSONObject) result_vo;

            Post post = new Post();
//            设置文章标题
            post.setTitle(jsonObject.get("title",String.class));
//            设置对应内容
            post.setContent(jsonObject.get("description",String.class));
//            设置对应标签
            JSONArray search_tag = (JSONArray) jsonObject.get("search_tag");
            if(search_tag!=null) {
                post.setTags(Arrays.toString(search_tag.toArray()));
            }
            Integer loveNumber = jsonObject.get("view", Integer.class);
//            设置点赞数
            post.setThumbNum(loveNumber);
            post.setFavourNum(loveNumber);
//            设置用户id
//            post.setUserId(jsonObject.get("author_id",Long.class));
            post.setUserId(1L);
//            设置创建时间
            post.setCreateTime(jsonObject.get("created_at", Date.class));
//            设置修改时间
            post.setUpdateTime(new Date());
//            设置url
            String url = jsonObject.get("url_location", String.class);
            post.setUrl(url.substring(0,url.indexOf('?')));

            postList.add(post);
        }
        boolean saveSuccess = postService.saveBatch(postList);

        if(saveSuccess){
            log.info("添加影响了行数："+postList.size());
        }else{
            log.error("添加失败");
        }

    }
}
