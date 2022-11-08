package com.cunjun.demo.sheet;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.cunjun.demo.model.Stats;
import com.cunjun.demo.model.diff.RouteDiff;
import com.cunjun.demo.service.ComputeTimeService;
import com.cunjun.demo.utils.DataRowUtils;
import com.cunjun.demo.utils.StatsUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

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
    private final List<StatsRow> statsRowList = Lists.newArrayList();

    @Getter
    private final List<ExceptionRow> exceptionRowList = Lists.newArrayList();

    @Getter
    private Stats stats;

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
            ResultRow resultRow = DataRowUtils.convertToResultRow(defaultDepartTime, templateRow, routeDiff, statsRowList);
            resultRowList.add(resultRow);
        } catch (Exception e) {
            ExceptionRow exceptionRow = DataRowUtils.convertToExceptionRow(templateRow, e);
            exceptionRowList.add(exceptionRow);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成, 成功{}条, 异常{}条", resultRowList.size(), exceptionRowList.size());
        if (CollectionUtils.isNotEmpty(this.statsRowList)) {
            if (this.stats == null) {
                this.stats = new Stats();
            }

            List<Long> timeDiffInMinutesList = this.statsRowList.stream().map(StatsRow::getTimeDiffInMinutes).collect(Collectors.toList());
            List<Double> costDiffInYuanList = this.statsRowList.stream().map(StatsRow::getCostDiffInYuan).collect(Collectors.toList());
            List<Double> totalDistanceDiffInKmList = this.statsRowList.stream().map(StatsRow::getTotalDistanceDiffInKm).collect(Collectors.toList());
            // 最大值
            long maxTimeDiffInMinutes = StatsUtils.maxOfLongList(timeDiffInMinutesList);
            double maxCostDiffInYuan = StatsUtils.maxOfDoubleList(costDiffInYuanList);
            double maxTotalDistanceDiffInKm = StatsUtils.maxOfDoubleList(totalDistanceDiffInKmList);
            this.stats.setMaxTimeDiffInMinutes(maxTimeDiffInMinutes);
            this.stats.setMaxCostDiffInYuan(maxCostDiffInYuan);
            this.stats.setMaxTotalDistanceDiffInKm(maxTotalDistanceDiffInKm);

            // 最小值
            long minTimeDiffInMinutes = StatsUtils.minOfLongList(timeDiffInMinutesList);
            double minCostDiffInYuan = StatsUtils.minOfDoubleList(costDiffInYuanList);
            double minTotalDistanceDiffInKm = StatsUtils.minOfDoubleList(totalDistanceDiffInKmList);
            this.stats.setMinTimeDiffInMinutes(minTimeDiffInMinutes);
            this.stats.setMinCostDiffInYuan(minCostDiffInYuan);
            this.stats.setMinTotalDistanceDiffInKm(minTotalDistanceDiffInKm);

            // 均值
            double meanTimeDiffInMinutes = StatsUtils.meanOfLongList(timeDiffInMinutesList);
            double meanCostDiffInYuan = StatsUtils.meanOfDoubleList(costDiffInYuanList);
            double meanDistanceDiffInKm = StatsUtils.meanOfDoubleList(totalDistanceDiffInKmList);
            this.stats.setMeanTimeDiffInMinutes(meanTimeDiffInMinutes);
            this.stats.setMeanCostDiffInYuan(meanCostDiffInYuan);
            this.stats.setMeanDistanceDiffInKm(meanDistanceDiffInKm);

            // 中位数
            double medianTimeDiffInMinutes = StatsUtils.medianOfLongList(timeDiffInMinutesList);
            double medianCostDiffInYuan = StatsUtils.medianOfDoubleList(costDiffInYuanList);
            double medianDistanceDiffInKm = StatsUtils.medianOfDoubleList(totalDistanceDiffInKmList);
            this.stats.setMedianTimeDiffInMinutes(medianTimeDiffInMinutes);
            this.stats.setMedianCostDiffInYuan(medianCostDiffInYuan);
            this.stats.setMedianDistanceDiffInKm(medianDistanceDiffInKm);

            // 标准差
            double stdTimeDiffInMinutes = StatsUtils.standardDeviationOfLongList(timeDiffInMinutesList);
            double stdCostDiffInYuan = StatsUtils.standardDeviationOfDoubleList(costDiffInYuanList);
            double stdDistanceDiffInKm = StatsUtils.standardDeviationOfDoubleList(totalDistanceDiffInKmList);
            this.stats.setStdTimeDiffInMinutes(stdTimeDiffInMinutes);
            this.stats.setStdCostDiffInYuan(stdCostDiffInYuan);
            this.stats.setStdDistanceDiffInKm(stdDistanceDiffInKm);
        }
    }

}
