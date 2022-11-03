package com.cunjun.demo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cunjun.demo.model.Poi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/2
 */
@Slf4j
@Service
public class ComputeTimeService {

    @Autowired
    private RestTemplate restTemplate;

    public void computeTime(String departTime, String originAddress) {
        String oldCampusDestAddress = "上海市浦东新区世纪大道1555号";
        String newCampusDestAddress = "上海市浦东新区高青西路杨思西路口(东方体育中心地铁站出入口步行490米)";

        // 1. 新校区、老校区地址转成POI，只要做一次
        Poi oldCampusPoi = convertAddressToPoi(oldCampusDestAddress);
        log.info("老校区地址: {}, poi: {}", oldCampusDestAddress, oldCampusPoi.toString());
        Poi newCampusPoi = convertAddressToPoi(newCampusDestAddress);
        log.info("新校区地址: {}, poi: {}", newCampusDestAddress, newCampusPoi.toString());

        // 2. 出发地地址转成POI
        Poi originPoi = convertAddressToPoi(originAddress);
        log.info("出发地地址: {}, poi: {}", originAddress, originPoi.toString());

        // 3. 出发地POI + 新校区 POI 调用路径规划API
        Route routeToNewCampus = computePath(departTime, originPoi, newCampusPoi);
//        log.info("{}", JSON.toJSONString(routeToNewCampus));

        // 4. 出发地POI + 老校区 POI 调用路径规划API
        Route routeToOldCampus = computePath(departTime, originPoi, oldCampusPoi);
//        log.info("{}", JSON.toJSONString(routeToOldCampus));

        // 5. 路径规划结果做diff
        log.info("[{}]从[{}]到新校区[{}]比到老校区[{}]:", departTime, originAddress, newCampusDestAddress, oldCampusDestAddress);

        String timeDiffText = null;
        if (routeToNewCampus.getDurationInMinutes() - routeToOldCampus.getDurationInMinutes() > 0) {
            timeDiffText = "增加";
        } else if (routeToNewCampus.getDurationInMinutes() - routeToOldCampus.getDurationInMinutes() < 0) {
            timeDiffText = "减少";
        }
        if (timeDiffText != null) {
            log.info("时间: {} {}分钟, 到新校区{}, 到老校区{}", timeDiffText, Math.abs(routeToNewCampus.getDurationInMinutes() - routeToOldCampus.getDurationInMinutes()),
                routeToNewCampus.getDuration(), routeToOldCampus.getDuration());
        } else {
            log.info("时间: 相同, 都是{}", routeToNewCampus.getDuration());
        }

        String costDiffText = null;
        if (routeToNewCampus.getCostInYuan() - routeToOldCampus.getCostInYuan() > 0) {
            costDiffText = "增加";
        } else if (routeToNewCampus.getCostInYuan() - routeToOldCampus.getCostInYuan() < 0) {
            costDiffText = "减少";
        }
        if (costDiffText != null) {
            log.info("时间: {} {}元, 到新校区{}, 到老校区{}", costDiffText, Math.abs(routeToNewCampus.getCostInYuan() - routeToOldCampus.getCostInYuan()),
                routeToNewCampus.getCost(), routeToOldCampus.getCost());
        } else {
            log.info("花费: 相同, 都是{}", routeToNewCampus.getCost());
        }

        String totalDistanceDiffText = null;
        if (routeToNewCampus.getTotalDistanceInKm() - routeToOldCampus.getTotalDistanceInKm() > 0) {
            totalDistanceDiffText = "增加";
        } else if (routeToNewCampus.getTotalDistanceInKm() - routeToOldCampus.getTotalDistanceInKm() < 0) {
            totalDistanceDiffText = "减少";
        }
        if (totalDistanceDiffText != null) {
            log.info("总距离: {} {}km, 到新校区{}, 到老校区{}", totalDistanceDiffText,
                Math.abs(new BigDecimal(String.valueOf(routeToNewCampus.getTotalDistanceInKm()))
                    .subtract(new BigDecimal(String.valueOf(routeToOldCampus.getTotalDistanceInKm())))
                    .doubleValue()),
                routeToNewCampus.getTotalDistanceInKm() + "km", routeToOldCampus.getTotalDistanceInKm() + "km");
        } else {
            log.info("总距离: 相同, 都是{}", routeToNewCampus.getTotalDistanceInKm() + "km");
        }

        String walkingDistanceText = null;
        if (routeToNewCampus.getWalkingDistanceInKm() - routeToOldCampus.getWalkingDistanceInKm() > 0) {
            walkingDistanceText = "增加";
        } else if (routeToNewCampus.getWalkingDistanceInKm() - routeToOldCampus.getWalkingDistanceInKm() < 0) {
            walkingDistanceText = "减少";
        }
        if (walkingDistanceText != null) {
            log.info("步行距离: {} {}km, 到新校区{}, 到老校区{}", walkingDistanceText,
                Math.abs(new BigDecimal(String.valueOf(routeToNewCampus.getWalkingDistanceInKm()))
                    .subtract(new BigDecimal(String.valueOf(routeToOldCampus.getWalkingDistanceInKm())))
                    .doubleValue()),
                routeToNewCampus.getWalkingDistanceInKm() + "km", routeToOldCampus.getWalkingDistanceInKm() + "km");
        } else {
            log.info("步行距离: 相同, 都是{}", routeToNewCampus.getWalkingDistanceInKm() + "km");
        }

        // 6. 输出diff结果
    }

