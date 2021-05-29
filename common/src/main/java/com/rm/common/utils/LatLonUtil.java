package com.rm.common.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class LatLonUtil {
    private static final double EARTH_RADIUS = 6378137;
    private static final double RAD = Math.PI / 180.0;

    /**
     * @param raidus 单位米
     *               return minLat,minLng,maxLat,maxLng
     */
    public static double[] getAround(double lat, double lng, int raidus) {

        Double degree = (24901 * 1609) / 360.0;

        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidus;
        Double minLat = lat - radiusLat;
        Double maxLat = lat + radiusLat;

        Double mpdLng = degree * Math.cos(lat * RAD);
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidus;
        Double minLng = lng - radiusLng;
        Double maxLng = lng + radiusLng;
        return new double[]{minLat, minLng, maxLat, maxLng};
    }

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
     *
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = lat1 * RAD;
        double radLat2 = lat2 * RAD;
        double a = radLat1 - radLat2;
        double b = (lng1 - lng2) * RAD;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    /**
     * 相对方向
     *
     * @param lngLat1
     * @param lngLat2
     * @return
     */
    public static String relativeDirection(String lngLat1, String lngLat2) {
        String[] arr1 = lngLat1.split(",");
        String[] arr2 = lngLat2.split(",");
        String result = "";
        Double lng1 = Double.valueOf(arr1[0]);
        Double lng2 = Double.valueOf(arr2[0]);
        Double lat1 = Double.valueOf(arr1[1]);
        Double lat2 = Double.valueOf(arr2[1]);

        if (Math.abs(lng1 - lng2) > 0.0001) {
            if (lng1 < lng2) {
                result += "西";
            } else {
                result += "东";
            }
        }
        if (Math.abs(lat1 - lat2) > 0.0001) {
            if (lat1 < lat2) {
                result += "南";
            } else {
                result += "北";
            }
        }
        return result;
    }

    /**
     * 根据坐标组计算里程
     *
     * @param locationList
     * @return
     */
    public static double getTotalMileageByLocations(List<Location> locationList) {
        double totalMileage = 0;
        for (int i = 0; locationList != null && i < locationList.size() - 1; i++) {
            Location location = locationList.get(i);
            Location location1 = locationList.get(i + 1);
            totalMileage += getDistance(location1.getLongti_WG(), location1.getLati_WG(), location.getLongti_WG(), location.getLati_WG());
        }
        return totalMileage;
    }


    public static class Location implements Serializable {

        Date date;
        Double longti_WG;
        Double lati_WG;
        Double ver;
        Double longti_BD;
        Double lati_BD;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Double getLongti_WG() {
            return longti_WG;
        }

        public void setLongti_WG(Double longti_WG) {
            this.longti_WG = longti_WG;
        }

        public Double getLati_WG() {
            return lati_WG;
        }

        public void setLati_WG(Double lati_WG) {
            this.lati_WG = lati_WG;
        }

        public Double getVer() {
            return ver;
        }

        public void setVer(Double ver) {
            this.ver = ver;
        }

        public Double getLongti_BD() {
            return longti_BD;
        }

        public void setLongti_BD(Double longti_BD) {
            this.longti_BD = longti_BD;
        }

        public Double getLati_BD() {
            return lati_BD;
        }

        public void setLati_BD(Double lati_BD) {
            this.lati_BD = lati_BD;
        }
    }

}
