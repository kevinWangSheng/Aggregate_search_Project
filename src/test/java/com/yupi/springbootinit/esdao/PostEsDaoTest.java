package com.yupi.springbootinit.esdao;

import com.yupi.springbootinit.model.dto.post.PostEsDTO;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.service.PostEsService;
import com.yupi.springbootinit.service.PostService;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 帖子 ES 操作测试
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@SpringBootTest
public class PostEsDaoTest {

    @Resource
    private PostEsDao postEsDao;

    @Resource
    private PostService postService;

    @Resource
    private PostEsService postEsService;

    @Test
    void test() {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Post> page =
                postService.searchFromEs(postQueryRequest);
        System.out.println(page);
    }

    @Test
    void testSelect() {
        System.out.println(postEsDao.count());
        Page<PostEsDTO> PostPage = postEsDao.findAll(
                PageRequest.of(0, 5, Sort.by("createTime")));
        List<PostEsDTO> postList = PostPage.getContent();
        System.out.println(postList);
    }

    @Test
    void testAdd() {
        PostEsDTO postEsDTO = new PostEsDTO();
        postEsDTO.setId(2L);
        postEsDTO.setTitle("这是一个新生成的id，是系统自动生成的，你吗的，混蛋啊你的，你知道法律吗？");
        postEsDTO.setContent("真他妈混蛋啊你，你都不知道法律，你怎么出来混啊，草你吗的");
        postEsDTO.setTags(Arrays.asList("骂人", "混蛋"));
        postEsDTO.setUserId(1L);
        postEsDTO.setCreateTime(new Date());
        postEsDTO.setUpdateTime(new Date());
        postEsDTO.setIsDelete(0);
        postEsDao.save(postEsDTO);
        System.out.println(postEsDTO.getId());
    }

    @Test
    void testFindById() {
        Optional<PostEsDTO> postEsDTO = postEsDao.findById(1L);
        System.out.println(postEsDTO);
    }

    @Test
    void testCount() {
        System.out.println(postEsDao.count());
    }

    @Test
    void testFindByCategory() {
        List<PostEsDTO> postEsDaoTestList = postEsDao.findByUserId(1L);
        System.out.println(postEsDaoTestList);
    }

    @Test
    void testDelete(){
        postEsDao.deletePostEsDTOByUserId(1l);
    }

    @Test
    void testGetStringId(){
//        在查询到对应的数据的时候，因为你在实体类postEs中定义的id是long类型的，所以在转化的时候会直接报错 ConversionFailedException。
        List<PostEsDTO> byId = postEsDao.findPostEsDTOByTitle("混蛋");
        System.out.println(byId);
    }

    @Test
    void getPostEsByTitle(){
        List<PostEsDTO> esDTOByTitle = postEsDao.findPostEsDTOByTitle("混蛋");
        System.out.println(esDTOByTitle);
    }


    @Test
    void testSearch(){
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setCurrent(1);
        postQueryRequest.setPageSize(10);
        postQueryRequest.setSearchText("数构");
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Post> postPage = postEsService.searchFromEs(postQueryRequest);
        System.out.println(Arrays.toString(postPage.getRecords().toArray()));
    }


}
