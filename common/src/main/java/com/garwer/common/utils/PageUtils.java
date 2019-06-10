package com.garwer.common.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @Author: Garwer
 * @Date: 2019/6/7 12:11 AM
 * @Version 1.0
 * 分页工具
 */

@Slf4j
public class PageUtils {
    public static final String START = "start";
    public static final String SIZE = "size";

    /**
     *
     * @param params
     * 分页参数必填
     */
    public static void pageParamCheck(JSONObject params) {
        boolean flag = (Objects.isNull(params) || !params.containsKey(START) || !params.containsKey(SIZE));
        if (flag) {
            throw new UnsupportedOperationException("分页参数校验失败,请检查!" + START + "," + SIZE + "等配置:" + params);
        }

        Integer start = params.getInteger(START);
        Integer size = params.getInteger(SIZE);
        //限制start、size
        if (start < 0 || size < 0 || size > 100) {
            throw new UnsupportedOperationException("分页参数校验失败!" + start + size);
        }
    }
}
