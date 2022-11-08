package com.cunjun.demo.model.diff;

import com.cunjun.demo.model.Route;
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
    private long timeDiffInMinutes;

    /**
     * 通勤时间差异
     */
    private String timeDiffDisplay;

    /**
     * 通勤费用差异
     */
    private double costDiffInYuan;

    /**
     * 展示的通勤费用差异
     */
    private String costDiffDisplay;

    /**
     * 总路程差异，米
     */
    private Long totalDistanceDiffInMeters;

    /**
     * 总路程差异，km
     */
    private double totalDistanceDiffInKm;

    /**
     * 总路程差异
     */
    private String totalDistanceDiffDisplay;

    /**
     * 到新校区的路线
     */
    private Route routeToNewCampus;

    /**
     * 到老校区的路线
     */
    private Route routeToOldCampus;

}
