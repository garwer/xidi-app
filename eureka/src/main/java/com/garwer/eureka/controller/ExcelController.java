package com.garwer.eureka.controller;

import com.cloud.common.excel.ExcelUtil;
import com.cloud.common.excel.entity.ExportInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Garwer
 * @Date: 2019/5/16 10:01 PM
 * @Version 1.0
 */

@Controller
@RequestMapping("/test")
public class ExcelController {
    @GetMapping("/writeExcel")
    @ResponseBody
    public Object writeExcel(HttpServletResponse response) throws IOException {
        List<ExportInfo> list = new ArrayList<>();
        String fileName = "一个 Excel 文件";
        String sheetName = "第一个 sheet";
        for (int i = 0; i < 5; i++) {
            ExportInfo info = new ExportInfo();
            info.setName("linjw" + i);
            info.setAge(i + "");
            info.setEmail(UUID.randomUUID().toString());
            list.add(info);
        }
        ExcelUtil.writeExcel(response, list, fileName, sheetName, new ExportInfo());
        return null;
    }
}
