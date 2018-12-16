package com.yd1994.alpacablog.blogservice.dto;

import com.yd1994.alpacablog.blogservice.entity.FileDO;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文件信息 数据传输对象
 * </p>
 *
 * @author yd
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class FileInfo implements Serializable {

    /**
     * ID
     */
    private Long id;
    /**
     * 文件名
     */
    private String name;
    /**
     * 文件类型
     * 0：图片
     */
    private Integer type;
    /**
     * 文件地址
     */
    private String path;
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

    public FileInfo() {
    }

    public FileInfo(FileDO fileDO) {

        if (fileDO != null) {
            BeanUtils.copyProperties(fileDO, this);
            // 时间转换
            this.created = fileDO.getGmtCreated();
            this.modified = fileDO.getGmtModified();
        }

    }

    public FileDO toFileDO() {
        FileDO fileDO = new FileDO();
        BeanUtils.copyProperties(this, fileDO);
        // fileDO.setGmtCreated(this.getCreated());
        // fileDO.setGmtModified(this.getModified());
        return fileDO;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
