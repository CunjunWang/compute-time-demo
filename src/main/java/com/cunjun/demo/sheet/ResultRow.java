package com.cunjun.demo.sheet;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/3
 */
@Getter
@Setter
public class ResultRow {

    @ExcelProperty("Employee name")
    private String employeeName;

    @ExcelProperty("Address")
    private String originAddress;

    @ExcelProperty("Depart time")
    private String departTime;

    @ExcelProperty("Time to old campus (in minutes)")
    private String timeToOldCampus;

    @ExcelProperty("Time to new campus (in minutes)")
    private String timeToNewCampus;

    @ExcelProperty("Time difference (in minutes)")
    private String timeDiff;

    @ExcelProperty("Estimated arrival time")
    private String estimatedArrivalTime;

    @ExcelProperty("Cost to old campus (in Yuan)")
    private String costToOldCampus;

    @ExcelProperty("Cost to new campus (in Yuan)")
    private String costToNewCampus;

    @ExcelProperty("Cost difference (in Yuan)")
    private String costDiff;

    @ExcelProperty("Distance to old campus (in km)")
    private String distanceToOldCampus;

    @ExcelProperty("Distance to new campus (in km)")
    private String distanceToNewCampus;

    @ExcelProperty("Total distance difference (in km)")
    private String totalDistanceDiff;

}
