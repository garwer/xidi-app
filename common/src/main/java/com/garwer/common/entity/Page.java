package com.garwer.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Garwer
 * @Date: 2019/6/6 11:01 PM
 * @Version 1.0
 * 分页对象 可以给数据库/es返回数据使用
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {
    private int total;
    private List<T> data;
}
