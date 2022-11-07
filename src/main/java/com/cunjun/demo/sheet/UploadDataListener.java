package com.cunjun.demo.sheet;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.cunjun.demo.model.Route;
import com.cunjun.demo.model.RouteDiff;
import com.cunjun.demo.service.ComputeTimeService;
import com.cunjun.demo.utils.TimeUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/3
 */
@Slf4j
public class UploadDataListener implements ReadListener<TemplateRow> {

    private final ComputeTimeService computeTimeService;

    @Getter
    private final List<ResultRow> resultRowList = Lists.newArrayList();

    @Getter
    private final List<ExceptionRow> exceptionRowList = Lists.newArrayList();

    @Value("${com.cunjun.demo.default-depart-time}")
    private String defaultDepartTime;

    public UploadDataListener(ComputeTimeService computeTimeService) {
        this.computeTimeService = computeTimeService;
    }

    @Override
    public void invoke(TemplateRow templateRow, AnalysisContext analysisContext) {
        String departTime = templateRow.getDepartTime();
        if (StringUtils.isEmpty(departTime)) {
            departTime = defaultDepartTime;
        }
        String departCity = templateRow.getCity();
        if (StringUtils.isEmpty(departCity)) {
            departCity = "上海市";
        }
        try {
            String originAddress = templateRow.getOriginAddress();
            if (StringUtils.isEmpty(originAddress)) {
                log.error("地址信息为空");
                throw new IllegalArgumentException("员工出发地址为空, 请检查地址");
            }
            RouteDiff routeDiff = computeTimeService.computeTime(departTime, departCity, originAddress);
            ResultRow resultRow = convertToResultRow(templateRow, routeDiff);
            resultRowList.add(resultRow);
        } catch (Exception e) {
            log.error("计算路程时间失败, {}", e.getMessage());
            ExceptionRow exceptionRow = convertToExceptionRow(templateRow, e);
            exceptionRowList.add(exceptionRow);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成, 成功{}条, 异常{}条", resultRowList.size(), exceptionRowList.size());
    }

    private ResultRow convertToResultRow(TemplateRow templateRow, RouteDiff routeDiff) {
        Route routeToNewCampus = routeDiff.getRouteToNewCampus();
        Route routeToOldCampus = routeDiff.getRouteToOldCampus();

        ResultRow resultRow = new ResultRow();
        resultRow.setEmployeeName(templateRow.getEmployeeName());
        resultRow.setOriginAddress(templateRow.getOriginAddress());
        resultRow.setDepartTime(templateRow.getDepartTime() == null ? "08:00" : templateRow.getDepartTime());
        resultRow.setTimeToOldCampus(routeToOldCampus.getDuration());
        resultRow.setTimeToNewCampus(routeToNewCampus.getDuration());
        resultRow.setTimeDiff(routeDiff.getTimeDiff());
        resultRow.setEstimatedArrivalTime(TimeUtils.computeEstimatedArrivalTime(resultRow.getDepartTime(), routeToNewCampus.getDurationInMinutes()));
        resultRow.setCostToOldCampus(routeToOldCampus.getCost());
        resultRow.setCostToNewCampus(routeToNewCampus.getCost());
        resultRow.setCostDiff(routeDiff.getCostDiff());
        resultRow.setDistanceToOldCampus(routeToOldCampus.getTotalDistanceInKm() + "km");
        resultRow.setDistanceToNewCampus(routeToNewCampus.getTotalDistanceInKm() + "km");
        resultRow.setTotalDistanceDiff(routeDiff.getTotalDistanceDiff());
        return resultRow;
    }

    private ExceptionRow convertToExceptionRow(TemplateRow templateRow, Exception e) {
        ExceptionRow exceptionRow = new ExceptionRow();
        exceptionRow.setEmployeeName(templateRow.getEmployeeName());
        exceptionRow.setOriginAddress(templateRow.getOriginAddress());
        exceptionRow.setFailReason(e.getMessage());
        return exceptionRow;
    }

}
