package com.yupi.springbootinit.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wang
 * @create 2023-2023-17-16:23
 */
@Service
@Slf4j
public class PictureServiceImpl implements PictureService {
    @Override
    public Page<Picture> getPictures(String searchText,long pageNum,long pageSize) {
        if(searchText==null || searchText.equals("")){
            searchText="丁元英";
        }

        String url = String.format("https://www.bing.com/images/search?q=%s&first=%d", searchText,pageNum);
        WebClient webClient = new WebClient();
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage pageHtml = null;
        Document doc = null;
        try {
            pageHtml = webClient.getPage(url);
            doc = Jsoup.parse(pageHtml.asXml());
        } catch (Exception e) {
            log.info("有点出错了");
        }

        List<Picture> pictures = new ArrayList<>();
        Elements elements = doc.getElementsByClass("imgpt");
        for(Element element:elements){
            String m = element.getElementsByClass("iusc").get(0).attr("m");
            Map<String,Object> map = JSONUtil.toBean(m, Map.class);
            String  murl = (String) map.get("murl");
            String title = (String) map.get("t");

            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(murl);
            pictures.add(picture);
            if(pictures.size()>=pageSize){
                break;
            }
        }
        Page<Picture> pagePicture = new Page<>(pageNum,pageSize);

        pagePicture.setRecords(pictures);

        return pagePicture;
    }
}
