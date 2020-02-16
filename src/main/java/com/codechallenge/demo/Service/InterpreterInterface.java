package com.codechallenge.demo.Service;

import com.codechallenge.demo.Model.BarChart;
import com.codechallenge.demo.Model.InputData;
import com.codechallenge.demo.Model.PlotBox;
import com.codechallenge.demo.Model.ScatterChart;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface InterpreterInterface {
    String pythonCodeParser(String code, HttpSession session);
    List<ScatterChart> scatterChartDataGenerator(List<String> series, List<Integer> values);
    List<PlotBox> plotBoxDataGenerator(List<String> series, List<Integer> values);
    List<BarChart> barChartDataGenerator(List<String> series, List<Integer> values);
    InputData getSeriesFromInput(String data);
}
