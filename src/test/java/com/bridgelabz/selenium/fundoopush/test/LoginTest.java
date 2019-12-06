package com.bridgelabz.selenium.fundoopush.test;


import org.testng.annotations.Test;

import com.bridgelabz.selenium.fundoopush.engine.KeywordEngine;

public class LoginTest {

	public KeywordEngine keywordEngine;

	@Test
	public void loginTest() {
		keywordEngine = new KeywordEngine();
		keywordEngine.startExecution("login");
	}
}
