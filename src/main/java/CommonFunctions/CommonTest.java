package CommonFunctions;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.observer.ExtentObserver;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.common.io.Files;

public class CommonTest {
	public WebDriver driver;
	protected static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
	ExtentSparkReporter extentSparkReporter;
	String encodedBase64 = null;
	protected static ExtentReports extent;
	
	
	public CommonTest(){
	
	}
	
	@BeforeClass
	public void startReporter(ITestContext context)
	   {
		   extentSparkReporter  = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/extentReport.html");
	       //configuration items to change the look and feel
	       //add content, manage tests etc
	       extentSparkReporter.config().setDocumentTitle("Automation Report");
	       extentSparkReporter.config().setReportName("Test Report");
	       extentSparkReporter.config().setTheme(Theme.STANDARD);
	       extentSparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
	       extent = new ExtentReports();
	       extent.attachReporter(extentSparkReporter);
	    }
	
	@BeforeTest
	@Parameters({"browser"})
	public void capability_setup(String browser) {
		if(browser.equals("Chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.setAcceptInsecureCerts(true);
			options.addArguments("start-maximized");
			options.addArguments("--disable-web-security");
			options.addArguments("--disable-site-isolation-trials");
			options.addArguments("--disable-blink-features");
			options.addArguments("--disable-blink-features=AutomationControlled");
			options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation" , "load-extension"));
			options.setExperimentalOption("useAutomationExtension", false);
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Administrator\\git\\SeleniumTestNG\\chromedriver.exe");
			driver = new ChromeDriver(options);
		}
		else if(browser.equals("Edge")) {
			 EdgeOptions options = new EdgeOptions();
			 options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			 options.setExperimentalOption("excludeSwitches", Arrays.asList("disable-popup-blocking"));
			 options.setExperimentalOption("useAutomationExtension", false);
			 options.addArguments("start-maximized");
			 options.addArguments("--disable-web-security");
			 options.addArguments("--disable-site-isolation-trials");
			 options.addArguments("--disable-blink-features");
			 options.addArguments("--disable-blink-features=AutomationControlled");
			 System.setProperty("webdriver.edge.driver", "C:\\Users\\Administrator\\git\\SeleniumTestNG\\msedgedriver.exe");
			 driver = new EdgeDriver(options);
		}
		threadLocalDriver.set(driver);
	}
	
	public static WebDriver getDriver(){
        return threadLocalDriver.get();
    }
	
	@AfterTest(alwaysRun=true)
	public void tear_down() {
		try {
			if(driver!=null) {
				getDriver().quit();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String capture_screenshot() throws IOException {
		byte[] screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
		encodedBase64 = Base64.getEncoder().encodeToString(screenshot);
		return encodedBase64;
	}
}
