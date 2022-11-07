package com.cunjun.demo.sheet;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.cunjun.demo.model.RouteDiff;
import com.cunjun.demo.service.ComputeTimeService;
import com.cunjun.demo.utils.DataRowUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/3
 */
@Slf4j
public class UploadDataListener implements ReadListener<TemplateRow> {

    private final String defaultDepartTime;

    private final String defaultDepartCity;

    private final ComputeTimeService computeTimeService;

    @Getter
    private final List<ResultRow> resultRowList = Lists.newArrayList();

    @Getter
    private final List<ExceptionRow> exceptionRowList = Lists.newArrayList();

    public UploadDataListener(ComputeTimeService computeTimeService, String defaultDepartTime, String defaultDepartCity) {
        this.defaultDepartCity = defaultDepartCity;
        this.defaultDepartTime = defaultDepartTime;
        this.computeTimeService = computeTimeService;
    }

    @Override
    public void invoke(TemplateRow templateRow, AnalysisContext analysisContext) {
        String departTime = StringUtils.isEmpty(templateRow.getDepartTime()) ? defaultDepartTime : templateRow.getDepartTime();
        String departCity = StringUtils.isEmpty(templateRow.getCity()) ? defaultDepartCity : templateRow.getCity();
        try {
            String originAddress = templateRow.getOriginAddress();
            if (StringUtils.isEmpty(originAddress)) {
                String errorMessage = String.format("员工[%s]出发地址为空, 请检查地址", templateRow.getEmployeeName());
                throw new IllegalArgumentException(errorMessage);
            }
            RouteDiff routeDiff = computeTimeService.computeTime(departTime, departCity, originAddress);
            ResultRow resultRow = DataRowUtils.convertToResultRow(defaultDepartTime, templateRow, routeDiff);
            resultRowList.add(resultRow);
        } catch (Exception e) {
            ExceptionRow exceptionRow = DataRowUtils.convertToExceptionRow(templateRow, e);
            exceptionRowList.add(exceptionRow);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成, 成功{}条, 异常{}条", resultRowList.size(), exceptionRowList.size());
    }

}
