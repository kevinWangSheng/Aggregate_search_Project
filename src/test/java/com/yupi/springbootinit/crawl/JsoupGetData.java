package com.yupi.springbootinit.crawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Set;

/**
 * @author wang
 * @create 2023-2023-16-0:29
 */
@SpringBootTest
public class JsoupGetData {

    @Test
    public void getHtml() throws IOException, InterruptedException {
        String keyword = "JVM";
        String url = String.format("https://zzk.cnblogs.com/s/blogpost?Keywords=%s&pageindex=2", keyword);

        System.setProperty("webdriver.chrome.driver", "D:\\Google\\chromedriver\\chromedriver.exe");
        // 创建一个Chrome浏览器驱动对象
        System.getProperties().setProperty("webdriver.chrome.driver", "D:\\Google\\chromedriver\\chromedriver.exe");
        ChromeOptions option = new ChromeOptions();

        //chrome.exe所在的地址
        option.setBinary("D:\\Google\\chromedriver\\chromedriver.exe");

        ChromeDriver webDriver = new ChromeDriver(option);

        //请求根地址
        webDriver.get(url);

        //页面信息
        WebElement htmlElement = webDriver.findElement(By.xpath("/html"));

        //当前页句柄
        String primaryHandle = webDriver.getWindowHandle();

        //换页
        Set<String> handles = webDriver.getWindowHandles();
        for (String handel : handles) {
            if (!handel.equals(primaryHandle)) {
                webDriver.switchTo().window(handel);
                break;
            }
        }
    }
    @Test
    public void test() throws IOException {
        String searchText = "哈夫曼树";
        String searchUrl = String.format("https://so.csdn.net/so/search?q=%s&urw=", searchText);
        String json = "{msg: \"查询成功\", code: 200, data: [{title: \"<em>哈夫曼树</em>(Huffman Tree)\",…},…]}";
        searchUrl = String.format("http://www.ofweek.com/newquery.action?keywords=%s&pagenum=1","高薪" );


        Document post = Jsoup.connect(searchUrl).post();

        System.out.println(1);


    }
}
