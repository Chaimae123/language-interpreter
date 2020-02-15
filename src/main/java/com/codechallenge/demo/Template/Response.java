package com.codechallenge.demo.Template;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {

    private List<T> data;
    private ArrayList<String> series;

    public Response(List<T> data, ArrayList<String> series) {
        this.data = data;
        this.series = series;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public ArrayList<String> getSeries() {
        return series;
    }

    public void setSeries(ArrayList<String> series) {
        this.series = series;
    }
}
