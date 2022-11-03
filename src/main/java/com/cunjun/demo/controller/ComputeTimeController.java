package com.cunjun.demo.controller;

import com.alibaba.excel.EasyExcel;
import com.cunjun.demo.business.ResultData;
import com.cunjun.demo.model.RouteDiff;
import com.cunjun.demo.service.ComputeTimeService;
import com.cunjun.demo.sheet.ResultRow;
import com.cunjun.demo.sheet.TemplateRow;
import com.cunjun.demo.sheet.UploadDataListener;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/2
 */
@Slf4j
@RestController
@RequestMapping(value = "/compute/time")
@Api(value = "计算路程时间", tags = {"计算路程时间"})
public class ComputeTimeController {

    @Autowired
    private ComputeTimeService computeTimeService;

    /**
     * 文件上传
     * <p>1. 创建excel对应的实体对象
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link UploadDataListener}
     * <p>3. 直接读即可
     */
    @ResponseBody
    @RequestMapping(value = "/batch/upload", method = RequestMethod.POST)
    @ApiOperation(value = "上传文件批量计算", notes = "上传文件批量计算")
    public String uploadFileAndComputeBatch(MultipartFile file, HttpServletResponse response) throws IOException {
        UploadDataListener uploadDataListener = new UploadDataListener(computeTimeService);
        EasyExcel.read(file.getInputStream(), TemplateRow.class, uploadDataListener).sheet().doRead();
        List<ResultRow> resultRowList = uploadDataListener.getResultRowList();
        log.warn("共获取结果集: {}", resultRowList.size());

        String fileName = "result.xlsx";
        String encodeFileName = URLEncoder.encode(fileName, "utf8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename=" + encodeFileName);
        response.setHeader("filename", new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        ServletOutputStream outputStream = response.getOutputStream();
        EasyExcel.write(outputStream, ResultRow.class)
            .sheet("Template")
            .doWrite(resultRowList);
        outputStream.flush();

        return "success";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "计算路程时间", notes = "计算路程时间")
    public ResultData computeTimeSingle(String departTime, String originAddress) {
        RouteDiff routeDiff = computeTimeService.computeTime(departTime, originAddress);
        return ResultData.ok().objectResult(routeDiff);
    }

}
