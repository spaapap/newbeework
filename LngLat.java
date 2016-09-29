package com.jetmap.read;

/**
 * Created by Admin on 2016/8/30.
 */
public class LngLat {
    public double lng;
    public double lat;
    private double weights;
    private Object object;

    private int lineno;

    public LngLat(double x, double y) {
        this.lng = x;
        this.lat = y;
    }

    public LngLat(double x, double y, Object object) {
        this.lng = x;
        this.lat = y;
        this.object = object;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getWeights() {

        return weights;
    }

    public void setWeights(double weights) {
        this.weights = weights;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public int getLineno() {
        return lineno;
    }

    public void setLineno(int lineno) {
        this.lineno = lineno;
    }
}
