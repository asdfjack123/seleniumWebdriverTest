package jack.seleniumWebdriverTest;

import java.io.*;
import java.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import Tests.Test1;
import utils.Strs;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		Test1 t = new Test1();
		String function = "登入/登出";
		String logBeginDate = "2020/06/10";
		String logEndDate = "2020/06/10";
		t.put3Data(function, logBeginDate, logEndDate);
		
		
		
//		t.putData("logBeginDate", "2020/03/11");
		
		t.search();
//		t.printExcel();
//		t.saveText();
//		t.quit();
	}
}
