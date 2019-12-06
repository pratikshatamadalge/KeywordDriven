package com.bridgelabz.selenium.fundoopush.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Base {

	WebDriver driver;
	Properties properties;

	public WebDriver initDriver(String browserName) {
		if (browserName.equals("firefox")) {
			
			// System.setProperty("webdriver.chrome.driver",
			// "/home/admin1/Downloads/chromedriver_linux64 (1)");
			   System.setProperty("webdriver.gecko.driver",
		               "//home/admin1/Downloads/geckodriver-v0.26.0-linux64/geckodriver");
			if (properties.getProperty("headless").equals("yes")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless");
				driver = new ChromeDriver();
			} else {
				driver = new FirefoxDriver();
			}
		}
		return driver;
	}

	public Properties initProperties() {
		properties = new Properties();

		try {
			FileInputStream input = new FileInputStream("/home/admin1/selenium-workspace/KeywordDriven/"
					+ "src/main/java/com/bridgelabz/selenium/fundoopush/config/config.properties");
			properties.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
}
