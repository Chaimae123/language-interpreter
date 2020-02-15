package com.codechallenge.demo.Model;

import java.util.List;

public class PlotBox {

    private Integer id;
    private String series;
    private String group;
    private Integer low;
    private Integer high;
    private Integer z;
    private Integer q1;
    private Integer q2;
    private Integer q3;
    private List<Integer> outliers;

    public PlotBox(Integer id, String series, String group, Integer low, Integer high, Integer z, Integer q1, Integer q2, Integer q3) {
        this.id = id;
        this.series = series;
        this.group = group;
        this.low = low;
        this.high = high;
        this.z = z;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
    }

    public PlotBox(List<Integer> outliers) {
        this.outliers = outliers;
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

    public Integer getLow() {
        return low;
    }

    public void setLow(Integer low) {
        this.low = low;
    }

    public Integer getHigh() {
        return high;
    }

    public void setHigh(Integer high) {
        this.high = high;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }

    public Integer getQ1() {
        return q1;
    }

    public void setQ1(Integer q1) {
        this.q1 = q1;
    }

    public Integer getQ2() {
        return q2;
    }

    public void setQ2(Integer q2) {
        this.q2 = q2;
    }

    public Integer getQ3() {
        return q3;
    }

    public void setQ3(Integer q3) {
        this.q3 = q3;
    }

    public List<Integer> getOutliers() {
        return outliers;
    }

    public void setOutliers(List<Integer> outliers) {
        this.outliers = outliers;
    }
}
