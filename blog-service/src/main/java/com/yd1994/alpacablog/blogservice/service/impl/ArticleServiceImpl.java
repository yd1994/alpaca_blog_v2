package com.yd1994.alpacablog.blogservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yd1994.alpacablog.blogservice.common.exception.ResourceIsDeletedException;
import com.yd1994.alpacablog.blogservice.common.exception.ResourceNotFoundException;
import com.yd1994.alpacablog.blogservice.common.http.ResponseBody;
import com.yd1994.alpacablog.blogservice.common.http.RestRequestParam;
import com.yd1994.alpacablog.blogservice.dto.Article;
import com.yd1994.alpacablog.blogservice.entity.*;
import com.yd1994.alpacablog.blogservice.mapper.ArticleArticleTagMapper;
import com.yd1994.alpacablog.blogservice.mapper.ArticleMapper;
import com.yd1994.alpacablog.blogservice.mapper.CategoryArticleMapper;
import com.yd1994.alpacablog.blogservice.service.IArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * <p>
 * 博文表 服务实现类
 * </p>
 *
 * @author yd
 * @since 2018-11-26
 */
@Slf4j
@CacheConfig(cacheNames = "articles")
@Service
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryArticleMapper categoryArticleMapper;

    @Autowired
    private ArticleArticleTagMapper articleArticleTagMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Cacheable(key = "#id", unless = "#result == null")
    @Override
    public Article get(Long id) throws Exception {
        ArticleDO articleDO = Optional.ofNullable(this.articleMapper.getById(id)).orElseThrow(() -> new ResourceNotFoundException());
        if (articleDO.getDeleted()) {
            throw new ResourceIsDeletedException();
        }
        String trafficNameInRedis = "articles::" + id + ":traffic";
        Object traffic = this.redisTemplate.opsForValue().get(trafficNameInRedis);
        if (traffic == null) {
            this.redisTemplate.opsForValue().set(trafficNameInRedis, articleDO.getTraffic());
        }
        return new Article(articleDO);
    }

    @Override
    public ResponseBody<Article> list(RestRequestParam restRequestParam, Long categoryId) {
        restRequestParam.check("id, title, content, summary, traffic, top");
        List<ArticleDO> articleDOList = this.articleMapper.list(restRequestParam,
                restRequestParam.getSize() * (restRequestParam.getPage() - 1), restRequestParam.getSize(), categoryId);
        Long total = this.articleMapper.listTotal(restRequestParam, categoryId);
        List<Article> articleList = new ArrayList<>(articleDOList.size());
        articleDOList.forEach(articleDO -> articleList.add(new Article(articleDO)));
        return new ResponseBody<>(articleList, total);
    }

    @Override
    public Long incrementTraffic(Long id) {
        String trafficNameInRedis = "articles::" + id + ":traffic";
        Long traffic = (Long) this.redisTemplate.opsForValue().increment(trafficNameInRedis, 1L);
        if (traffic % 10 == 0) {
            ArticleDO articleDO = new ArticleDO();
            articleDO.setId(id);
            articleDO.setTraffic(traffic);
            // 当访问量为10的倍数，持久化到
            this.articleMapper.updateById(articleDO);
        }
        return traffic;
    }

    @Transactional
    @Override
    public int add(Article article, String username) {
        Assert.notNull(article, "参数有误");

        ArticleDO articleDO = article.toArticleDO();
        CategoryDO categoryDO = articleDO.getCategoryDO();
        Set<ArticleTagDO> articleTagDOSet = articleDO.getArticleTagDOSet();

        articleDO.setGmtCreated(LocalDateTime.now());
        articleDO.setCreatedBy(username);
        articleDO.setGmtModified(LocalDateTime.now());
        articleDO.setModifiedBy(username);

        int result = this.articleMapper.insert(articleDO);

        if (result == 0) {
            log.error("添加失败：" + article);
            throw new IllegalArgumentException("参数有误");
        }
        insertArticleTagAndCategory(articleDO.getId(), categoryDO, articleTagDOSet);

        article.setId(articleDO.getId());
        return result;
    }

    @CacheEvict(key = "#article.id")
    @Transactional
    @Override
    public int update(Article article, String username) throws Exception {
        Assert.notNull(article, "参数有误");
        Assert.notNull(article.getVersion(), "参数有误：version不能为空");

        ArticleDO articleDO = article.toArticleDO();
        CategoryDO categoryDO = articleDO.getCategoryDO();
        Set<ArticleTagDO> articleTagDOSet = articleDO.getArticleTagDOSet();

        articleDO.setGmtModified(LocalDateTime.now());
        articleDO.setModifiedBy(username);
        int result = this.articleMapper.updateById(articleDO);

        if (result == 0) {
            log.error("修改失败：" + article);
            throw new IllegalArgumentException("参数有误");
        }
        deleteArticleTagAndCategory(articleDO.getId(), articleDO.getCategoryDO() != null, articleDO.getArticleTagDOSet() != null);
        insertArticleTagAndCategory(articleDO.getId(), categoryDO, articleTagDOSet);

        return result;
    }

    @CacheEvict(key = "#id")
    @Transactional
    @Override
    public int delete(Long id, String username) throws Exception {
        ArticleDO articleDO = new ArticleDO();

        articleDO.setId(id);
        articleDO.setDeleted(true);

        articleDO.setGmtModified(LocalDateTime.now());
        articleDO.setModifiedBy(username);

        int result = this.articleMapper.updateById(articleDO);
        if (result == 0) {
            log.error("删除失败：" + id);
            throw new ResourceNotFoundException("删除失败：资源不存在");
        }
        // deleteArticleTagAndCategory(id, true, true);
        return result;
    }


    /**
     * 添加 categoryDO ID 跟 articleTagDO ID 到关系表
     * @param articleId com.yd1994.alpacablog.blogservice.entity.ArticleDO#id
     * @param categoryDO
     * @param articleTagDOSet
     */
    private void insertArticleTagAndCategory(Long articleId, CategoryDO categoryDO, Set<ArticleTagDO> articleTagDOSet) {

        if (categoryDO != null && categoryDO.getId() != null && categoryDO.getId() >= 0) {
            this.categoryArticleMapper.insert(new CategoryArticleDO(articleId, categoryDO.getId()));
        }
        if (articleTagDOSet != null) {
            articleTagDOSet.stream()
                    .filter(articleTagDO -> articleTagDO.getId() != null && articleTagDO.getId() >= 0)
                    .forEach(articleTagDO -> this.articleArticleTagMapper.insert(new ArticleArticleTagDO(articleId, articleTagDO.getId())));
        }

    }

    /**
     *
     * 根据 articleId删除 CategoryArticleDO， ArticleArticleTagDO 中的关系
     *
     * @param articleId com.yd1994.alpacablog.blogservice.entity.ArticleDO#id
     * @param deleteCategoryArticle 是否删除CategoryArticle
     * @param deleteArticleArticleTag 是否删除ArticleArticleTag
     */
    private void deleteArticleTagAndCategory(Long articleId, boolean deleteCategoryArticle, boolean deleteArticleArticleTag) {
        Wrapper deleteWrapper = new QueryWrapper<>().eq("article_id", articleId);
        if (deleteCategoryArticle) {
            this.categoryArticleMapper.delete(deleteWrapper);
        }
        if (deleteArticleArticleTag) {
            this.articleArticleTagMapper.delete(deleteWrapper);
        }
    }
}
