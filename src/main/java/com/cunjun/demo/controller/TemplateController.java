package com.cunjun.demo.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.cunjun.demo.sheet.TemplateRow;
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
import java.util.List;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/3
 */
@RestController
@RequestMapping(value = "")
@Api(value = "文件模板", tags = {"文件模板"})
public class TemplateController {

    /**
     * 文件下载（失败了会返回一个有部分数据的Excel）
     * <p>
     * 1. 创建excel对应的实体对象
     * <p>
     * 2. 设置返回的 参数
     * <p>
     * 3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大
     */
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
            .doWrite(templateData());
        outputStream.flush();
    }

    private List<TemplateRow> templateData() {
        List<TemplateRow> list = ListUtils.newArrayList();
        TemplateRow templateRow = new TemplateRow();
        templateRow.setEmployeeName("张三");
        templateRow.setOriginAddress("上海市黄浦区人民大道200号");
        list.add(templateRow);
        return list;
    }

}
