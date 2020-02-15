package com.codechallenge.demo.Service;

import com.codechallenge.demo.Model.OutputCode;
import com.codechallenge.demo.Model.PythonCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;

import javax.script.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PythonParserImp implements PythonParserInterface{

    ScriptEngineManager scriptEngineManager;
    ScriptEngine scriptEngine;
    private String codeInput ;
    private OutputCode outputCode;
    private String languageType = "javascript";
    Bindings bindings;

    public PythonParserImp() {
        scriptEngineManager = new ScriptEngineManager();
        scriptEngine = scriptEngineManager.getEngineByName(this.getLanguageType());
        bindings = scriptEngine.getBindings(ScriptContext.ENGINE_SCOPE);
    }

    @Override
    public String pythonCodeParser(String code, HttpSession session) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper resultObjectMapper = new ObjectMapper();
        PythonCode pythonCode = new PythonCode();

        try {
            pythonCode = objectMapper.readValue(code, PythonCode.class);
        }catch (IOException e){
            Logger.getLogger("IOException").log(Level.SEVERE, null, e);
        }

            String[] subStrings = pythonCode.getCode().split(" ", 2);
            codeInput = subStrings[1].trim();
            if (subStrings[0].substring(0,1).equals("%")){
                this.setLanguageType(subStrings[0].trim().substring(1, subStrings[0].length()));
            }else {
                return "Incorrect input";
            }

            if (codeInput.contains("print")){
                 codeInput = codeInput.substring(codeInput.indexOf(' ')).trim();
             }
            if (session.isNew()){
                scriptEngineManager = new ScriptEngineManager();
                scriptEngine = scriptEngineManager.getEngineByName(this.getLanguageType());
            }

            if (scriptEngineManager.getEngineByName(this.getLanguageType()) == null){
                session.invalidate();
                return "Language specified not found.";
            }

        try {
            outputCode = new OutputCode();
            if (code.contains("print")){
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

    public String getLanguageType() {
        return languageType;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }
}
