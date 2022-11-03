package com.cunjun.demo.sheet;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import com.cunjun.demo.model.Route;
import com.cunjun.demo.model.RouteDiff;
import com.cunjun.demo.service.ComputeTimeService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/3
 */
@Slf4j
public class UploadDataListener implements ReadListener<TemplateRow> {

    private ComputeTimeService computeTimeService;

    @Getter
    private List<ResultRow> resultRowList = Lists.newArrayList();

    public UploadDataListener(ComputeTimeService computeTimeService) {
        this.computeTimeService = computeTimeService;
    }

    @Override
    public void invoke(TemplateRow templateRow, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSON.toJSONString(templateRow));
        String departTime = templateRow.getDepartTime();
        if (StringUtils.isEmpty(departTime)) {
            departTime = "08:00";
        }
        String originAddress = templateRow.getOriginAddress();
        if (StringUtils.isEmpty(originAddress)) {
            log.warn("地址为空");
            return;
        }
        RouteDiff routeDiff = computeTimeService.computeTime(departTime, originAddress);

        ResultRow resultRow = convertToResult(templateRow, routeDiff);
        resultRowList.add(resultRow);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成, 共处理{}条", resultRowList.size());
    }

    private ResultRow convertToResult(TemplateRow templateRow, RouteDiff routeDiff) {
        Route routeToNewCampus = routeDiff.getRouteToNewCampus();
        Route routeToOldCampus = routeDiff.getRouteToOldCampus();

        ResultRow resultRow = new ResultRow();

        resultRow.setEmployeeName(templateRow.getEmployeeName());
        resultRow.setOriginAddress(templateRow.getOriginAddress());
        resultRow.setDepartTime(templateRow.getDepartTime() == null ? "08:00" : templateRow.getDepartTime());
        resultRow.setTimeToOldCampus(routeToOldCampus.getDuration());
        resultRow.setTimeToNewCampus(routeToNewCampus.getDuration());
        resultRow.setTimeDiff(routeDiff.getTimeDiff());
        resultRow.setEstimatedArrivalTime(computeTime(resultRow.getDepartTime(), routeToNewCampus.getDurationInMinutes()));
        resultRow.setCostToOldCampus(routeToOldCampus.getCost());
        resultRow.setCostToNewCampus(routeToNewCampus.getCost());
        resultRow.setCostDiff(routeDiff.getCostDiff());
        resultRow.setDistanceToOldCampus(routeToOldCampus.getTotalDistanceInKm() + "km");
        resultRow.setDistanceToNewCampus(routeToNewCampus.getTotalDistanceInKm() + "km");
        resultRow.setTotalDistanceDiff(routeDiff.getTotalDistanceDiff());

        return resultRow;
    }

    private String computeTime(String departTime, Long duration) {
        SimpleDateFormat minuteFormat = new SimpleDateFormat("HH:mm");
        try {
            Date date = minuteFormat.parse(departTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MINUTE, duration.intValue());
            date = calendar.getTime();
            return minuteFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


}
