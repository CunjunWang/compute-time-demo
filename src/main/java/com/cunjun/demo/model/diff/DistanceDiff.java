package com.cunjun.demo.model.diff;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/8
 */
@Getter
@Setter
public class DistanceDiff {

    /**
     * 展示的距离差异
     */
    private String distanceDiffDisplay;

    /**
     * 距离差异，千米
     */
    private double distanceDiffInKm;

    /**
     * 距离差异，米
     */
    private long distanceDiffInMeters;

}
