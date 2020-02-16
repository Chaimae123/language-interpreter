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

public class InterpreterImp implements InterpreterInterface {

    private ScriptEngineManager scriptEngineManager;
    private ScriptEngine scriptEngine;
    private String languageType = "javascript";
    private static final String GROUP = "Group ";

    public InterpreterImp() {
        scriptEngineManager = new ScriptEngineManager();
        scriptEngine = scriptEngineManager.getEngineByName(this.getLanguageType());
    }

    private InputString splitInputString(String inputString){
        ObjectMapper objectMapper = new ObjectMapper();
        CodeModel codeModel = new CodeModel();
        InputString generatedInputString = new InputString();
        try {
            codeModel = objectMapper.readValue(inputString, CodeModel.class);

        }catch (IOException e){
            Logger.getLogger("IOException").log(Level.SEVERE, null, e);
        }

        String[] subStrings = codeModel.getCode().split(" ", 2);

        generatedInputString.setCode(subStrings[1].trim());
        generatedInputString.setLanguage(subStrings[0].trim());

        return generatedInputString;
    }

    @Override
    public String pythonCodeParser(String code, HttpSession session) {
        InputString inputString = splitInputString(code);
        String codeInput = inputString.getCode();
        OutputCode outputCode;

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
            outputCode = new OutputCode();
            if (inputString.getCode().contains("print")){
                return printCodeInterpreter(codeInput, outputCode);
            }else {
                scriptEngine.eval(codeInput);
                return code;
            }

        } catch (ScriptException e) {
            Logger.getLogger("ScriptException").log(Level.SEVERE, null, e);
            return "Value of <" + codeInput + "> not found";
        }
    }
    private String printCodeInterpreter(String codeInput, OutputCode codeOutput) throws ScriptException{
        ObjectMapper resultObjectMapper = new ObjectMapper();
        try {
            codeOutput.setResult(String.valueOf(scriptEngine.eval(codeInput))); //
            return resultObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(codeOutput);
        }catch (JsonProcessingException e){
            Logger.getLogger("JsonProcessingException").log(Level.SEVERE, null, e);
            return "JSON Object could not be parsed";
        }
    }

    @Override
    public InputData getSeriesFromInput(String dataString){
        InputString inputString = splitInputString(dataString);
        String outputMatch = "";
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
    public List<ScatterChart> scatterChartDataGenerator(List<String> series, List<Integer> values) {

        List<ScatterChart> data = new ArrayList<>();
        int totalGroups = values.size() / series.size();
        ScatterChart scatterChartElement;
        int k = 0;
        while (k + 9 <values.size()){
            for (int j=0; j<series.size(); j++){
                scatterChartElement = new ScatterChart(k, series.get(j), GROUP + totalGroups, values.get(k++), values.get(k++), values.get(k++));
                data.add(scatterChartElement);
            }
            totalGroups--;
        }
        return data;
    }

    @Override
    public List<PlotBox> plotBoxDataGenerator(List<String> series, List<Integer> values) {
        List<PlotBox> data = new ArrayList<>();
        int totalGroups = values.size() / 7;
        PlotBox plotBox;
        int k = 0;
        while (k + 7 <values.size()){
            for (int j=0; j<series.size(); j++) {
                if (k + 7 < values.size()) {
                    plotBox = new PlotBox(k, series.get(j), GROUP + (totalGroups - 1), values.get(k++),
                            values.get(k++), values.get(k++), values.get(k++), values.get(k++), values.get(k++));
                    if (j == series.size() - 1 || values.size() - k < 7) {
                        plotBox.setOutliers(values.subList(k, values.size()));
                    } else {
                        List<Integer> sublist = new ArrayList<>();
                        sublist.add(values.get(k++));
                        plotBox.setOutliers(sublist);
                    }
                    data.add(plotBox);
                }
            }
            totalGroups--;
        }
        return data;
    }

    @Override
    public List<BarChart> barChartDataGenerator(List<String> series, List<Integer> values) {
        List<BarChart> data = new ArrayList<>();
        int totalGroups = values.size() / series.size() + 1;
        BarChart barChart;
        int k = 0;
        while (k < values.size()){
            for (int j=0; j<series.size(); j++){
                if (k<values.size() && values.get(k) != null) {
                    barChart = new BarChart(k, series.get(j), GROUP + totalGroups,
                            values.get(k));
                    data.add(barChart);
                    k++;
                }
            }
            totalGroups--;
        }
        return data;
    }

    private String getLanguageType() {
        return languageType;
    }

    private void setLanguageType(String languageType) {
        this.languageType = languageType;
    }
}
