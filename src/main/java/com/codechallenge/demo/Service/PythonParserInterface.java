package com.codechallenge.demo.Service;

import javax.servlet.http.HttpSession;

public interface PythonParserInterface {
    String pythonCodeParser(String code, HttpSession session);
}
