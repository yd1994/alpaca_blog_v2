package com.yd1994.alpacablog.blogservice.dto;

import com.yd1994.alpacablog.blogservice.entity.CategoryDO;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 分类信息 数据传输对象
 * </p>
 *
 * @author yd
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class Category implements Serializable {

    private Long id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类描述
     */
    private String description;
    /**
     * 是否可用
     */
    private Boolean enabled;
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

    public Category() {
    }

    /**
     * 通过CategoryDO创建
     * @param categoryDO
     */
    public Category(CategoryDO categoryDO) {
        if (categoryDO != null) {
            BeanUtils.copyProperties(categoryDO, this);
            // 时间转换
            this.created = categoryDO.getGmtCreated();
            this.modified = categoryDO.getGmtModified();
        }
    }

    /**
     * 转换为CategoryDO
     * @return
     */
    public CategoryDO toCategoryDO() {
        CategoryDO categoryDO = new CategoryDO();
        BeanUtils.copyProperties(this, categoryDO);
        // categoryDO.setGmtCreated(this.getCreated());
        // categoryDO.setGmtModified(this.getModified());
        return categoryDO;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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
}
