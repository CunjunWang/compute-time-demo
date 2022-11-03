package com.cunjun.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/2
 */
@Getter
@Setter
public class Route {

    /**
     * 价格
     */
    private String cost;

    /**
     * 价格，元
     */
    private Double costInYuan;

    /**
     * 消耗时间
     */
    private Long durationInSeconds;

    /**
     * 消耗时间，分钟
     */
    private Long durationInMinutes;

    /**
     * 消耗时间，分钟
     */
    private String duration;

    /**
     * 步行距离，千米
     */
    private String walkingDistance;

    /**
     * 步行距离，千米
     */
    private Double walkingDistanceInKm;

    /**
     * 总距离，千米
     */
    private String totalDistance;

    /**
     * 总距离，千米
     */
    private Double totalDistanceInKm;

    public void setDurationInMinutes(String durationInSeconds) {
        BigDecimal durationInSecondBigDecimal = new BigDecimal(durationInSeconds);
        BigDecimal durationInMinutesBigDecimal = durationInSecondBigDecimal.divide(new BigDecimal(60), RoundingMode.HALF_UP);
        this.durationInMinutes = durationInMinutesBigDecimal.longValue();
    }

    public void setDuration(String durationInSeconds) {
        BigDecimal durationInSecondBigDecimal = new BigDecimal(durationInSeconds);
        BigDecimal durationInMinutesBigDecimal = durationInSecondBigDecimal.divide(new BigDecimal(60), RoundingMode.HALF_UP);
        this.duration = "约" + durationInMinutesBigDecimal.longValue() + "分钟";
    }

    public void setWalkingDistance(String walkingDistanceInMeters) {
        BigDecimal walkingDistanceInMetersBigDecimal = new BigDecimal(walkingDistanceInMeters);
        BigDecimal walkingDistanceInKmBigDecimal = walkingDistanceInMetersBigDecimal.divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP);
        this.walkingDistance = "约" + walkingDistanceInKmBigDecimal.longValue() + "km";
    }

    public void setWalkingDistanceInKm(String walkingDistanceInMeters) {
        BigDecimal walkingDistanceInMetersBigDecimal = new BigDecimal(walkingDistanceInMeters);
        BigDecimal walkingDistanceInKmBigDecimal = walkingDistanceInMetersBigDecimal.divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP);
        this.walkingDistanceInKm = walkingDistanceInKmBigDecimal.doubleValue();
    }

    public void setTotalDistanceInKm(String totalDistanceInMeters) {
        BigDecimal totalDistanceInMetersBigDecimal = new BigDecimal(totalDistanceInMeters);
        BigDecimal totalDistanceInKmBigDecimal = totalDistanceInMetersBigDecimal.divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP);
        this.totalDistanceInKm = totalDistanceInKmBigDecimal.doubleValue();
    }

}
