package com.cunjun.demo.sheet;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/4
 */
@Getter
@Setter
public class ExceptionRow {

    @ExcelProperty("Employee name")
    private String employeeName;

    @ExcelProperty("Address")
    private String originAddress;

    @ExcelProperty("Fail Reason")
    private String failReason;

}
