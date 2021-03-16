package Excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Exceltest {

	WebDriver driver;

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	LocalDate localDate = LocalDate.now();



	@BeforeClass
	public void IEopen() throws Exception{

		FileInputStream inp=new FileInputStream("C:/Users/baven/workspace/HBSI/src/config/file.properties");
		Properties prop=new Properties();

		prop.load(inp);
		System.setProperty("webdriver.ie.driver", "C:/Users/baven/OneDrive/Documents/Testing/eclipse/IEDriverServer.exe");

		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability("requireWindowFocus", true);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		driver=new InternetExplorerDriver();
		driver.get(prop.getProperty("hotbrow"));
		//driver.get("https://hotfix.hbsconnect.com");
		driver.manage().window().maximize();
		driver.findElement(By.name("GOLD$_AccountNbr_Input")).clear();
		driver.findElement(By.name("GOLD$_Username_Input")).clear();
		driver.findElement(By.name("GOLD$_Password_Input")).clear();

		FileInputStream inp1=new FileInputStream("C:/Users/baven/workspace/HBSI/src/config/file.properties");
		Properties prop1=new Properties();

		prop1.load(inp1);

		driver.findElement(By.name("GOLD$_AccountNbr_Input")).sendKeys(prop1.getProperty("hotfacct"));
		driver.findElement(By.name("GOLD$_Username_Input")).sendKeys(prop1.getProperty("hotuser"));
		driver.findElement(By.name("GOLD$_Password_Input")).sendKeys(prop1.getProperty("hotpass"));
		driver.findElement(By.name("GOLD$_Action(Login)")).click();
		System.out.println("Successfully loginned");
		




	}


	@DataProvider
	public Object[][] getData(){
		Object data [][]=ReadExcelfile.getTestdata("Sheet1");
		return data;

	}


	@Test(priority=0, dataProvider="getData")
	public void login(String request) throws InterruptedException{
		driver.navigate().refresh();
		driver.switchTo().frame(0);
		driver.switchTo().frame(1); 
		driver.findElement(By.id("trigger")).click();
		driver.findElement(By.xpath("//*[contains(text(),'Interfaces')]")).click();
		Actions actions = new Actions(driver);
		WebElement elementLocator =driver.findElement(By.xpath("//*[contains(text(),'Test Interface')]"));
		actions.contextClick(elementLocator).perform();
		Actions MoveDownSubmenu = new Actions(driver);
		MoveDownSubmenu.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.RETURN).build().perform();
		String mainWindow=driver.getWindowHandle();
		System.out.println("Main window is: "+mainWindow);
		// It returns no. of windows opened by WebDriver and will return Set of Strings
		Set<String> set =driver.getWindowHandles();
		// Using Iterator to iterate with in windows
		Iterator<String> itr= set.iterator();
		while(itr.hasNext()){

			String childWindow=itr.next();
			if(!mainWindow.equals(childWindow)){
				System.out.println("Child window is: "+childWindow);
				driver.switchTo().window(childWindow);
				driver.manage().window().maximize();

				driver.findElement(By.name("GOLD$_RequestData")).sendKeys(request);
				driver.findElement(By.name("GOLD$_Action(Send)")).click();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				Thread.sleep(1000);
				driver.close();
			
				driver.switchTo().window(mainWindow);
				TlogVerify();
				Thread.sleep(1000);
				login(request);
			}

		}


	}

	@Test(priority=1)
	public void TlogVerify() throws InterruptedException{
		
		driver.navigate().refresh();
		driver.switchTo().frame(0);
		driver.switchTo().frame(1);
		
		driver.findElement(By.id("trigger")).click();
		driver.findElement(By.xpath("//*[contains(text(),'Interfaces')]")).click();
		Actions act1 = new Actions(driver);
		WebElement element =driver.findElement(By.xpath("//*[contains(text(),'Interface Administration')]"));
		act1.contextClick(element).perform();
		Actions Moved = new Actions(driver);
		Moved.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.RETURN).build().perform();


		String MainWindow = driver.getWindowHandle();
		String Childwindow = MainWindow;
		String ChildWindow1 = Childwindow;
		String ChildWindow2 = ChildWindow1;

		for (String Handle : driver.getWindowHandles())
		{
			if (!Childwindow.equals(Handle))
			{
				Childwindow = Handle;
				driver.switchTo().window(Childwindow);
				//Thread.sleep(5000);
				System.out.println("Child window is: "+Childwindow);

				driver.manage().window().maximize();


				Select tlog=new Select(driver.findElement(By.name("Interface")));
				tlog.selectByValue("TransferLogs");
				driver.findElement(By.name("GOLD$_Action(Refresh)")).click();
				driver.findElement(By.name("GOLD$_SearchProperty")).sendKeys("48771");
				Select DC=new Select(driver.findElement(By.name("GOLD$_SearchInterfaceTypeId")));
				DC.selectByValue("48");
				driver.findElement(By.name("GOLD$_SearchDateFrom")).clear();
				driver.findElement(By.name("GOLD$_SearchDateUntil")).clear();

				driver.findElement(By.name("GOLD$_SearchDateFrom")).sendKeys(dtf.format(localDate));
				driver.findElement(By.name("GOLD$_SearchDateUntil")).sendKeys(dtf.format(localDate));
				driver.findElement(By.name("GOLD$_Action(TransferLogsView)")).click();

				driver.findElement(By.id("listrow0")).click();

				//Actions acts = new Actions(driver);
				//WebElement elemecator =
						driver.findElement(By.xpath("//*[@id='pome0']//span[text()='View Internal Log']")).click();
				//acts.contextClick(elemecator).perform();
/*
				Actions Move = new Actions(driver);
				Move.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.RETURN).build().perform();
*/
				
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");  
				String log=driver.findElement(By.name("Log")).getText();
				System.out.println("The Search response is: "+log);
				String searchtext="RatePlanCode" ;
				if(log.contains(searchtext)){
					System.out.println("Search is PASS");
				}else{
					System.out.println("Search is FAIL");
				}
				Thread.sleep(1000);
				driver.close();      
			}

		}
		driver.switchTo().window(MainWindow);
		//driver.navigate().refresh();
		/*driver.switchTo().frame(0);
		driver.switchTo().frame(1); */ 

			}

		

/*
		for (String Handle1 : driver.getWindowHandles())
		{
			if (!ChildWindow1.equals(Handle1))
			{
				ChildWindow1 = Handle1;
				driver.switchTo().window(ChildWindow1);
				//Thread.sleep(1000);
				driver.manage().window().maximize();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");  
				String log=driver.findElement(By.name("Log")).getText();
				System.out.println("The Search response is: "+log);
				String searchtext="RatePlanCode" ;
				if(log.contains(searchtext)){
					System.out.println("Search is PASS");
				}else{
					System.out.println("Search is FAIL");
				}
				Thread.sleep(1000);
				driver.close();      
			}

		}  */
              
		//driver.switchTo().window(Childwindow);
		// driver.close();
		// driver.switchTo().window(MainWindow);
		// driver.navigate().refresh();




}
