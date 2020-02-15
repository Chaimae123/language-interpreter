package com.codechallenge.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	 public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	/*
	public static void main(String[] args) {
		try(PythonInterpreter pyInterp = new PythonInterpreter()) {
			pyInterp.exec("a=1; b=2; print a+b");
		}
	} */

}
