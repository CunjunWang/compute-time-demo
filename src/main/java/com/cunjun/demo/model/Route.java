package com.cunjun.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

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
     * 展示的价格
     */
    private String costDisplay;

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
    private String durationDisplay;

    /**
     * 步行距离, 米
     */
    private Long walkingDistanceInMeters;

    /**
     * 步行距离，千米
     */
    private Double walkingDistanceInKm;

    /**
     * 展示步行距离
     */
    private String walkingDistanceDisplay;

    /**
     * 总距离, 米
     */
    private Long totalDistanceInMeters;

    /**
     * 总距离，千米
     */
    private Double totalDistanceInKm;

    /**
     * 展示总距离
     */
    private String totalDistanceDisplay;

    public void setCostInYuan(String cost) {
        if (StringUtils.isEmpty(cost) || !NumberUtils.isNumber(cost)) {
            throw new IllegalArgumentException("路线花费数据非法: " + cost);
        }
        this.costInYuan = Double.parseDouble(cost);
    }

    public void setCostDisplay(String cost) {
        this.costDisplay = "￥" + cost + "元";
    }

    public void setDurationInMinutes(String durationInSeconds) {
        BigDecimal durationInSecondBigDecimal = new BigDecimal(durationInSeconds);
        BigDecimal durationInMinutesBigDecimal = durationInSecondBigDecimal.divide(new BigDecimal(60), RoundingMode.HALF_UP);
        this.durationInMinutes = durationInMinutesBigDecimal.longValue();
    }

    public void setDurationDisplay(String durationInSeconds) {
        BigDecimal durationInSecondBigDecimal = new BigDecimal(durationInSeconds);
        BigDecimal durationInMinutesBigDecimal = durationInSecondBigDecimal.divide(new BigDecimal(60), RoundingMode.HALF_UP);
        this.durationDisplay = "约" + durationInMinutesBigDecimal.longValue() + "分钟";
    }

    public void setWalkingDistanceInMeters(String walkingDistanceInMeters) {
        if (StringUtils.isEmpty(walkingDistanceInMeters) || !NumberUtils.isNumber(walkingDistanceInMeters)) {
            throw new IllegalArgumentException("步行距离数据非法: " + cost);
        }
        this.walkingDistanceInMeters = Long.parseLong(walkingDistanceInMeters);
    }

    public void setWalkingDistanceInKm(String walkingDistanceInMeters) {
        BigDecimal walkingDistanceInMetersBigDecimal = new BigDecimal(walkingDistanceInMeters);
        BigDecimal walkingDistanceInKmBigDecimal = walkingDistanceInMetersBigDecimal.divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP);
        this.walkingDistanceInKm = walkingDistanceInKmBigDecimal.doubleValue();
    }

    public void setWalkingDistanceDisplay(String walkingDistanceInMeters) {
        BigDecimal walkingDistanceInMetersBigDecimal = new BigDecimal(walkingDistanceInMeters);
        BigDecimal walkingDistanceInKmBigDecimal = walkingDistanceInMetersBigDecimal.divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP);
        this.walkingDistanceDisplay = "约" + walkingDistanceInKmBigDecimal.longValue() + "km";
    }

    public void setTotalDistanceInMeters(String totalDistanceInMeters) {
        if (StringUtils.isEmpty(totalDistanceInMeters) || !NumberUtils.isNumber(totalDistanceInMeters)) {
            throw new IllegalArgumentException("距离数据非法: " + cost);
        }
        this.totalDistanceInMeters = Long.parseLong(totalDistanceInMeters);
    }

    public void setTotalDistanceInKm(String totalDistanceInMeters) {
        BigDecimal totalDistanceInMetersBigDecimal = new BigDecimal(totalDistanceInMeters);
        BigDecimal totalDistanceInKmBigDecimal = totalDistanceInMetersBigDecimal.divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP);
        this.totalDistanceInKm = totalDistanceInKmBigDecimal.doubleValue();
    }

    public void setTotalDistanceDisplay(String totalDistanceInMeters) {
        if (this.totalDistanceInKm == null) {
            setTotalDistanceInKm(totalDistanceInMeters);
        }
        this.totalDistanceDisplay = "约" + this.totalDistanceInKm + "km";
    }

}
