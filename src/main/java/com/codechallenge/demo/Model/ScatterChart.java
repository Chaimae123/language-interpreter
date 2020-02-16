package com.codechallenge.demo.Model;

public class ScatterChart {

    private Integer id;
    private String series;
    private String group;
    private Integer x;
    private Integer y;
    private Integer z;

    public ScatterChart(Integer id, String series, String group, Integer x, Integer y, Integer z) {
        this.id = id;
        this.series = series;
        this.group = group;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }
}
