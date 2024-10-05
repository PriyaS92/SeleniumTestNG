import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import CommonFunctions.CommonTest;
import CommonFunctions.ReadExcel;
import pages.StockPage;

public class NSE_Stock extends CommonTest{

	WebDriverWait wait;
	JavascriptExecutor js;
	StockPage stock_search;
	ReadExcel read_excel;
	String data;
	ArrayList<String> lists = new ArrayList<String>();
	Set<Double> highprice = new TreeSet<Double>();
	Set<Double> lowprice = new TreeSet<Double>();
	Double high_price,low_price;
	int j=0;
	
	public NSE_Stock() {
		super();
	}
	
	@Test
	public void validate_stocks(ITestContext context) {
		try {
			stock_search = new StockPage();
			read_excel = new ReadExcel();
			stock_search.navigate_application();
			lists = read_excel.excel_Read();
			highprice = read_excel.gethighPrices();
			lowprice = read_excel.getlowPrices();
			List<Double> high = new ArrayList<>(highprice);
			List<Double> low = new ArrayList<>(lowprice);
			for(int i=0;i<lists.size();i++) {
				highprice.iterator().next();
				data = lists.get(i);				
				high_price = high.get(i);
				low_price = low.get(i);
				stock_search.search_functionality(data);
				stock_search.retrieving_stock(data,context);
				stock_search.historic_data(data,context,high_price,low_price);
			}			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
