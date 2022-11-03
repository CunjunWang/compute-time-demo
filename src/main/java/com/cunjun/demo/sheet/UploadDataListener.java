package com.cunjun.demo.sheet;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import com.cunjun.demo.model.RouteDiff;
import com.cunjun.demo.service.ComputeTimeService;
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
            departTime = "07:00";
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
        ResultRow resultRow = new ResultRow();
        resultRow.setEmployeeName(templateRow.getEmployeeName());
        resultRow.setOriginAddress(templateRow.getOriginAddress());
        resultRow.setDepartTime(templateRow.getDepartTime() == null ? "07:00" : templateRow.getDepartTime());
        resultRow.setTimeDiff(routeDiff.getTimeDiff());
        resultRow.setCostDiff(routeDiff.getCostDiff());
        resultRow.setTotalDistanceDiff(routeDiff.getTotalDistanceDiff());
        return resultRow;
    }

}
