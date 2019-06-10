package com.garwer.common.excel;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.common.collect.Lists;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Garwer
 * @Date: 2019/5/16 9:58 PM
 * @Version 1.0
 * https://github.com/HowieYuan/easyexcel-encapsulation
 */
public class ExcelUtil {
    public static void writeExcel(HttpServletResponse response, List<? extends BaseRowModel> list,
                                  String fileName, String sheetName, BaseRowModel object) {
        ExcelWriter writer = new ExcelWriter(getOutputStream(fileName, response), ExcelTypeEnum.XLSX);

        List<List<String>> paramList = Lists.newArrayList();
        Sheet sheet = new Sheet(1, 0, object.getClass());
        sheet.setSheetName(sheetName);

        Sheet sheet1 = new Sheet(2, 0, object.getClass());
        sheet1.setSheetName(sheetName + "zai lai yige");


        Sheet sheet2 = new Sheet(1, 0);


        List<List<String>> headList = Lists.newArrayList();
        headList.add(Arrays.asList("a标题"));
        headList.add(Arrays.asList("b标题"));

        sheet2.setHead(headList);
       // writer.write(list, sheet);
        //writer.write(list, sheet1);
        List<String> lista = Lists.newArrayList();
        lista.add("aa");
        lista.add("bb");

        paramList.add(lista);
        writer.write0(paramList, sheet2);

        writer.finish();
    }

    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) {
        //创建本地文件
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String filePath = fileName + ".xlsx";
        File dbfFile = new File(filePath);
        try {
            if (!dbfFile.exists() || dbfFile.isDirectory()) {
                dbfFile.createNewFile();
            }
            fileName = new String(filePath.getBytes(), "ISO-8859-1");
            response.addHeader("Content-Disposition", "filename=" + fileName);
            return response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("创建文件失败！");
        }
    }
}
