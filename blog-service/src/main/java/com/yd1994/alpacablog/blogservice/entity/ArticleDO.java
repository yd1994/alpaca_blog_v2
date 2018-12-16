package com.yd1994.alpacablog.blogservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * <p>
 * 博文表
 * </p>
 *
 * @author yd
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
@TableName("alpaca_blog_article")
public class ArticleDO implements Serializable {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 是否被删除
     */
    // @TableLogic
    private Boolean deleted;

    /**
     * 乐观锁
     */
    @Version
    private Long version;

    /**
     * 创建日期
     */
    private LocalDateTime gmtCreated;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 最后修改日期
     */
    private LocalDateTime gmtModified;

    /**
     * 最后修改人
     */
    private String modifiedBy;

    /**
     * 所属分类
     */
    @TableField(exist = false)
    private CategoryDO categoryDO;

    /**
     * 拥有标签
     */
    @TableField(exist = false)
    private Set<ArticleTagDO> articleTagDOSet;

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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public LocalDateTime getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public CategoryDO getCategoryDO() {
        return categoryDO;
    }

    public void setCategoryDO(CategoryDO categoryDO) {
        this.categoryDO = categoryDO;
    }

    public Set<ArticleTagDO> getArticleTagDOSet() {
        return articleTagDOSet;
    }

    public void setArticleTagDOSet(Set<ArticleTagDO> articleTagDOSet) {
        this.articleTagDOSet = articleTagDOSet;
    }
}
