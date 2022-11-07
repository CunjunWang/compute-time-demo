package com.cunjun.demo.utils;

import com.alibaba.excel.util.ListUtils;
import com.cunjun.demo.model.Route;
import com.cunjun.demo.model.RouteDiff;
import com.cunjun.demo.sheet.ExceptionRow;
import com.cunjun.demo.sheet.ResultRow;
import com.cunjun.demo.sheet.TemplateRow;

import java.util.List;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/7
 */
public class DataRowUtils {

    /**
     * 构造实例数据行
     */
    public static List<TemplateRow> convertToTemplateRow() {
        List<TemplateRow> list = ListUtils.newArrayList();
        TemplateRow templateRow = new TemplateRow();
        templateRow.setEmployeeName("张三");
        templateRow.setOriginAddress("上海市黄浦区人民大道200号");
        list.add(templateRow);
        return list;
    }

    /**
     * 构造计算数据结果
     */
    public static ResultRow convertToResultRow(String defaultDepartTime, TemplateRow templateRow, RouteDiff routeDiff) {
        Route routeToNewCampus = routeDiff.getRouteToNewCampus();
        Route routeToOldCampus = routeDiff.getRouteToOldCampus();

        ResultRow resultRow = new ResultRow();
        resultRow.setEmployeeName(templateRow.getEmployeeName());
        resultRow.setOriginAddress(templateRow.getOriginAddress());
        resultRow.setDepartTime(templateRow.getDepartTime() == null ? defaultDepartTime : templateRow.getDepartTime());
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

    /**
     * 构造异常数据结果
     */
    public static ExceptionRow convertToExceptionRow(TemplateRow templateRow, Exception e) {
        ExceptionRow exceptionRow = new ExceptionRow();
        exceptionRow.setEmployeeName(templateRow.getEmployeeName());
        exceptionRow.setOriginAddress(templateRow.getOriginAddress());
        exceptionRow.setFailReason(e.getMessage());
        return exceptionRow;
    }

}
