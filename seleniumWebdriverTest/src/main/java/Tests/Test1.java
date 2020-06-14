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
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Strs;

public class Test1 {
	public void runTheTest() {
		System.setProperty("webdriver.chrome.driver", "D:\\chr\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://10.0.0.123/MegaImage/home/SSOLogin?lightId=12345678");

		WebElement s = driver.findElement(By.tagName("h5"));

		System.out.println("----------------------");
		System.out.println(s);
		System.out.println(s.getText());
		System.out.println("----------------------");

		// 點擊使用者紀錄查詢(id是SystemAdmin.TransactionLog)
		WebElement t = driver.findElement(By.partialLinkText("系統管理"));

		System.out.println("----------------------");
		System.out.println(t);
		System.out.println(t.getText());
		System.out.println("----------------------");

		t.click();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement href = driver.findElement(By.id("SystemAdmin.TransactionLog"));
		System.out.println("----------------------");
		System.out.println(href.getText());
		System.out.println(href.getAttribute("value"));
		System.out.println(href.getAttribute("href"));
		System.out.println(href);
		System.out.println("----------------------");

		href.click();

//		driver.get("http://10.0.0.123/MegaImage/management/logFunctionManagePage");

		List<WebElement> options = driver.findElements(By.tagName("option"));
		for (WebElement option : options) {
			String opt = option.getText();
//			String opt = option.getAttribute("text");
			System.out.println(opt);
//			if ("Query.Image".equals(option.getAttribute("value"))) {
//				option.click();
//				break;
//			}
			if ("登入/登出".equals(option.getAttribute("text"))) {
				option.click();
				break;
			}
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement logBeginDate = driver.findElement(By.id("logBeginDate"));
		logBeginDate.clear();
		logBeginDate.sendKeys("2020/06/11");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement logEndDate = driver.findElement(By.id("logEndDate"));
		logEndDate.clear();
		logEndDate.sendKeys("2020/06/12");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		List<WebElement> buttons = driver.findElements(By.tagName("button"));
		for (WebElement button : buttons) {
			if ("查詢".equals(button.getText())) {
				button.click();
				break;
			}
		}

		try {
			(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
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

		ArrayList<String> testResult = new ArrayList<String>();
		StringBuffer headLine = new StringBuffer();

		WebElement theadtr = driver.findElement(By.tagName("thead")).findElement(By.tagName("tr"));
		List<WebElement> ths = theadtr.findElements(By.tagName("th"));
		for (WebElement th : ths) {
			String tmp = Strs.fixedSizeWithBlank(th.getText(), 24);
			headLine.append(tmp);
			System.out.print(tmp);
		}

		testResult.add(headLine.toString());
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
				testResult.add(bodyLine.toString());
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
		
		saveText(testResult);
		// 關閉瀏覽器
		// driver.quit();
		
		System.out.println("over");

	}

	private void saveText(ArrayList<String> text, String path, String name) {
		/* 寫入Txt檔案 */

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

	private void saveText(ArrayList<String> text) {
		saveText(text, "D:\\text", "output");
	}

}
