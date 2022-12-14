package com.cunjun.demo.controller;

import com.alibaba.excel.EasyExcel;
import com.cunjun.demo.sheet.TemplateRow;
import com.cunjun.demo.utils.DataRowUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/3
 */
@RestController
@RequestMapping(value = "")
@Api(value = "文件模板", tags = {"文件模板"})
public class TemplateController {

    @RequestMapping(value = "/template/download", method = RequestMethod.GET)
    @ApiOperation(value = "下载模板", notes = "下载模板", produces = "application/octet-stream")
    public void download(HttpServletResponse response) throws IOException {
        String fileName = "template.xlsx";
        String encodeFileName = URLEncoder.encode(fileName, "utf8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename=" + encodeFileName);
        response.setHeader("filename", new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        ServletOutputStream outputStream = response.getOutputStream();
        EasyExcel.write(outputStream, TemplateRow.class)
            .sheet("Template")
            .doWrite(DataRowUtils.convertToTemplateRow());
        outputStream.flush();
    }

}
