package com.codechallenge.demo.Controller;

import com.codechallenge.demo.Model.BarChart;
import com.codechallenge.demo.Model.InputData;
import com.codechallenge.demo.Model.PlotBox;
import com.codechallenge.demo.Model.ScatterChart;
import com.codechallenge.demo.Service.PythonParserImp;
import com.codechallenge.demo.Template.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class PythonParserController {

    private PythonParserImp pythonParser = new PythonParserImp();

    @PostMapping("/execute")
    public String parsePythonInputCode(@RequestBody String code, HttpSession session){
        return pythonParser.pythonCodeParser(code, session);
    }

    @PostMapping("/execute/scatter-chart")
    public Response<ScatterChart> parseChartsData(@RequestBody String code){
        InputData inputData = pythonParser.getSeriesFromInput(code);
        List<ScatterChart> list = pythonParser.scatterChartDataGenerator(inputData.getSeries(), inputData.getValues());
        return new Response<ScatterChart>(list, inputData.getSeries());
    }

    @PostMapping("/execute/plotbox-chart")
    public Response<PlotBox> parsePlotBoxsData(@RequestBody String code){
        InputData inputData = pythonParser.getSeriesFromInput(code);
        List<PlotBox> list = pythonParser.plotBoxDataGenerator(inputData.getSeries(), inputData.getValues());
        return new Response<PlotBox>(list, inputData.getSeries());
    }

    @PostMapping("/execute/bar-chart")
    public Response<BarChart> parseBarChartData(@RequestBody String code){
        InputData inputData = pythonParser.getSeriesFromInput(code);
        List<BarChart> list = pythonParser.barChartDataGenerator(inputData.getSeries(), inputData.getValues());
        return new Response<BarChart>(list, inputData.getSeries());
    }
}
