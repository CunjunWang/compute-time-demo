package com.cunjun.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/2
 */
@Getter
@Setter
@Builder
public class Poi {

    private String lat;

    private String lon;

    @Override
    public String toString() {
        return lon + "," + lat;
    }

}
