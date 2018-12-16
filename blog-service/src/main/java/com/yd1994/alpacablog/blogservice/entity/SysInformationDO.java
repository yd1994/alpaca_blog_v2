package com.yd1994.alpacablog.blogservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统信息
 * </p>
 *
 * @author yd
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
@TableName(value = "alpaca_blog_sys_information")
public class SysInformationDO implements Serializable {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 信息名
     */
    private String name;
    /**
     * 信息值
     */
    private String value;
    /**
     * 文件类型
     */
    private String type;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
