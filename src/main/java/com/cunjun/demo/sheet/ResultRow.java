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

    @ExcelProperty("Depart time (07:00 by default)")
    private String departTime;

    @ExcelProperty("Time difference (in minutes)")
    private String timeDiff;

    @ExcelProperty("Cost difference (in Yuan)")
    private String costDiff;

    @ExcelProperty("Total distance difference (in km)")
    private String totalDistanceDiff;

}
