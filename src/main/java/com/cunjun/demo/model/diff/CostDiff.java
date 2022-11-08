package com.cunjun.demo.model.diff;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/8
 */
@Getter
@Setter
public class CostDiff {

    /**
     * 展示的路程时间差异
     */
    private String costDiffDisplay;

    /**
     * 路程时间差异，分钟
     */
    private double costDiffInYuan;

}
