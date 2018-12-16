package com.yd1994.alpacablog.blogservice.common.http;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>restful 集合用参数</p>
 *
 * @author yd
 */
@EqualsAndHashCode(callSuper = false)
@ToString
public class RestRequestParam implements Serializable {

    /**
     * 分页信息：页码
     */
    private Integer page = 1;
    /**
     * 分页信息：每页记录数
     */
    private Integer size = 10;
    /**
     * 以sortByAsc asc排序
     */
    private String[] sortByAscs;
    /**
     * sortByDesc desc排序
     */
    private String[] sortByDescs;
    /**
     * before的参数
     */
    private String beforeBy;
    /**
     * 在beforeBy定义参数的时间之前
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime before;
    /**
     * after的参数
     */
    private String afterBy;
    /**
     * 在afterBy定义参数的时间参数之后
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime after;
    /**
     * 预定义搜索内容
     */
    private String view;

    /**
     * 参数是否全部校验
     */
    private boolean isCheckAll = false;

    /**
     * 获取分页与排序信息
     * @param validParameter 排序可选参数 格式“id, title, content, summary, traffic”
     * @return
     */
    public Page getPageInfo(String validParameter) {
        if (!isCheckAll) {
            this.check(validParameter);
        }
        return new Page(this.page, this.size).setAsc(this.sortByAscs).setDesc(this.sortByDescs);
    }

    public void check(String validParameter) {
        checkPageAndSize();
        checkSortByAscAndSortByDesc(validParameter);
        checkBeforeAndAfter();
        this.isCheckAll = true;
    }

    public void checkPageAndSize() {
        if (page <= 0) {
            throw new IllegalArgumentException("参数有误：page不能小于1或者为空。");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("参数有误：size不能小于1或者为空。");
        }
    }

    public void checkSortByAscAndSortByDesc(String validParameter) {
        validParameter += "，created, modified";
        if (this.sortByAscs != null) {
            for (int i = 0; i < this.sortByAscs.length; i++) {
                if (!validParameter.contains(this.sortByAscs[i])) {
                    throw new IllegalArgumentException("参数有误：sortByAsc 可用参数：" + validParameter);
                }
                if ("created".equals(this.sortByAscs[i])) {
                    this.sortByAscs[i] = "gmt_created";
                } else if ("modified".equals(this.sortByAscs[i])) {
                    this.sortByAscs[i] = "gmt_modified";
                }

            }
        }
        if (this.sortByDescs != null) {
            for (int i = 0; i < this.sortByDescs.length; i++) {
                if (!validParameter.contains(this.sortByDescs[i])) {
                    throw new IllegalArgumentException("参数有误：sortByAsc 可用参数：" + validParameter);
                }
                if ("created".equals(this.sortByDescs[i])) {
                    this.sortByDescs[i] = "gmt_created";
                } else if ("modified".equals(this.sortByDescs[i])) {
                    this.sortByDescs[i] = "gmt_modified";
                }
            }
        }
    }

    public void checkBeforeAndAfter() {

        String beforeBy = this.beforeBy;
        if (StringUtils.isEmpty(beforeBy) ^ this.before == null) {
            throw new IllegalArgumentException("参数有误：before 为空。 before、beforeBy 都为空或都存在。");
        }
        if (!StringUtils.isEmpty(beforeBy) && !StringUtils.isEmpty(this.before)) {
            if ("created".equals(beforeBy)) {
                setBeforeBy("gmt_created");
            } else if ("modified".equals(beforeBy)) {
                setBeforeBy("gmt_modified");
            } else {
                throw new IllegalArgumentException("参数有误： getAfterBy");
            }
        }
        String afterBy = this.afterBy;
        if (StringUtils.isEmpty(afterBy) ^ this.after == null) {
            throw new IllegalArgumentException("参数有误：after 为空。 after、afterBy 都为空或都存在。");
        }
        if (!StringUtils.isEmpty(afterBy) && !StringUtils.isEmpty(this.after)) {
            if ("created".equals(afterBy)) {
                setAfterBy("gmt_created");
            } else if ("modified".equals(afterBy)) {
                setAfterBy("gmt_modified");
            } else {
                throw new IllegalArgumentException("参数有误： getAfterBy");
            }
        }
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String[] getSortByAscs() {
        return sortByAscs;
    }

    public void setSortByAscs(String[] sortByAscs) {
        this.sortByAscs = sortByAscs;
    }

    public String[] getSortByDescs() {
        return sortByDescs;
    }

    public void setSortByDescs(String[] sortByDescs) {
        this.sortByDescs = sortByDescs;
    }

    public String getBeforeBy() {
        return beforeBy;
    }

    public void setBeforeBy(String beforeBy) {
        this.beforeBy = beforeBy;
    }

    public LocalDateTime getBefore() {
        return before;
    }

    public void setBefore(LocalDateTime before) {
        this.before = before;
    }

    public String getAfterBy() {
        return afterBy;
    }

    public void setAfterBy(String afterBy) {
        this.afterBy = afterBy;
    }

    public LocalDateTime getAfter() {
        return after;
    }

    public void setAfter(LocalDateTime after) {
        this.after = after;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
