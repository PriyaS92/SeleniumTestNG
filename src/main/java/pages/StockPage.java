package pages;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestContext;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.google.common.io.Files;

import CommonFunctions.CommonTest;
import CommonFunctions.Reports;


public class StockPage extends CommonTest{
	@FindBy(xpath="//input[@id='header-search-input']")
	public WebElement search_textbox;
	@FindBy(xpath="//div[@role='listbox']/descendant::div[2]")
	public WebElement search_result;
	@FindBy(xpath="//span[@id='quoteLtp']")
	public WebElement stock_price;
	@FindBy(xpath="//div[@class='nav nav-tabs horizontal_tab']/descendant::a[3]")
	public WebElement historical_data_tab;
	@FindBy(xpath="//div[@id='info-historicaldata']/descendant::a[3]")
	public WebElement periodic_high_low_data_tab;
	public WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;
	private static Logger logger = LoggerFactory.getLogger(StockPage.class);
	
	
	public StockPage(){
		this.driver = getDriver();
		PageFactory.initElements(driver, this);
	}
	
	public void navigate_application() {
		try {
			getDriver().get("https://www.nseindia.com/");
			TimeUnit.SECONDS.sleep(15);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void search_functionality(String data) {
		try {
			search_textbox.sendKeys(data);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			search_result.click();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void retrieving_stock(String data,ITestContext testContext) {
		try {
			logger.info("Stock Price of "+data+":"+stock_price.getText().trim());
			Reports.setTest(extent.createTest("NSE India Stock - Pre Stock retrieval - "+testContext.getName(), "For company:"+data));			
			driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(5));
			if(stock_price.isDisplayed()) {
				Reports.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromBase64String("data:image/png;base64,"+capture_screenshot()).build());
			}
			else {
				Reports.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromBase64String("data:image/png;base64,"+capture_screenshot()).build());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void historic_data(String data,ITestContext testContext,Double high_price,Double low_price) {
		try {
			logger.info("Retrieving historic data");
			historical_data_tab.click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(35));
			periodic_high_low_data_tab.click();
			driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(3));
			String header_1 = driver.findElement(By.xpath("//div[@id='card1']/descendant::h4[1]")).getText().trim();
			String from_date = driver.findElement(By.xpath("//div[@id='card1']/descendant::h4[2]/span[1]")).getText().trim();
			String to_date = driver.findElement(By.xpath("//div[@id='card1']/descendant::h4[2]/span[2]")).getText().trim();
			String high_value = driver.findElement(By.xpath("//div[@id='card1']/descendant::table[1]/table/tbody/tr[2]/td[2]")).getText().trim().replace(",", "");
			String low_value = driver.findElement(By.xpath("//div[@id='card1']/descendant::table[1]/table/tbody/tr[3]/td[2]")).getText().trim().replace(",", "");
			logger.info(header_1+from_date+" to "+to_date);
			logger.info("High price of "+data+": "+high_value);
			logger.info("Low price of "+data+": "+low_value);
			js = (JavascriptExecutor)driver;
			js.executeScript("document.body.style.zoom ='80%'");
			Reports.setTest(extent.createTest("NSE India Stock - Post Stock - "+testContext.getName(), "For company:"+data));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			if(driver.findElement(By.xpath("//div[@id='card1']/descendant::table[1]/table/tbody/tr[2]/td[2]")).isDisplayed()) {
				System.out.println(high_price+";"+high_value);
				System.out.println(low_price+";"+low_value);
				Assert.assertTrue(String.valueOf(high_price).equals(high_value));
				Reports.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromBase64String("data:image/png;base64,"+capture_screenshot()).build());
			}
			else {
				Reports.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromBase64String("data:image/png;base64,"+capture_screenshot()).build());
			}
			extent.flush();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
