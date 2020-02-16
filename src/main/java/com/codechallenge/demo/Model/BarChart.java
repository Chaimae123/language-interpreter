package com.codechallenge.demo.Model;

public class BarChart {

    private Integer id;
    private String series;
    private String group;
    private Integer value;

    public BarChart(Integer id, String series, String group, Integer value) {
        this.id = id;
        this.series = series;
        this.group = group;
        this.value = value;
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
