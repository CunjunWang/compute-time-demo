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
    private String costDiff;

    /**
     * 总路程差异
     */
    private String totalDistanceDiff;

    // 3. 出发地POI + 新校区 POI 调用路径规划API
    private Route routeToNewCampus;

    // 4. 出发地POI + 老校区 POI 调用路径规划API
    private Route routeToOldCampus;

}
