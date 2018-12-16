package com.yd1994.alpacablog.blogservice.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 博文-标签 关系表
 * </p>
 *
 * @author yd
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
@TableName(value = "alpaca_blog_article_article_tag")
public class ArticleArticleTagDO implements Serializable {

    /**
     * ArticleDO ID
     * @see com.yd1994.alpacablog.blogservice.entity.ArticleDO#id
     */
    @TableId
    private Long articleId;

    /**
     * ArticleTagDO ID
     * @see com.yd1994.alpacablog.blogservice.entity.ArticleTagDO#id
     */
    @TableId
    private Long articleTagId;

    public ArticleArticleTagDO() {
    }

    public ArticleArticleTagDO(Long articleId, Long articleTagId) {
        this.articleId = articleId;
        this.articleTagId = articleTagId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getArticleTagId() {
        return articleTagId;
    }

    public void setArticleTagId(Long articleTagId) {
        this.articleTagId = articleTagId;
    }

}
