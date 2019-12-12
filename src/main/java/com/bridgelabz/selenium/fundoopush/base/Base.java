package com.bridgelabz.selenium.fundoopush.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author pratiksha
 *
 */
public class Base {

	WebDriver driver;
	Properties properties;

	/**
	 * @param browserName
	 * @return
	 */
	public WebDriver initDriver(String browserName) {

		if (browserName.equals("chrome")) {
			// To set the path of crome driver
			System.setProperty("webdriver.chrome.driver",
					"/home/admin1/Downloads/chromedriver_linux64 (1)/chromedriver");
			ChromeOptions options = new ChromeOptions();

			// To open the crome in incognitto
			options.addArguments("--incognito");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			driver = new ChromeDriver(capabilities);
			// To maximize window
			driver.manage().window().maximize();

		} else if (browserName.equals("firefox")) {
			// To set the path of firefoxdriver
			System.setProperty("webdriver.gecko.driver",
					"//home/admin1/Downloads/geckodriver-v0.26.0-linux64/geckodriver");
			driver = new FirefoxDriver();
		}
		return driver;
	}

	/**
	 * @return object of properties
	 * @throws FileNotFoundException 
	 */
	public Properties initProperties() throws FileNotFoundException {
		properties = new Properties();

		
			// set the path of properties file
			FileInputStream input = new FileInputStream(
					"/home/admin1/Downloads/KeywordDriven/src/main/java/com/bridgelabz/selenium/fundoopush/config/config.properties");
			// To fetch all the date from input file
			try {
				properties.load(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return properties;
	}
}
