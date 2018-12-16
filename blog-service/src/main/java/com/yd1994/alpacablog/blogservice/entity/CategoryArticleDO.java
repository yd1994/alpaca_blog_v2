package com.yd1994.alpacablog.blogservice.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 博文-分类 关系表
 * </p>
 *
 * @author yd
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
@TableName(value = "alpaca_blog_category_article")
public class CategoryArticleDO implements Serializable {

    /**
     * ArticleDO ID
     * @see com.yd1994.alpacablog.blogservice.entity.ArticleDO#id
     */
    @TableId
    private Long articleId;

    /**
     * CategoryDO ID
     * @see com.yd1994.alpacablog.blogservice.entity.CategoryDO#id
     */
    @TableId
    private Long categoryId;

    public CategoryArticleDO() {
    }

    public CategoryArticleDO(Long articleId, Long categoryId) {
        this.articleId = articleId;
        this.categoryId = categoryId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
