package com.bridgelabz.selenium.fundoopush.test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.bridgelabz.selenium.fundoopush.engine.KeywordEngine;

public class LoginTest {
	WebDriver driver;
	public KeywordEngine keywordEngine;

	@Test
	public void loginTest() {
		keywordEngine = new KeywordEngine();
		keywordEngine.startExecution("login");
	}
}
