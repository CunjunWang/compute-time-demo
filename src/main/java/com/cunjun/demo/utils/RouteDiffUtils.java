package com.cunjun.demo.utils;

import com.cunjun.demo.model.Route;
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

        String costDiffStr = computeAndLogCostDiff(routeToNewCampus, routeToOldCampus);
        routeDiff.setCostDiff(costDiffStr);

        String totalDistanceDiffStr = computeAndLogTotalDistanceDiff(routeToNewCampus, routeToOldCampus);
        routeDiff.setTotalDistanceDiff(totalDistanceDiffStr);

        return routeDiff;
    }

    /**
     * 计算时间差异
     */
    public static TimeDiff computeTimeDiff(Route routeToNewCampus, Route routeToOldCampus) {
        TimeDiff result = new TimeDiff();

        long timeDiffInMinutes = Math.abs(routeToNewCampus.getDurationInMinutes() - routeToOldCampus.getDurationInMinutes());
        result.setTimeDiffInMinutes(timeDiffInMinutes);
        String timeDiffText = null;
        if (timeDiffInMinutes > 0) {
            timeDiffText = "+";
        } else if (timeDiffInMinutes < 0) {
            timeDiffText = "-";
        }
        if (timeDiffText == null) {
            result.setTimeDiffDisplay("相同");
        } else {
            result.setTimeDiffDisplay(timeDiffText + timeDiffInMinutes + "分钟");
        }

        return result;
    }

    /**
     * 输出通勤费差异
     */
    public static String computeAndLogCostDiff(Route routeToNewCampus, Route routeToOldCampus) {
        String costDiffText = null;
        if (routeToNewCampus.getCostInYuan() - routeToOldCampus.getCostInYuan() > 0) {
            costDiffText = "+";
        } else if (routeToNewCampus.getCostInYuan() - routeToOldCampus.getCostInYuan() < 0) {
            costDiffText = "-";
        }
        String costDiff = Math.abs(routeToNewCampus.getCostInYuan() - routeToOldCampus.getCostInYuan()) + "元";
//        if (costDiffText != null) {
//            log.info("时间: {} {}, 到新校区{}, 到老校区{}", costDiffText, costDiff,
//                routeToNewCampus.getCost(), routeToOldCampus.getCost());
//        } else {
//            log.info("花费: 相同, 都是{}", routeToNewCampus.getCost());
//        }
        if (costDiffText == null) {
            return "相同";
        } else {
            return costDiffText + costDiff;
        }
    }

    /**
     * 输出总路程差异
     */
    public static String computeAndLogTotalDistanceDiff(Route routeToNewCampus, Route routeToOldCampus) {
        String totalDistanceDiffText = null;
        if (routeToNewCampus.getTotalDistanceInKm() - routeToOldCampus.getTotalDistanceInKm() > 0) {
            totalDistanceDiffText = "+";
        } else if (routeToNewCampus.getTotalDistanceInKm() - routeToOldCampus.getTotalDistanceInKm() < 0) {
            totalDistanceDiffText = "-";
        }
        String distanceDiff = Math.abs(new BigDecimal(String.valueOf(routeToNewCampus.getTotalDistanceInKm()))
            .subtract(new BigDecimal(String.valueOf(routeToOldCampus.getTotalDistanceInKm())))
            .doubleValue()) + "km";
//        if (totalDistanceDiffText != null) {
//            log.info("总距离: {} {}, 到新校区{}, 到老校区{}", totalDistanceDiffText,
//                distanceDiff, routeToNewCampus.getTotalDistanceInKm() + "km", routeToOldCampus.getTotalDistanceInKm() + "km");
//        } else {
//            log.info("总距离: 相同, 都是{}", routeToNewCampus.getTotalDistanceInKm() + "km");
//        }
        if (totalDistanceDiffText == null) {
            return "相同";
        } else {
            return totalDistanceDiffText + distanceDiff;
        }
    }

    /**
     * 输出步行距离差异
     */
    public static void logWalkingDistanceDiff(Route routeToNewCampus, Route routeToOldCampus) {
        String walkingDistanceText = null;
        if (routeToNewCampus.getWalkingDistanceInKm() - routeToOldCampus.getWalkingDistanceInKm() > 0) {
            walkingDistanceText = "+";
        } else if (routeToNewCampus.getWalkingDistanceInKm() - routeToOldCampus.getWalkingDistanceInKm() < 0) {
            walkingDistanceText = "-";
        }
//        if (walkingDistanceText != null) {
//            log.info("步行距离: {} {}km, 到新校区{}, 到老校区{}", walkingDistanceText,
//                Math.abs(new BigDecimal(String.valueOf(routeToNewCampus.getWalkingDistanceInKm()))
//                    .subtract(new BigDecimal(String.valueOf(routeToOldCampus.getWalkingDistanceInKm())))
//                    .doubleValue()),
//                routeToNewCampus.getWalkingDistanceInKm() + "km", routeToOldCampus.getWalkingDistanceInKm() + "km");
//        } else {
//            log.info("步行距离: 相同, 都是{}", routeToNewCampus.getWalkingDistanceInKm() + "km");
//        }
    }

}
