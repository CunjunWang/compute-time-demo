package com.cunjun.demo.utils;

import com.alibaba.excel.util.ListUtils;
import com.cunjun.demo.constant.ComputeTimeConstants;
import com.cunjun.demo.model.Route;
import com.cunjun.demo.model.Stats;
import com.cunjun.demo.model.diff.RouteDiff;
import com.cunjun.demo.sheet.*;
import com.google.common.collect.Lists;

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
        templateRow.setEmployeeName(ComputeTimeConstants.TEMPLATE_EMPLOYEE_NAME);
        templateRow.setOriginAddress(ComputeTimeConstants.TEMPLATE_DEPART_ADDRESS);
        list.add(templateRow);
        return list;
    }

    /**
     * 构造计算数据结果
     */
    public static ResultRow convertToResultRow(String defaultDepartTime, TemplateRow templateRow, RouteDiff routeDiff, List<StatsRow> statsRowList) {
        Route routeToNewCampus = routeDiff.getRouteToNewCampus();
        Route routeToOldCampus = routeDiff.getRouteToOldCampus();

        ResultRow resultRow = new ResultRow();
        resultRow.setEmployeeName(templateRow.getEmployeeName());
        resultRow.setOriginAddress(templateRow.getOriginAddress());
        resultRow.setDepartTime(templateRow.getDepartTime() == null ? defaultDepartTime : templateRow.getDepartTime());
        resultRow.setTimeToOldCampus(routeToOldCampus.getDurationDisplay());
        resultRow.setTimeToNewCampus(routeToNewCampus.getDurationDisplay());
        resultRow.setTimeDiffDisplay(routeDiff.getTimeDiffDisplay());
        resultRow.setEstimatedArrivalTime(TimeUtils.computeEstimatedArrivalTime(resultRow.getDepartTime(), routeToNewCampus.getDurationInMinutes()));
        resultRow.setCostToOldCampus(routeToOldCampus.getCost());
        resultRow.setCostToNewCampus(routeToNewCampus.getCost());
        resultRow.setCostDiffDisplay(routeDiff.getCostDiffDisplay());
        resultRow.setDistanceToOldCampus(routeToOldCampus.getTotalDistanceInKm() + "km");
        resultRow.setDistanceToNewCampus(routeToNewCampus.getTotalDistanceInKm() + "km");
        resultRow.setTotalDistanceDiffDisplay(routeDiff.getTotalDistanceDiffDisplay());

        StatsRow statsRow = new StatsRow();
        statsRow.setTimeDiffInMinutes(routeDiff.getTimeDiffInMinutes());
        statsRow.setCostDiffInYuan(routeDiff.getCostDiffInYuan());
        statsRow.setTotalDistanceDiffInKm(routeDiff.getTotalDistanceDiffInKm());
        statsRowList.add(statsRow);

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

    /**
     * 构造统计数据结果
     */
    public static List<StatsDisplayRow> convertToStatsDisplayRow(Stats stats) {
        List<StatsDisplayRow> result = Lists.newArrayList();
        StatsDisplayRow timeDiffStatsRow = StatsDisplayRow.builder()
            .dataName("Time Difference")
            .max(stats.getMaxTimeDiffInMinutes())
            .min(stats.getMinTimeDiffInMinutes())
            .mean(stats.getMeanTimeDiffInMinutes())
            .median(stats.getMedianTimeDiffInMinutes())
            .standardDeviation(stats.getStdTimeDiffInMinutes())
            .build();
        result.add(timeDiffStatsRow);
        StatsDisplayRow costDiffStatsRow = StatsDisplayRow.builder()
            .dataName("Cost Difference")
            .max(stats.getMaxCostDiffInYuan())
            .min(stats.getMinCostDiffInYuan())
            .mean(stats.getMeanCostDiffInYuan())
            .median(stats.getMedianCostDiffInYuan())
            .standardDeviation(stats.getStdCostDiffInYuan())
            .build();
        result.add(costDiffStatsRow);
        StatsDisplayRow distanceDiffStatsRow = StatsDisplayRow.builder()
            .dataName("Distance Difference")
            .max(stats.getMaxTotalDistanceDiffInKm())
            .min(stats.getMinTotalDistanceDiffInKm())
            .mean(stats.getMeanDistanceDiffInKm())
            .median(stats.getMedianDistanceDiffInKm())
            .standardDeviation(stats.getStdDistanceDiffInKm())
            .build();
        result.add(distanceDiffStatsRow);
        return result;
    }

}
