package com.cunjun.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/2
 */
@Getter
@Setter
public class Poi {

    private String lat;

    private String lon;

    @Override
    public String toString() {
        return lon + "," + lat;
    }

    public Poi(String lat, String lon) {
        if (StringUtils.isEmpty(lat) || StringUtils.isEmpty(lon)) {
            throw new IllegalArgumentException("poi信息为空");
        }
        this.lat = lat;
        this.lon = lon;
    }

    public Poi(String poiString) {
        if (StringUtils.isEmpty(poiString)) {
            throw new IllegalArgumentException("poi信息为空");
        }
        String[] split = poiString.split(",");
        this.lon = split[0];
        this.lat = split[1];
    }

}
