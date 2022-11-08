package com.cunjun.demo.utils;

import com.cunjun.demo.model.Route;
import com.cunjun.demo.model.diff.CostDiff;
import com.cunjun.demo.model.diff.DistanceDiff;
import com.cunjun.demo.model.diff.RouteDiff;
import com.cunjun.demo.model.diff.TimeDiff;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/3
 */
@Slf4j
public class RouteDiffUtils {

    public static RouteDiff computeRouteDiff(Route routeToOldCampus, Route routeToNewCampus) {
        RouteDiff routeDiff = new RouteDiff();

        routeDiff.setRouteToNewCampus(routeToNewCampus);
        routeDiff.setRouteToOldCampus(routeToOldCampus);

        TimeDiff timeDiff = computeTimeDiff(routeToNewCampus, routeToOldCampus);
        routeDiff.setTimeDiffDisplay(timeDiff.getTimeDiffDisplay());
        routeDiff.setTimeDiffInMinutes(timeDiff.getTimeDiffInMinutes());

        CostDiff costDiff = computeCostDiff(routeToNewCampus, routeToOldCampus);
        routeDiff.setCostDiffDisplay(costDiff.getCostDiffDisplay());
        routeDiff.setCostDiffInYuan(costDiff.getCostDiffInYuan());

        DistanceDiff totalDistanceDiff = computeTotalDistanceDiff(routeToNewCampus, routeToOldCampus);
        routeDiff.setTotalDistanceDiffDisplay(totalDistanceDiff.getDistanceDiffDisplay());
        routeDiff.setTotalDistanceDiffInMeters(totalDistanceDiff.getDistanceDiffInMeters());
        routeDiff.setTotalDistanceDiffInKm(totalDistanceDiff.getDistanceDiffInKm());

        return routeDiff;
    }

    /**
     * 计算时间差异
     */
    public static TimeDiff computeTimeDiff(Route routeToNewCampus, Route routeToOldCampus) {
        TimeDiff result = new TimeDiff();

        long rawTimeDiff = routeToNewCampus.getDurationInMinutes() - routeToOldCampus.getDurationInMinutes();
        result.setTimeDiffInMinutes(rawTimeDiff);
        String timeDiffText = null;
        if (rawTimeDiff > 0) {
            timeDiffText = "+";
        } else if (rawTimeDiff < 0) {
            timeDiffText = "-";
        }
        if (timeDiffText == null) {
            result.setTimeDiffDisplay("相同");
        } else {
            result.setTimeDiffDisplay(timeDiffText + Math.abs(rawTimeDiff) + "分钟");
        }

        return result;
    }

    /**
     * 计算通勤费用差异
     */
    public static CostDiff computeCostDiff(Route routeToNewCampus, Route routeToOldCampus) {
        CostDiff result = new CostDiff();

        double rawCostDiffInYuan = routeToNewCampus.getCostInYuan() - routeToOldCampus.getCostInYuan();
        result.setCostDiffInYuan(rawCostDiffInYuan);
        String costDiffText = null;
        if (rawCostDiffInYuan > 0) {
            costDiffText = "+";
        } else if (rawCostDiffInYuan < 0) {
            costDiffText = "-";
        }
        if (costDiffText == null) {
            result.setCostDiffDisplay("相同");
        } else {
            result.setCostDiffDisplay(costDiffText + "￥" + Math.abs(rawCostDiffInYuan) + "元");
        }

        return result;
    }

    /**
     * 计算总路程差异
     */
    public static DistanceDiff computeTotalDistanceDiff(Route routeToNewCampus, Route routeToOldCampus) {
        DistanceDiff result = new DistanceDiff();

        double rawDistanceDiff = new BigDecimal(String.valueOf(routeToNewCampus.getTotalDistanceInKm()))
            .subtract(new BigDecimal(String.valueOf(routeToOldCampus.getTotalDistanceInKm())))
            .doubleValue();
        result.setDistanceDiffInKm(rawDistanceDiff);
        result.setDistanceDiffInMeters(routeToNewCampus.getTotalDistanceInMeters() - routeToOldCampus.getTotalDistanceInMeters());
        String totalDistanceDiffText = null;
        if (rawDistanceDiff > 0) {
            totalDistanceDiffText = "+";
        } else if (rawDistanceDiff < 0) {
            totalDistanceDiffText = "-";
        }
        if (totalDistanceDiffText == null) {
            result.setDistanceDiffDisplay("相同");
        } else {
            result.setDistanceDiffDisplay(totalDistanceDiffText + Math.abs(rawDistanceDiff) + "km");
        }

        return result;
    }

}
