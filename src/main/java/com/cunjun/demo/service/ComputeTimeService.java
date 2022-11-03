package com.cunjun.demo.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cunjun.demo.model.Poi;
import com.cunjun.demo.model.Route;
import com.cunjun.demo.model.RouteDiff;
import com.cunjun.demo.utils.PathDiffUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public RouteDiff computeTime(String departTime, String originAddress) {
        String oldCampusDestAddress = "上海市浦东新区世纪大道1555号";
        String newCampusDestAddress = "上海市浦东新区高青西路杨思西路口(东方体育中心地铁站出入口步行490米)";

        // 1. 新校区、老校区地址转成POI，只要做一次
        Poi oldCampusPoi = new Poi("121.534185,31.225888");
//            convertAddressToPoi(oldCampusDestAddress);
//        log.info("老校区地址: {}, poi: {}", oldCampusDestAddress, oldCampusPoi.toString());
        Poi newCampusPoi = new Poi("121.481562,31.148379");
//            convertAddressToPoi(newCampusDestAddress);
//        log.info("新校区地址: {}, poi: {}", newCampusDestAddress, newCampusPoi.toString());

        // 2. 出发地地址转成POI
        Poi originPoi = convertAddressToPoi(originAddress);
//        log.info("出发地地址: {}, poi: {}", originAddress, originPoi.toString());

        // 3. 出发地POI + 新校区 POI 调用路径规划API
        Route routeToNewCampus = computeRoute(departTime, originPoi, newCampusPoi);

        // 4. 出发地POI + 老校区 POI 调用路径规划API
        Route routeToOldCampus = computeRoute(departTime, originPoi, oldCampusPoi);

        // 5. 路径规划结果做diff
        RouteDiff routeDiff = PathDiffUtils.computeRouteDiff(routeToOldCampus, routeToNewCampus);
        routeDiff.setRouteToNewCampus(routeToNewCampus);
        routeDiff.setRouteToOldCampus(routeToOldCampus);

        // 6. 输出diff结果
//        log.info("[{}]从[{}]到新校区[{}]比到老校区[{}]:", departTime, originAddress, newCampusDestAddress, oldCampusDestAddress);
//        PathDiffUtils.computeAndLogTimeDiff(routeToNewCampus, routeToOldCampus);
//        PathDiffUtils.computeAndLogCostDiff(routeToNewCampus, routeToOldCampus);
//        PathDiffUtils.computeAndLogTotalDistanceDiff(routeToNewCampus, routeToOldCampus);
//        PathDiffUtils.logWalkingDistanceDiff(routeToNewCampus, routeToOldCampus);

        return routeDiff;
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
        return new Poi(lat, lon);
    }

    private Route computeRoute(String departTime, Poi originPoi, Poi destPoi) {
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
