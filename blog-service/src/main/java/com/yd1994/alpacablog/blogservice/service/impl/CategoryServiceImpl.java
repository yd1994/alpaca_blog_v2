package com.yd1994.alpacablog.blogservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yd1994.alpacablog.blogservice.common.exception.ResourceIsDeletedException;
import com.yd1994.alpacablog.blogservice.common.exception.ResourceNotFoundException;
import com.yd1994.alpacablog.blogservice.common.http.ResponseBody;
import com.yd1994.alpacablog.blogservice.common.http.RestRequestParam;
import com.yd1994.alpacablog.blogservice.dto.Category;
import com.yd1994.alpacablog.blogservice.entity.CategoryDO;
import com.yd1994.alpacablog.blogservice.mapper.CategoryMapper;
import com.yd1994.alpacablog.blogservice.service.ICategoryService;
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
 * 分类表 服务实现类
 * </p>
 *
 * @author yd
 * @since 2018-11-26
 */
@Slf4j
@CacheConfig(cacheNames = "categories")
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseBody<Category> list(RestRequestParam restRequestParam) throws Exception {
        IPage<CategoryDO> page = restRequestParam.getPageInfo("name. description");
        QueryWrapper<CategoryDO> queryWrapper = new QueryWrapper<>();
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
        page = this.categoryMapper.selectPage(page, queryWrapper);
        List<Category> categoryList = new ArrayList<>(page.getRecords().size());
        page.getRecords().forEach(categoryDO ->  categoryList.add(new Category(categoryDO)));
        return new ResponseBody<>(categoryList, page.getTotal());
    }

    @Cacheable(key = "#id", unless = "#result == null")
    @Override
    public Category get(Long id) throws Exception {
        CategoryDO categoryDO = Optional.ofNullable(this.categoryMapper.selectById(id))
                .orElseThrow(() -> new ResourceNotFoundException());
        if (categoryDO.getDeleted()) {
            throw new ResourceIsDeletedException();
        }
        return new Category(categoryDO);
    }

    @Transactional
    @Override
    public int add(Category category, String username) throws Exception {
        Assert.notNull(category, "参数有误。");

        CategoryDO categoryDO = category.toCategoryDO();
        categoryDO.setId(null);

        categoryDO.setGmtCreated(LocalDateTime.now());
        categoryDO.setCreatedBy(username);
        categoryDO.setGmtModified(LocalDateTime.now());
        categoryDO.setModifiedBy(username);

        int result = this.categoryMapper.insert(categoryDO);

        if (result == 0) {
            log.error("添加失败：" + category);
            throw new IllegalArgumentException("参数有误");
        }
        category.setId(categoryDO.getId());

        return result;
    }

    @CacheEvict(key = "#category.id")
    @Transactional
    @Override
    public int update(Category category, String username) throws Exception {
        Assert.notNull(category, "参数有误。");

        CategoryDO categoryDO = category.toCategoryDO();

        categoryDO.setGmtModified(LocalDateTime.now());
        categoryDO.setModifiedBy(username);

        int result = this.categoryMapper.updateById(categoryDO);

        if (result == 0) {
            log.error("修改失败：" + category);
            throw new IllegalArgumentException("参数有误");
        }
        category.setId(categoryDO.getId());
        return result;
    }

    @CacheEvict(key = "#id")
    @Transactional
    @Override
    public int delete(Long id, String username) throws Exception {
        CategoryDO categoryDO = Optional.ofNullable(this.categoryMapper.selectById(id))
                .orElseThrow(() -> new ResourceNotFoundException());

        categoryDO.setDeleted(true);

        categoryDO.setGmtModified(LocalDateTime.now());
        categoryDO.setModifiedBy(username);

        int result = this.categoryMapper.updateById(categoryDO);

        if (result == 0) {
            log.error("删除失败：" + id);
            throw new ResourceNotFoundException("删除失败：资源不存在");
        }

        return result;
    }
}
