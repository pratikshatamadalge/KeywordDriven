package com.bridgelabz.selenium.fundoopush.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.bridgelabz.selenium.fundoopush.base.Base;

public class KeywordEngine {

	public WebDriver driver;
	public Properties properties;
	public static Workbook book;
	public static Sheet sheet;
	public Base base;
	WebElement element;
	public final String SCENARIO_PATH = "/home/admin1/selenium-workspace/KeywordDriven/src/main/java/com/bridgelabz/selenium/fundoopush/scenario/Fundoopush.xls";

	public void startExecution(String sheetname) {

		FileInputStream file = null;
		String locatorName = null;
		String locatorValue = null;
		try {
			file = new FileInputStream(SCENARIO_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			book = WorkbookFactory.create(file);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetname);
		int k = 0;
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			try {
				String locatorColValue = sheet.getRow(i + 1).getCell(k + 1).toString().trim();
				if (!locatorColValue.equalsIgnoreCase("NA")) {
					locatorName = locatorColValue.split("=")[0].trim();
					locatorValue = locatorColValue.split("=")[1].trim();
				}

				String action = sheet.getRow(i + 1).getCell(k + 2).toString().trim();
				String value = sheet.getRow(i + 1).getCell(k + 3).toString().trim();

				switch (action) {
				case "open browser":
					base = new Base();
					properties = base.initProperties();
					if (value.isEmpty() || value.equals("NA")) {
						driver = base.initDriver(properties.getProperty("browser"));
					} else {
						driver = base.initDriver(value);
					}
					break;

				case "enter url":
					if (value.isEmpty() || value.equals("NA")) {
						driver.get(properties.getProperty("url"));
					} else {
						driver.get(value);
					}
					break;

				case "quit":
					driver.close();
					break;

				default:
					break;
				}

				switch (locatorName) {
				case "id":
					element = driver.findElement(By.id(locatorValue));
					if (action.equalsIgnoreCase("sendkeys")) {
						element.clear();
						element.sendKeys(value);
					} else if (action.equalsIgnoreCase("click")) {
						element.click();
					}
					locatorName = null;
					break;

				case "linkText":
					element = driver.findElement(By.linkText(locatorValue));
					element.click();
					locatorName = null;
					break;

				case "xpath":
					String result = locatorColValue.replaceFirst("xpath =", "").trim();
					element = driver.findElement(By.xpath(result));
					element.click();
					locatorName = null;
					break;

				default:
					break;
				}
			} catch (Exception e) {

			}
		}
	}
}