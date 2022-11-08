package com.cunjun.demo.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/8
 */
@Getter
@Setter
public class Stats {

    // 最大值
    long maxTimeDiffInMinutes;
    double maxCostDiffInYuan;
    double maxTotalDistanceDiffInKm;

    // 最小值
    long minTimeDiffInMinutes;
    double minCostDiffInYuan;
    double minTotalDistanceDiffInKm;

    // 均值
    double meanTimeDiffInMinutes;
    double meanCostDiffInYuan;
    double meanDistanceDiffInKm;

    // 中位数
    double medianTimeDiffInMinutes;
    double medianCostDiffInYuan;
    double medianDistanceDiffInKm;

    // 标准差
    double stdTimeDiffInMinutes;
    double stdCostDiffInYuan;
    double stdDistanceDiffInKm;

}
