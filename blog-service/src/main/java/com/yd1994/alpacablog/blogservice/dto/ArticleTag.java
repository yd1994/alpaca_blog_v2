package com.yd1994.alpacablog.blogservice.dto;

import com.yd1994.alpacablog.blogservice.entity.ArticleTagDO;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 标签 数据传输对象
 * </p>
 *
 * @author yd
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class ArticleTag implements Serializable {


    private Long id;
    /**
     * 标签名称
     */
    private String name;
    /**
     * 标签描述
     */
    private String description;
    /**
     * 是否可用
     */
    private Boolean available;
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

    public ArticleTag() {
    }

    public ArticleTag(ArticleTagDO articleTagDO) {
        if (articleTagDO != null) {
            BeanUtils.copyProperties(articleTagDO, this);
            // 时间转换
            this.created = articleTagDO.getGmtCreated();
            this.modified = articleTagDO.getGmtModified();
        }
    }

    public ArticleTagDO toArticleTagDO() {
        ArticleTagDO articleTagDO = new ArticleTagDO();
        BeanUtils.copyProperties(this, articleTagDO);
        // articleTagDO.setGmtCreated(this.getCreated());
        // articleTagDO.setGmtModified(this.getModified());
        return articleTagDO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
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

    @Override
    public String toString() {
        return "ArticleTag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", available=" + available +
                ", version=" + version +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
