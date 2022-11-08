package com.cunjun.demo.sheet;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/8
 */
@Getter
@Setter
public class StatsRow {

    /**
     * 通勤时间差异
     */
    private long timeDiffInMinutes;

    /**
     * 通勤费用差异
     */
    private double costDiffInYuan;

    /**
     * 总路程差异，km
     */
    private double totalDistanceDiffInKm;

}
