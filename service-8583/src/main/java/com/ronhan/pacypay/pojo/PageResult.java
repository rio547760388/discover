package com.ronhan.pacypay.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/11/17
 **/
@Data
@AllArgsConstructor
@ToString
public class PageResult<T> {
    private List<T> list;

    private long totalRecords;

    private long totalPages;

    private long pageSize;

    private long pageNum;
}
