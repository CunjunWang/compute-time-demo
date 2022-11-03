package com.cunjun.demo.utils;

import com.cunjun.demo.service.Route;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/3
 */
@Slf4j
public class PathDiffUtils {

    /**
     * 输出时间差异
     */
    public static void logTimeDiff(Route routeToNewCampus, Route routeToOldCampus) {
        String timeDiffText = null;
        if (routeToNewCampus.getDurationInMinutes() - routeToOldCampus.getDurationInMinutes() > 0) {
            timeDiffText = "增加";
        } else if (routeToNewCampus.getDurationInMinutes() - routeToOldCampus.getDurationInMinutes() < 0) {
            timeDiffText = "减少";
        }
        if (timeDiffText != null) {
            log.info("时间: {} {}分钟, 到新校区{}, 到老校区{}", timeDiffText, Math.abs(routeToNewCampus.getDurationInMinutes() - routeToOldCampus.getDurationInMinutes()),
                routeToNewCampus.getDuration(), routeToOldCampus.getDuration());
        } else {
            log.info("时间: 相同, 都是{}", routeToNewCampus.getDuration());
        }
    }

    /**
     * 输出通勤费差异
     */
    public static void logCostDiff(Route routeToNewCampus, Route routeToOldCampus) {
        String costDiffText = null;
        if (routeToNewCampus.getCostInYuan() - routeToOldCampus.getCostInYuan() > 0) {
            costDiffText = "增加";
        } else if (routeToNewCampus.getCostInYuan() - routeToOldCampus.getCostInYuan() < 0) {
            costDiffText = "减少";
        }
        if (costDiffText != null) {
            log.info("时间: {} {}元, 到新校区{}, 到老校区{}", costDiffText, Math.abs(routeToNewCampus.getCostInYuan() - routeToOldCampus.getCostInYuan()),
                routeToNewCampus.getCost(), routeToOldCampus.getCost());
        } else {
            log.info("花费: 相同, 都是{}", routeToNewCampus.getCost());
        }
    }

    /**
     * 输出总路程差异
     */
    public static void logTotalDistanceDiff(Route routeToNewCampus, Route routeToOldCampus) {
        String totalDistanceDiffText = null;
        if (routeToNewCampus.getTotalDistanceInKm() - routeToOldCampus.getTotalDistanceInKm() > 0) {
            totalDistanceDiffText = "增加";
        } else if (routeToNewCampus.getTotalDistanceInKm() - routeToOldCampus.getTotalDistanceInKm() < 0) {
            totalDistanceDiffText = "减少";
        }
        if (totalDistanceDiffText != null) {
            log.info("总距离: {} {}km, 到新校区{}, 到老校区{}", totalDistanceDiffText,
                Math.abs(new BigDecimal(String.valueOf(routeToNewCampus.getTotalDistanceInKm()))
                    .subtract(new BigDecimal(String.valueOf(routeToOldCampus.getTotalDistanceInKm())))
                    .doubleValue()),
                routeToNewCampus.getTotalDistanceInKm() + "km", routeToOldCampus.getTotalDistanceInKm() + "km");
        } else {
            log.info("总距离: 相同, 都是{}", routeToNewCampus.getTotalDistanceInKm() + "km");
        }
    }

    /**
     * 输出步行距离差异
     */
    public static void logWalkingDistanceDiff(Route routeToNewCampus, Route routeToOldCampus) {
        String walkingDistanceText = null;
        if (routeToNewCampus.getWalkingDistanceInKm() - routeToOldCampus.getWalkingDistanceInKm() > 0) {
            walkingDistanceText = "增加";
        } else if (routeToNewCampus.getWalkingDistanceInKm() - routeToOldCampus.getWalkingDistanceInKm() < 0) {
            walkingDistanceText = "减少";
        }
        if (walkingDistanceText != null) {
            log.info("步行距离: {} {}km, 到新校区{}, 到老校区{}", walkingDistanceText,
                Math.abs(new BigDecimal(String.valueOf(routeToNewCampus.getWalkingDistanceInKm()))
                    .subtract(new BigDecimal(String.valueOf(routeToOldCampus.getWalkingDistanceInKm())))
                    .doubleValue()),
                routeToNewCampus.getWalkingDistanceInKm() + "km", routeToOldCampus.getWalkingDistanceInKm() + "km");
        } else {
            log.info("步行距离: 相同, 都是{}", routeToNewCampus.getWalkingDistanceInKm() + "km");
        }
    }

}
