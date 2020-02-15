package com.codechallenge.demo.Controller;

import com.codechallenge.demo.Service.PythonParserImp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class PythonParserController {
    private PythonParserImp pythonParser = new PythonParserImp();


    @PostMapping("/execute")
    public String parsePythonInputCode(@RequestBody String code, HttpSession session){
        System.out.println(session.getId());
        return pythonParser.pythonCodeParser(code, session);
    }
}
