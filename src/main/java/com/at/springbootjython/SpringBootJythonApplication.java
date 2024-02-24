package com.at.springbootjython;

import com.at.springbootjython.util.ScriptEngineManagerUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootJythonApplication {

	public static void main(String[] args) {
		ScriptEngineManagerUtils.listEngines();
		SpringApplication.run(SpringBootJythonApplication.class, args);
	}

}
