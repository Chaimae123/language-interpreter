package com.codechallenge.demo.Template;

import java.util.List;

public class Response<T> {

    private List<T> data;
    private List<String> series;

    public Response(List<T> data, List<String> series) {
        this.data = data;
        this.series = series;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<String> getSeries() {
        return series;
    }

    public void setSeries(List<String> series) {
        this.series = series;
    }
}
