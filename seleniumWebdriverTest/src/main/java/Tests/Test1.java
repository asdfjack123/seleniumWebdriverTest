package Tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Strs;

public class Test1 {
	private WebDriver driver;
	private WebElement function;
	private WebElement action;
	private WebElement userId;
	private WebElement logBeginDate;
	private WebElement logEndDate;
	private WebElement form;
	private WebElement searchButton;
	private WebElement clearButton;
	private WebElement printExcelButton;
	private WebElement printPdfButton;
	
	
	
	/**
	 * 預設登入後進使用者紀錄查詢
	 */
	public Test1() {
		System.setProperty("webdriver.chrome.driver", "C:\\chr\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://10.0.0.123/MegaImage/home/SSOLogin?lightId=12345678");

		WebElement s = driver.findElement(By.tagName("h5"));	
		System.out.println("----------------------");
		System.out.println(s);
		System.out.println(s.getText());
		System.out.println("----------------------");
		// 找到系統管理
		WebElement t = driver.findElement(By.partialLinkText("系統管理"));
		System.out.println("----------------------");
		System.out.println(t);
		System.out.println(t.getText());
		System.out.println("----------------------");
		//點擊系統管理
		t.click();
		sleep(1000);	
		// 找到使用者紀錄查詢(id是SystemAdmin.TransactionLog)
		WebElement href = driver.findElement(By.id("SystemAdmin.TransactionLog"));
		System.out.println("----------------------");
		System.out.println(href.getText());
		System.out.println(href.getAttribute("value"));
		System.out.println(href.getAttribute("href"));
		System.out.println(href);
		System.out.println("----------------------");
		//點擊使用者紀錄
		href.click();
		
		sleep(1000);
		
		function = driver.findElement(By.id("function"));
		function = driver.findElement(By.id(""));
		function = driver.findElement(By.id(""));
		function = driver.findElement(By.id(""));
		function = driver.findElement(By.id(""));
		function = driver.findElement(By.id(""));
		function = driver.findElement(By.id(""));
		
	}
	
	
	public void put3Data(String function,String logBeginDate,String logEndDate) {
//		System.setProperty("webdriver.chrome.driver", "C:\\chr\\chromedriver.exe");
//		WebDriver driver = new ChromeDriver();

//		List<WebElement> options = driver.findElements(By.tagName("option"));
//		for (WebElement option : options) {
//			String opt = option.getText();
//			System.out.println(opt);
//			if (function.equals(option.getAttribute("text"))) {
//				option.click();
//				break;
//			}
//		}
//		
		Select select = new Select(driver.findElement(By.tagName("select")));
		select.selectByVisibleText(function);

		
		sleep(1000);

//		WebElement WElogBeginDate = driver.findElement(By.id("logBeginDate"));
//		WElogBeginDate.clear();
//		WElogBeginDate.sendKeys(logBeginDate);

		this.logBeginDate.clear();
		this.logBeginDate.sendKeys(logBeginDate);
		
		sleep(1000);

		WebElement WElogEndDate = driver.findElement(By.id("logEndDate"));
		WElogEndDate.clear();
		WElogEndDate.sendKeys(logEndDate);
		sleep(1000);
		

	}

	public void putData(String id,String data) {
		WebElement element = driver.findElement(By.id(id));
		if(element == null) {
			return;
		}
		element.clear();
		element.sendKeys(data);
	}
	
	public void quit() {
		driver.quit();
	}
	
	public void close() {
		driver.close();
	}
	
	public void printExcel() {
		List<WebElement> buttons = driver.findElements(By.tagName("button"));
		for (WebElement button : buttons) {
			if ("匯出Excel".equals(button.getText())) {
				button.click();
				break;
			}
		}
		checkAlertMessage();
	}
	
	public void search() {
		List<WebElement> buttons = driver.findElements(By.tagName("button"));
		for (WebElement button : buttons) {
			if ("查詢".equals(button.getText())) {
				button.click();
				break;
			}
		}
		checkAlertMessage();
	}
	
