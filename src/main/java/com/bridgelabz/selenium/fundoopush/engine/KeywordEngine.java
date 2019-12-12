package com.bridgelabz.selenium.fundoopush.engine;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.bridgelabz.selenium.fundoopush.base.Base;

/**
 * @author pratiksha
 *
 */
public class KeywordEngine {

	public WebDriver driver;
	public Properties properties;
	public static Workbook book;
	public static Sheet sheet;
	public Base base;
	WebElement element;
	WebElement AddImageOption;
	Robot robot;
	String image = "/home/admin1/Desktop/image";
	public final String SCENARIO_PATH = "/home/admin1/Downloads/KeywordDriven/src/main/java/com/bridgelabz/selenium/fundoopush/scenario/Fundoopush.xls";

	/**
	 * @param sheetname
	 */
	public void startExecution(String sheetname) {

		// To fetch data from the xlsx files
		FileInputStream file = null;
		try {
			file = new FileInputStream(SCENARIO_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// To initialize workbook
		try {
			book = WorkbookFactory.create(file);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}

		// To initialize sheet
		sheet = book.getSheet(sheetname);

		// Logic to read xls file row and coulumns wise
		// initialization variable k for coulumn
		int k = 0;

		// initialization row row wise
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			try {
				System.out.println("***************************");

				System.out.println("lastrownumber" + sheet.getLastRowNum() + " i=" + i);
				String locatorName = sheet.getRow(i + 1).getCell(k + 1).toString().trim();
				String locatorValue = sheet.getRow(i + 1).getCell(k + 2).toString().trim();
				System.out.println("locatorname=" + locatorName + " locatovalue=" + locatorValue);
				String action = sheet.getRow(i + 1).getCell(k + 3).toString().trim();
				String value = sheet.getRow(i + 1).getCell(k + 4).toString().trim();

				// Switch case for action coulumn
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
					Thread.sleep(5000);
					driver.close();
					break;

				case "add image":
					System.out.println("inside add image");
					// AddImageOption.click();
					System.out.println("after click on add image option");
					StringSelection imagepath = new StringSelection(image);
					System.out.println("imagepath:" + imagepath);
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(imagepath, null);
					Thread.sleep(1000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_V);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					robot.keyRelease(KeyEvent.VK_V);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);
//					robot.keyPress(KeyEvent.VK_ENTER);
//					robot.keyRelease(KeyEvent.VK_ENTER);

					robot.setAutoDelay(2000);
					break;

				case "scroll page":
					JavascriptExecutor jscript = (JavascriptExecutor) driver;
					jscript.executeScript("window.scrollBy(0,1000)");
					break;

				default:
					break;
				}

				// Switch case for locatorname coulumn
				switch (locatorName) {
				case "id":
					element = driver.findElement(By.id(locatorValue));
					if (action.equalsIgnoreCase("sendkeys")) {
						element.clear();
						element.sendKeys(value);
					} else if (action.equalsIgnoreCase("click")) {
						element.click();
					} else if (action.equalsIgnoreCase("isDisplayed")) {
						element.isDisplayed();
					} else if (action.equalsIgnoreCase("getText")) {
						String elementText = element.getText();
						System.out.println("elementText=" + elementText);
					}
					locatorName = null;
					break;

				case "name":
					element = driver.findElement(By.name(locatorValue));
					if (action.equalsIgnoreCase("sendkeys")) {
						element.clear();
						element.sendKeys(value);
					} else if (action.equalsIgnoreCase("click")) {
						element.click();
					} else if (action.equalsIgnoreCase("isDisplayed")) {
						element.isDisplayed();
					} else if (action.equalsIgnoreCase("getText")) {
						String elementText = element.getText();
						System.out.println("elementText=" + elementText);
					}
					locatorName = null;
					break;

				case "linkText":
					element = driver.findElement(By.linkText(locatorValue));
					element.click();
					locatorName = null;
					break;

				case "partialText":
					System.out.println("action:" + action);
					element = driver.findElement(By.partialLinkText(locatorValue));
					System.out.println("element in partial text: " + element);
					element.click();
					locatorName = null;
					break;

				case "xpath":
					System.out.println("xpath:" + locatorValue);
					System.out.println("action:" + action);
					element = driver.findElement(By.xpath(locatorValue));
					System.out.println("element" + element);
					if (action.equalsIgnoreCase("sendkeys")) {
						element.clear();
						element.sendKeys(value);
					} else if (action.equalsIgnoreCase("click")) {
						System.out.println("inside xpath click");
						element.click();
						Thread.sleep(3000);
					} else if (action.equalsIgnoreCase("isDisplayed")) {
						element.isDisplayed();
					} else if (action.equalsIgnoreCase("getText")) {
						String elementText = element.getText();
						System.out.println("elementText=" + elementText);
					}

					locatorName = null;
					break;

				case "cssSelector":
					element = driver.findElement(By.cssSelector(locatorValue));
					if (action.equalsIgnoreCase("sendkeys")) {
						element.clear();
						element.sendKeys(value);
					} else if (action.equalsIgnoreCase("click")) {
						element.click();
					} else if (action.equalsIgnoreCase("isDisplayed")) {
						element.isDisplayed();
					} else if (action.equalsIgnoreCase("getText")) {
						String elementText = element.getText();
						System.out.println("elementText=" + elementText);
					}
					locatorName = null;
					break;

				case "className":
					element = driver.findElement(By.className(locatorValue));
					if (action.equalsIgnoreCase("sendkeys")) {
						element.clear();
						element.sendKeys(value);
					} else if (action.equalsIgnoreCase("click")) {
						element.click();
					} else if (action.equalsIgnoreCase("isDisplayed")) {
						element.isDisplayed();
					} else if (action.equalsIgnoreCase("getText")) {
						String elementText = element.getText();
						System.out.println("elementText=" + elementText);
					}
					locatorName = null;
					break;

				default:
					System.out.println("default =" + locatorValue);
					break;
				}
			} catch (Exception e) {

			}
		}
	}
}