package com.yupi.springbootinit;

import cn.hutool.Hutool;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.service.PostService;
import com.yupi.springbootinit.service.impl.PostServiceImpl;
import org.eclipse.parsson.JsonUtil;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.*;

/**
 * @author wang
 * @create 2023-2023-15-23:53
 */
@SpringBootTest
public class Crawler {


    @Test
    public void fetchPicTest() throws IOException {
        String keyword = "丁元英";
        String url = String.format("https://www.bing.com/images/search?q=%s&first=1", keyword);
        WebClient webClient = new WebClient();
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        HtmlPage page = webClient.getPage(url);

        Document doc = Jsoup.parse(page.asXml());
        List<Picture> pictures = new ArrayList<>();
        Elements elements = doc.getElementsByClass("iuscp isv");
        for(Element element:elements){
            String m = element.getElementsByClass("iusc").get(0).attr("m");
            Map<String,Object> map = JSONUtil.toBean(m, Map.class);
            String  murl = (String) map.get("murl");
            String title = (String) map.get("t");

            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(murl);

            pictures.add(picture);
        }
        System.out.println(doc);

        Document document = Jsoup.connect(url).
                ignoreContentType(true).
                ignoreHttpErrors(true).
                userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").
                get();

        System.out.println(document);
    }


    public static PostService getPostService(){
        return new PostServiceImpl();
    }


    @Autowired
    SqlSessionTemplate sqlSessionTemplate;


    @Test
    public void crawlerTest() throws IOException {
        // Set the URL of the search results page
        String keyword = "JVM";
        String searchUrl = "https://so.csdn.net/api/v3/search?q="+keyword+"&t=all&p=1&s=0&tm=0&lv=-1&ft=0&l=&u=&ct=-1&pnt=-1&ry=-1&ss=-1&dct=-1&vco=-1&cc=-1&sc=-1&akt=-1&art=-1&ca=-1&prs=&pre=&ecc=-1&ebc=-1&ia=1&dId=&cl=-1&scl=-1&tcl=-1&platform=pc&ab_test_code_overlap=&ab_test_random_code=";

        // Request the search results page using Jsoup
        Document doc = Jsoup.connect(searchUrl).ignoreContentType(true).get();

        String text = doc.body().text();
        System.out.println(text);

        // Extract links to the articles from the search results page
        String body = doc.body().html();

        Map<String,Object> jsonMap = new HashMap<>();
//        jsonMap = JSONUtil.toBean(body, jsonMap.getClass());

        jsonMap = JSONUtil.toBean(text, jsonMap.getClass());

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
            post.setUserId(1l);
//            设置创建时间
            post.setCreateTime(jsonObject.get("created_at", Date.class));
//            设置修改时间
            post.setUpdateTime(new Date());
//            设置url
            String url = jsonObject.get("url_location", String.class);
            post.setUrl(url.substring(0,url.indexOf('?')));

            postList.add(post);
        }

        getPostService().saveBatch(postList);
    }


}
