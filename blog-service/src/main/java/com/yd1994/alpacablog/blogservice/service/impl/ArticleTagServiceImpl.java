package com.yd1994.alpacablog.blogservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yd1994.alpacablog.blogservice.common.exception.ResourceIsDeletedException;
import com.yd1994.alpacablog.blogservice.common.exception.ResourceNotFoundException;
import com.yd1994.alpacablog.blogservice.common.http.ResponseBody;
import com.yd1994.alpacablog.blogservice.common.http.RestRequestParam;
import com.yd1994.alpacablog.blogservice.dto.ArticleTag;
import com.yd1994.alpacablog.blogservice.entity.ArticleTagDO;
import com.yd1994.alpacablog.blogservice.mapper.ArticleTagMapper;
import com.yd1994.alpacablog.blogservice.service.IArticleTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 博文表 服务实现类
 * </p>
 *
 * @author yd
 * @since 2018-11-26
 */
@Slf4j
@CacheConfig(cacheNames = "articleTag")
@Service
public class ArticleTagServiceImpl implements IArticleTagService {

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public ResponseBody<ArticleTag> list(RestRequestParam restRequestParam) {
        IPage<ArticleTagDO> page = restRequestParam.getPageInfo("name. description");
        QueryWrapper<ArticleTagDO> queryWrapper = new QueryWrapper<>();
        queryWrapper = queryWrapper.eq("deleted", false);
        if (restRequestParam.getBefore() != null) {
            queryWrapper = queryWrapper.le(restRequestParam.getBeforeBy(), restRequestParam.getBefore());
        }
        if (restRequestParam.getAfter() != null) {
            queryWrapper = queryWrapper.ge(restRequestParam.getAfterBy(), restRequestParam.getAfter());
        }
        if (!StringUtils.isEmpty(restRequestParam.getView())) {
            queryWrapper = queryWrapper.and(wrapper -> {
                return wrapper.like("name", restRequestParam.getView())
                        .or().like("description", restRequestParam.getView());
            });
        }
        page = this.articleTagMapper.selectPage(page, queryWrapper);

        List<ArticleTagDO> articleTagDOList = page.getRecords();
        List<ArticleTag> articleTagList = new ArrayList<>(articleTagDOList.size());
        articleTagDOList.forEach(articleTagDO -> articleTagList.add(new ArticleTag(articleTagDO)));
        return new ResponseBody<ArticleTag>(articleTagList, page.getTotal());
    }

    @Cacheable(key = "#id", unless = "#result == null")
    @Override
    public ArticleTag get(Long id) throws Exception {
        ArticleTagDO articleTagDO = Optional.ofNullable(this.articleTagMapper.selectById(id))
                .orElseThrow(() -> new ResourceNotFoundException());
        if (articleTagDO.getDeleted()) {
            throw new ResourceIsDeletedException();
        }
        return new ArticleTag(articleTagDO);
    }

    @Transactional
    @Override
    public int add(ArticleTag articleTag, String username) throws Exception {
        Assert.notNull(articleTag, "参数有误。");

        ArticleTagDO articleTagDO = articleTag.toArticleTagDO();
        articleTagDO.setId(null);

        articleTagDO.setGmtCreated(LocalDateTime.now());
        articleTagDO.setCreatedBy(username);
        articleTagDO.setGmtModified(LocalDateTime.now());
        articleTagDO.setModifiedBy(username);

        int result = this.articleTagMapper.insert(articleTagDO);

        if (result == 0) {
            log.error("添加失败：" + articleTag);
            throw new IllegalArgumentException("参数有误");
        }

        articleTag.setId(articleTagDO.getId());

        return result;
    }

    @CacheEvict(key = "#articleTag.id")
    @Transactional
    @Override
    public int update(ArticleTag articleTag, String username) throws Exception {
        Assert.notNull(articleTag, "参数有误。");

        ArticleTagDO articleTagDO = articleTag.toArticleTagDO();

        articleTagDO.setGmtModified(LocalDateTime.now());
        articleTagDO.setModifiedBy(username);

        int result = this.articleTagMapper.updateById(articleTagDO);

        if (result == 0) {
            log.error("添加失败：" + articleTag);
            throw new IllegalArgumentException("参数有误");
        }

        return result;
    }

    @CacheEvict(key = "#id")
    @Transactional
    @Override
    public int delete(Long id, String username) throws Exception {

        ArticleTagDO articleTagDO = Optional.ofNullable(this.articleTagMapper.selectById(id))
                .orElseThrow(() -> new ResourceNotFoundException());

        articleTagDO.setDeleted(true);

        articleTagDO.setGmtModified(LocalDateTime.now());
        articleTagDO.setModifiedBy(username);

        int result = this.articleTagMapper.updateById(articleTagDO);

        if (result == 0) {
            log.error("删除失败：" + id);
            throw new ResourceNotFoundException("删除失败：资源不存在");
        }

        return result;
    }
}
