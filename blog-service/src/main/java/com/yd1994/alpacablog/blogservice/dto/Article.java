package com.yd1994.alpacablog.blogservice.dto;

import com.yd1994.alpacablog.blogservice.entity.ArticleDO;
import com.yd1994.alpacablog.blogservice.entity.ArticleTagDO;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 博文 数据传输对象
 * <p/>
 *
 * @author yd
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class Article implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 简介
     */
    private String summary;

    /**
     * 点击量
     */
    private Long traffic;

    /**
     * 是否置顶
     */
    private Boolean top;

    /**
     * 乐观锁
     */
    private Long version;

    /**
     * 创建日期
     */
    private LocalDateTime created;

    /**
     * 最后修改日期
     */
    private LocalDateTime modified;

    /**
     * 所属分类
     */
    private Category category;

    /**
     * 拥有标签
     */
    private List<ArticleTag> articleTagSet;

    public Article() {
    }

    public Article(ArticleDO articleDO) {
        if (articleDO != null) {
            BeanUtils.copyProperties(articleDO, this);
            // 时间转换
            this.created = articleDO.getGmtCreated();
            this.modified = articleDO.getGmtModified();
            // 分类转换
            if (articleDO.getCategoryDO() != null) {
                this.category = new Category(articleDO.getCategoryDO());
            }
            Set<ArticleTagDO> articleTagDOSet = articleDO.getArticleTagDOSet();
            if (articleTagDOSet != null) {
                Set<ArticleTag> articleTagSet = new HashSet<>(articleTagDOSet.size());
                articleTagDOSet.forEach(articleTagDO -> articleTagSet.add(new ArticleTag(articleTagDO)));
                this.articleTagSet = new ArrayList<>(articleTagSet);
            } else {
                this.articleTagSet = Collections.EMPTY_LIST;
            }
        }
    }

    public ArticleDO toArticleDO() {
        ArticleDO articleDO = new ArticleDO();
        BeanUtils.copyProperties(this, articleDO);
        // articleDO.setGmtCreated(this.getCreated());
        // articleDO.setGmtModified(this.getModified());
        if (this.getCategory() != null) {
            articleDO.setCategoryDO(this.getCategory().toCategoryDO());
        }
        if (this.articleTagSet != null) {
            Set<ArticleTagDO> articleTagDOSet = new HashSet<>(articleTagSet.size());
            articleTagSet.forEach(articleTag -> articleTagDOSet.add(articleTag.toArticleTagDO()));
            articleDO.setArticleTagDOSet(articleTagDOSet);
        }
        return articleDO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getTraffic() {
        return traffic;
    }

    public void setTraffic(Long traffic) {
        this.traffic = traffic;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ArticleTag> getArticleTagSet() {
        return articleTagSet;
    }

    public void setArticleTagSet(List<ArticleTag> articleTagSet) {
        this.articleTagSet = articleTagSet;
    }
}