    private Poi convertAddressToPoi(String address) {
        String keywordSearchPoiUrl = "https://restapi.amap.com/v3/place/text?keywords={address}&city=上海&citylimit=true&key=729bdc43eebc3ba79e7f6664f7133f98";

        String originUrl = keywordSearchPoiUrl.replace("{address}", address);
//        log.warn("外调高德地图获取POI request: {}", originUrl);
        ResponseEntity<JSONObject> response = restTemplate.exchange(originUrl, HttpMethod.GET, null, JSONObject.class);
//        log.warn("外调高德地图获取POI response: {}", JSON.toJSONString(response));
        JSONArray poiList = response.getBody().getJSONArray("pois");
        if (CollectionUtils.isEmpty(poiList)) {
            throw new IllegalArgumentException("未查询到地址信息");
        }
        LinkedHashMap poi = (LinkedHashMap) poiList.get(0);
        String location = (String) poi.get("location");
        if (StringUtils.isEmpty(location)) {
            throw new IllegalArgumentException("未查询到地址信息");
        }
        String[] split = location.split(",");
        String lon = split[0];
        String lat = split[1];
        return Poi.builder().lat(lat).lon(lon).build();
    }

    private Route computePath(String departTime, Poi originPoi, Poi destPoi) {
//        "https://restapi.amap.com/v3/direction/transit/integrated?origin=116.481499,39.990475&destination=116.465063,39.999538&city=010&output=xml&key=<用户的key>"
        String pathProgrammingUrl = "https://restapi.amap.com/v3/direction/transit/integrated?origin=" + originPoi.toString() +
            "&destination=" + destPoi.toString() + "&city=021&time=" + departTime + "&key=729bdc43eebc3ba79e7f6664f7133f98";

        ResponseEntity<JSONObject> response = restTemplate.exchange(pathProgrammingUrl, HttpMethod.GET, null, JSONObject.class);
        LinkedHashMap routeMap = (LinkedHashMap) response.getBody().get("route");
        String distanceInMeter = (String) routeMap.get("distance");
        String taxiCost = (String) routeMap.get("taxi_cost");

        List<LinkedHashMap> routesMapList = (List<LinkedHashMap>) routeMap.get("transits");
        LinkedHashMap bestRouteMap = routesMapList.get(0);

        Route finalRouteResult = new Route();

        String cost = (String) bestRouteMap.get("cost");
        finalRouteResult.setCost("￥" + cost);
        finalRouteResult.setCostInYuan(Double.parseDouble(cost));

        String durationInSeconds = (String) bestRouteMap.get("duration");
        finalRouteResult.setDurationInSeconds(Long.parseLong(durationInSeconds));
        finalRouteResult.setDurationInMinutes(durationInSeconds);
        finalRouteResult.setDuration(durationInSeconds);

        String walkingDistanceInMeters = (String) bestRouteMap.get("walking_distance");
        finalRouteResult.setWalkingDistance(walkingDistanceInMeters);
        finalRouteResult.setWalkingDistanceInKm(walkingDistanceInMeters);

        String totalDistanceInMeters = (String) bestRouteMap.get("distance");
        finalRouteResult.setTotalDistance(totalDistanceInMeters);
        finalRouteResult.setTotalDistanceInKm(totalDistanceInMeters);

        List<LinkedHashMap> routeSegments = (List<LinkedHashMap>) bestRouteMap.get("segments");

        return finalRouteResult;
    }

}
