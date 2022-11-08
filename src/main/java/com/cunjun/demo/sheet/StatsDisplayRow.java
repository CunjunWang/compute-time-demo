package com.cunjun.demo.sheet;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/8
 */
@Getter
@Setter
@Builder
public class StatsDisplayRow {

    @ExcelProperty("Data Name")
    private String dataName;

    @ExcelProperty("Max")
    private double max;

    @ExcelProperty("Min")
    private double min;

    @ExcelProperty("Mean")
    private double mean;

    @ExcelProperty("Median")
    private double median;

    @ExcelProperty("Standard Deviation")
    private double standardDeviation;

}
