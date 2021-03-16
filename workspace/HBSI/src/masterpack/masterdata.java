package masterpack;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.internal.Configuration;

import masterdatas.loginproperty;

@Listeners(masterpack.listenerclass.class)

public class masterdata {



	WebDriver driver;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	LocalDate localDate = LocalDate.now();
	String date = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern( "yyyy-MM-dd" ));


	

	@Test(priority=0,groups= {"sanity","regression"})	
	public void IEopen() throws Exception{
		FileInputStream inp=new FileInputStream("C:/Users/baven/workspace/HBSI/src/config/file.properties");
		Properties prop=new Properties();

		prop.load(inp);
		System.setProperty("webdriver.ie.driver", "C:/Users/baven/OneDrive/Documents/Testing/eclipse/IEDriverServer.exe");

		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability("requireWindowFocus", true);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		driver=new InternetExplorerDriver();
		driver.get(prop.getProperty("uatbrow"));
		//driver.get("https://hotfix.hbsconnect.com");
		driver.manage().window().maximize();

		loginproperty login=new loginproperty(driver);

		login.m_login();
		login.pptylogin();


	}


	@Test(priority=4,groups={"sanity","regression"})
	public void RoomTypeCreation() throws Exception{
		loginproperty login=new loginproperty(driver);
		login.roomcreate();

	}

	@Test(priority=3,groups={"sanity","regression"})	
	public void rateplancreation() throws Exception{
		loginproperty login=new loginproperty(driver);
		login.rateplan();

	}


	@Test(priority=6,groups={"sanity","regression"})
	public void rateset() throws Exception{
		loginproperty login=new loginproperty(driver);
		login.setrate();

	}

	@Test(priority=5,groups={"sanity","regression"})
	public void Inventoryset() throws InterruptedException{
		loginproperty login=new loginproperty(driver);
		login.Inv_Set();

	}


	@Test(priority=7,groups={"regression"})
	public void occupancypolicyset() throws Exception{
		loginproperty login=new loginproperty(driver);
		login.occup_policy();
	}	

	@Test(priority=1,groups={"sanity","regression"})
	public void mealplancreation() throws Exception{
		loginproperty login=new loginproperty(driver);
		login.meal_plan();

	}	

	@Test(priority=2,groups={"sanity"})
	public void taxcreate() throws Exception{
		loginproperty login=new loginproperty(driver);
		login.tax_create();

	}
}

