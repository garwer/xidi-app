package com.cloud.common.excel;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cloud.common.excel.entity.ExportInfo;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Author: Garwer
 * @Date: 2019/5/16 9:47 PM
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        List<String> list = Lists.newArrayList();
        String fileName = "一个 Excel 文件";
        String sheetName = "第一个 sheet";
        //ExcelUtil.writeExcel(response, list, fileName, sheetName, new ExportInfo());
    }
}
