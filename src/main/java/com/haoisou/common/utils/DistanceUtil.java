package com.haoisou.common.utils;

import java.math.BigDecimal;

public class DistanceUtil {
    // 地球赤道半径
    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据当前经纬度和固定地点经纬度判断是否超过了指定的半径
     *
     * @param lat
     * @param lng
     * @param latCurrent
     * @param lngCurrent
     * @param radius     指定的半径
     * @return
     */
    public static boolean effectiveDistance(String lat, String lng, String latCurrent, String lngCurrent, int radius) {

        String latDif = new BigDecimal(latCurrent).subtract(new BigDecimal(lat)).pow(2).toString();
        String lngDif = new BigDecimal(lngCurrent).subtract(new BigDecimal(lng)).pow(2).toString();
        double distance = getDistance(Double.parseDouble(latCurrent), Double.parseDouble(lngCurrent), Double.parseDouble(lat), Double.parseDouble(lng));
        if (radius > distance) {
            return true;
        }
        return false;
    }

    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }
}
