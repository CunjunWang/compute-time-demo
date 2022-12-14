package com.cunjun.demo.model.diff;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/7
 */
@Getter
@Setter
public class TimeDiff {

    /**
     * 展示的路程时间差异
     */
    private String timeDiffDisplay;

    /**
     * 路程时间差异，分钟
     */
    private long timeDiffInMinutes;

}
