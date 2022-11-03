package com.cunjun.demo.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/3
 */
@Getter
@Setter
public class RouteDiff {

    /**
     * 通勤时间差异
     */
    private String timeDiff;

    /**
     * 通勤费用差异
     */
    private String costDiff;

    /**
     * 总路程差异
     */
    private String totalDistanceDiff;

}
