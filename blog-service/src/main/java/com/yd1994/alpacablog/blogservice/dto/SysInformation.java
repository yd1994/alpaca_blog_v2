package com.yd1994.alpacablog.blogservice.dto;

import com.yd1994.alpacablog.blogservice.entity.SysInformationDO;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统信息 数据传输对象
 * </p>
 *
 * @author yd
 */

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class SysInformation implements Serializable {

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

    public SysInformation() {
    }

    public SysInformation(SysInformationDO sysInformationDO) {

        if (sysInformationDO != null) {

            BeanUtils.copyProperties(sysInformationDO, this);
            // 时间转换
            this.created = sysInformationDO.getGmtCreated();
            this.modified = sysInformationDO.getGmtModified();
        }
    }

    public SysInformationDO toSysInformationDO() {
        SysInformationDO sysInformationDO = new SysInformationDO();
        BeanUtils.copyProperties(this, sysInformationDO);
        // sysInformationDO.setGmtCreated(this.getCreated());
        // sysInformationDO.setGmtModified(this.getModified());
        return sysInformationDO;
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