	private void checkAlertMessage() {
		try {
			(new WebDriverWait(driver, 1)).until(new ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver d) {
	                return (d.findElement(By.className("bootbox-body")) != null );
	            }
	        });
			String message = driver.findElement(By.className("bootbox-body")).getText();
			System.out.println("提示訊息:  " + message);
			driver.findElement(By.className("btn-primary")).click();
			return;
		} catch (Exception e) {

		}
	}
	

	public ArrayList<String> getResult(){
		ArrayList<String> result = new ArrayList<String>();
		StringBuffer headLine = new StringBuffer();
		WebElement theadtr = driver.findElement(By.tagName("thead")).findElement(By.tagName("tr"));
		List<WebElement> ths = theadtr.findElements(By.tagName("th"));
		for (WebElement th : ths) {
			String tmp = Strs.fixedSizeWithBlank(th.getText(), 24);
			headLine.append(tmp);
			System.out.print(tmp);
		}
		result.add(headLine.toString());
		System.out.println();
		String clstring;
		do {
			List<WebElement> trs = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
			for (WebElement tr : trs) {
				StringBuffer bodyLine = new StringBuffer();
				List<WebElement> tds = tr.findElements(By.tagName("td"));
				for (WebElement td : tds) {
					String tmp2 = Strs.fixedSizeWithBlank(td.getText(), 24);
					bodyLine.append(tmp2);
					System.out.print(tmp2);
				}
				result.add(bodyLine.toString());
				System.out.println();
			}
			
			WebElement next = driver.findElement(By.id("tblist_next")); 
			clstring =  next.getAttribute("class");
			if(clstring.contains("disabled")) {
				break;
			}
			else {
				next.findElement(By.tagName("a")).click();;
			}
		}
		while(true);
		return result;
	} 
	
	public void saveText(String path, String name) {
		/* 寫入Txt檔案 */
		ArrayList<String> text = getResult();
		File writename = new File(path + "\\" + name + ".txt");
		try {
			writename.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		} // 建立新檔案
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(writename));
			for (String tmp : text) {
				out.write(tmp);
				out.write("\r\n");
			}
			out.flush(); // 把快取區內容壓入檔案
			out.close(); // 最後記得關閉檔案
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void saveText() {
		saveText("C:\\text", "output");
	}


	public WebDriver getDriver() {
		return driver;
	}


	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}


	public WebElement getFunction() {
		return function;
	}


	public void setFunction(WebElement function) {
		this.function = function;
	}


	public WebElement getAction() {
		return action;
	}


	public void setAction(WebElement action) {
		this.action = action;
	}


	public WebElement getUserId() {
		return userId;
	}


	public void setUserId(WebElement userId) {
		this.userId = userId;
	}


	public WebElement getLogBeginDate() {
		return logBeginDate;
	}


	public void setLogBeginDate(WebElement logBeginDate) {
		this.logBeginDate = logBeginDate;
	}


	public WebElement getLogEndDate() {
		return logEndDate;
	}


	public void setLogEndDate(WebElement logEndDate) {
		this.logEndDate = logEndDate;
	}


	public WebElement getForm() {
		return form;
	}


	public void setForm(WebElement form) {
		this.form = form;
	}


	public WebElement getSearchButton() {
		return searchButton;
	}


	public void setSearchButton(WebElement searchButton) {
		this.searchButton = searchButton;
	}


	public WebElement getClearButton() {
		return clearButton;
	}


	public void setClearButton(WebElement clearButton) {
		this.clearButton = clearButton;
	}


	public WebElement getPrintExcelButton() {
		return printExcelButton;
	}


	public void setPrintExcelButton(WebElement printExcelButton) {
		this.printExcelButton = printExcelButton;
	}


	public WebElement getPrintPdfButton() {
		return printPdfButton;
	}


	public void setPrintPdfButton(WebElement printPdfButton) {
		this.printPdfButton = printPdfButton;
	}


	public Select getFunctionTypeSelect() {
		return new Select(this.function);
	}

	
}
