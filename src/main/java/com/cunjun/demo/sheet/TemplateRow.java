package com.cunjun.demo.sheet;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/3
 */
@Getter
@Setter
@EqualsAndHashCode
public class TemplateRow {

    @ExcelProperty("Employee name")
    private String employeeName;

    @ExcelProperty("Address")
    private String originAddress;

    @ExcelProperty("Depart time (08:00 by default)")
    private String departTime;

    @ExcelProperty("City (上海 by default)")
    private String city;

}
