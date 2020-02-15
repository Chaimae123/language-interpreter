package com.codechallenge.demo.Service;

import com.codechallenge.demo.Model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PythonParserImp implements PythonParserInterface{

    ScriptEngineManager scriptEngineManager;
    ScriptEngine scriptEngine;
    private String codeInput ;
    private OutputCode outputCode;
    private String languageType = "javascript";

    public PythonParserImp() {
        scriptEngineManager = new ScriptEngineManager();
        scriptEngine = scriptEngineManager.getEngineByName(this.getLanguageType());
    }

    public InputString splitInputString(String inputString){
        ObjectMapper objectMapper = new ObjectMapper();
        PythonCode pythonCode = new PythonCode();
        InputString generatedInputString = new InputString();
        String[] subStrings;
        try {
            pythonCode = objectMapper.readValue(inputString, PythonCode.class);

        }catch (IOException e){
            Logger.getLogger("IOException").log(Level.SEVERE, null, e);
        }

        subStrings = pythonCode.getCode().split(" ", 2);

        generatedInputString.setCode(subStrings[1].trim());
        generatedInputString.setLanguage(subStrings[0].trim());

        return generatedInputString;
    }

    @Override
    public String pythonCodeParser(String code, HttpSession session) {
        ObjectMapper resultObjectMapper = new ObjectMapper();
        InputString inputString = splitInputString(code);
        outputCode = new OutputCode();
        codeInput = inputString.getCode();

        if (inputString.getLanguage().substring(0,1).equals("%")){
            setLanguageType(inputString.getLanguage().substring(1, inputString.getLanguage().length()));
        }else {
            return "Incorrect input";
        }

        if (codeInput.contains("print")){
            codeInput = codeInput.substring(codeInput.indexOf(' ')).trim();
        }

        if (session.isNew()){
            scriptEngineManager = new ScriptEngineManager();
            scriptEngine = scriptEngineManager.getEngineByName(getLanguageType());
        }

        if (scriptEngineManager.getEngineByName(getLanguageType()) == null){
            session.invalidate();
            return "Language specified not found.";
        }

        try {
            if (inputString.getCode().contains("print")){
                try {
                    outputCode.setResult(String.valueOf(scriptEngine.eval(codeInput)));
                    return resultObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(outputCode);
                }catch (JsonProcessingException e){
                    Logger.getLogger("JsonProcessingException").log(Level.SEVERE, null, e);
                    return "JSON Object could not be parsed";
                }
            }else {
                scriptEngine.eval(codeInput);
                return code;
            }

        } catch (ScriptException e) {
            Logger.getLogger("ScriptException").log(Level.SEVERE, null, e);
            return "Value of <" + codeInput + "> not found";
        }
    }
    @Override
    public InputData getSeriesFromInput(String dataString){
        InputString inputString = splitInputString(dataString);
        String outputMatch = new String();
        Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(inputString.getCode());
        while(m.find()) outputMatch = m.group(1);
        String stringUnquoted = outputMatch.substring(1, outputMatch.length()-1);
        String[] dataSplit = stringUnquoted.split(Pattern.quote("\\t"));
        ArrayList<Integer> values = new ArrayList<>();
        ArrayList<String> series = new ArrayList<>();
        InputData returnedObject = new InputData();

        for (int i=0; i<dataSplit.length; i++){
            if (!dataSplit[i].contains("\\n")){
                series.add(dataSplit[i]);
            }else{
                 String[] vals = dataSplit[i].split(Pattern.quote("\\n"));
                 for (int j=0; j< vals.length; j++){
                     values.add(Integer.parseInt(vals[j]));
                 }
            }
        }
        returnedObject.setSeries(series);
        returnedObject.setValues(values);

        return returnedObject;
    }

    @Override
    public List<ScatterChart> scatterChartDataGenerator(ArrayList<String> series, ArrayList<Integer> values) {

        List<ScatterChart> data = new ArrayList<>();
        Integer totalGroups = values.size() / series.size();
        ScatterChart scatterChartElement;
        int k = 0;
        while (k + 9 <values.size()){
            for (int j=0; j<series.size(); j++){
                scatterChartElement = new ScatterChart(k, series.get(j), "Group " + totalGroups, values.get(k++), values.get(k++), values.get(k++));
                data.add(scatterChartElement);
            }
            totalGroups--;
        }
        return data;
    }

    @Override
    public List<PlotBox> plotBoxDataGenerator(ArrayList<String> series, ArrayList<Integer> values) {
        List<PlotBox> data = new ArrayList<>();
        Integer totalGroups = values.size() / 7;
        PlotBox plotBox;
        int k = 0;
        while (k + 7 <values.size()){
            for (int j=0; j<series.size(); j++){
                plotBox = new PlotBox(k, series.get(j), "Group " + (Math.round(totalGroups) - 1), values.get(k++),
                        values.get(k++), values.get(k++), values.get(k++), values.get(k++), values.get(k++));
                if (j == series.size() - 1 || values.size() - k < 7){
                    plotBox.setOutliers(values.subList(k, values.size()));
                }else {
                    List<Integer> sublist = new ArrayList<>();
                    sublist.add(values.get(k++));
                    plotBox.setOutliers(sublist);
                }
                data.add(plotBox);
            }
            totalGroups--;
        }
        return data;
    }

    @Override
    public List<BarChart> barChartDataGenerator(ArrayList<String> series, ArrayList<Integer> values) {
        List<BarChart> data = new ArrayList<>();
        Integer totalGroups = values.size() / series.size();
        BarChart barChart;
        int k = 0;
        while (k < values.size()){
            for (int j=0; j<series.size() - 1; j++){
                barChart = new BarChart(k, series.get(j), "Group " + totalGroups,
                        values.get(k) != null ? values.get(k) : 0);
                data.add(barChart);
                k++;
            }
            totalGroups--;
        }
        return data;
    }

    public String getLanguageType() {
        return languageType;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }
}
