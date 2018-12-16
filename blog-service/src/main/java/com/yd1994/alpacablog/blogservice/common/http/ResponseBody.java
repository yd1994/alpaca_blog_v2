package com.yd1994.alpacablog.blogservice.common.http;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 集合用 响应body
 *
 * @author yd
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class ResponseBody<T> implements Serializable {

    private List<T> data;
    private Long total;

    public ResponseBody(List<T> data, Long total) {
        this.data = data;
        this.total = total;
    }
}
