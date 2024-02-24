package com.at.springbootjython;


import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootJythonApplicationTests {



	@Test
	public void givenPythonScriptEngineIsAvailable_whenScriptInvoked_thenOutputDisplayed() throws Exception {
		StringWriter output = new StringWriter();
		ScriptContext context = new SimpleScriptContext();
		context.setWriter(output);

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("python");
		engine.eval(new FileReader(resolvePythonScriptPath("hello.py")), context);
		Assertions.assertEquals("Hello My Dear Readers!", output.toString()
				.trim());
	}

	@Test
	public void givenPythonInterpreter_whenPrintExecuted_thenOutputDisplayed() {
		try (PythonInterpreter pyInterp = new PythonInterpreter()) {
			StringWriter output = new StringWriter();
			pyInterp.setOut(output);

			pyInterp.exec("print('Hello My Dear Readers!')");
			Assertions.assertEquals("Hello My Dear Readers!", output.toString()
					.trim());
		}
	}

	@Test
	public void givenPythonInterpreter_whenNumbersAdded_thenOutputDisplayed() {
		try (PythonInterpreter pyInterp = new PythonInterpreter()) {
			pyInterp.exec("x = 10+10");
			PyObject x = pyInterp.get("x");
			Assertions.assertEquals( 20, x.asInt());
		}
	}



//	@Test
//	public void givenPythonScript_whenPythonProcessExecuted_thenSuccess() throws ExecuteException, IOException {
//		String line = "python " + resolvePythonScriptPath("hello.py");
//		CommandLine cmdLine = CommandLine.parse(line);
//
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
//
//		DefaultExecutor executor = new DefaultExecutor();
//		executor.setStreamHandler(streamHandler);
//
//		int exitCode = executor.execute(cmdLine);
//		System.out.println("---"+exitCode);
//		Assertions.assertEquals( 0, exitCode);
//		Assertions.assertEquals( "Hello My Dear Readers!!", outputStream.toString()
//				.trim());
//	}

	private List<String> readProcessOutput(InputStream inputStream) throws IOException {
		try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
			return output.lines()
					.collect(Collectors.toList());
		}
	}

	private String resolvePythonScriptPath(String filename) {
		File file = new File("src/test/resources/" + filename);
		return file.getAbsolutePath();
	}

}
