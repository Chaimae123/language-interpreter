package com.codechallenge.demo.Service;

import com.codechallenge.demo.Model.BarChart;
import com.codechallenge.demo.Model.InputData;
import com.codechallenge.demo.Model.PlotBox;
import com.codechallenge.demo.Model.ScatterChart;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public interface PythonParserInterface {
    String pythonCodeParser(String code, HttpSession session);
    List<ScatterChart> scatterChartDataGenerator(ArrayList<String> series, ArrayList<Integer> values);
    List<PlotBox> plotBoxDataGenerator(ArrayList<String> series, ArrayList<Integer> values);
    List<BarChart> barChartDataGenerator(ArrayList<String> series, ArrayList<Integer> values);
    InputData getSeriesFromInput(String data);
}
