package masterdatas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class loginproperty {


	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	LocalDate localDate = LocalDate.now();
	String date = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern( "yyyy-MM-dd" ));

	WebDriver driver;
	By Act_no=By.name("GOLD$_AccountNbr_Input");
	By username=By.name("GOLD$_Username_Input");
	By password=By.name("GOLD$_Password_Input");
	By button=By.name("GOLD$_Action(Login)");
	By menu_item=By.xpath("//ul[contains(@id,'menu_items')]//h1");
	By create_SearchAccount=By.linkText("Create / Search Account");	
	By hotel_number=By.name("GOLD$_SearchHotelNumber");
	By refresh=By.name("GOLD$_Action(Refresh)");
	By list_row0=By.id("listrow0");
	By trigger=By.id("trigger");
	By ppty_log=By.xpath("//*[text()='Property Login']");
	By ppty_details= By.xpath("//*[contains(text(),'Property Details')]");
	By meal_pla=By.xpath(" //*[contains(text(),'Meal Plans')]");
	By list_row1=By.xpath("//*[contains(@id,'listrow1')]");
	By save=By.name("GOLD$_Action(Save)");
	By new_create=By.name("GOLD$_Action(New)");
	By save_ppty=By.name("GOLD$_Action(SaveProperties)");
	By Edit_ppty=By.xpath("//div[contains(@id,'pome11')]//span[contains(text(),'Edit properties')]");
	By rateplan_mana=By.xpath(" //*[contains(text(),'Rate Plan Manager')]");
	By edit=By.xpath("//div[contains(@id,'pome1')]//span[contains(text(),'Edit')]");




	public loginproperty(WebDriver driver){
		this.driver=driver;
	}

	public void m_login() throws IOException, InterruptedException{
		FileInputStream inp=new FileInputStream("C:/Users/baven/workspace/HBSI/src/config/file.properties");
		Properties prop=new Properties();

		prop.load(inp);
		driver.findElement(Act_no).clear();
		driver.findElement(username).clear();
		driver.findElement(password).clear();

		driver.findElement(Act_no).sendKeys(prop.getProperty("uatAcct"));
		driver.findElement(username).sendKeys(prop.getProperty("uatuser"));
		driver.findElement(password).sendKeys(prop.getProperty("uatpass"));
		//driver.findElement(password).sendKeys(prop.getProperty("hotpass"));

		driver.findElement(button).click();
		//Thread.sleep(1000);
		//driver.navigate().refresh();
		driver.switchTo().frame(0);
		driver.switchTo().frame(1);  
		driver.findElement(trigger).click();
		driver.findElement(menu_item).click();


	}

	public void pptylogin() throws IOException, InterruptedException{
		FileInputStream inp=new FileInputStream("C:/Users/baven/workspace/HBSI/src/config/file.properties");
		Properties prop=new Properties();

		prop.load(inp);
		
		driver.findElement(create_SearchAccount).click();
		driver.findElement(hotel_number).sendKeys(prop.getProperty("uatppty"));
		Thread.sleep(1000);

		driver.findElement(refresh).click();

		Thread.sleep(1000);

		driver.findElement(list_row0).click();
		driver.findElement(ppty_log).click();

		System.out.println("Successfully loaded Property ");
		String homepage=driver.getWindowHandle();
		System.out.println(homepage);

		new WebDriverWait(driver,5).until(ExpectedConditions.numberOfWindowsToBe(2));
		Set<String> windows=driver.getWindowHandles();

		System.out.println(windows.size());

		Iterator iterator=windows.iterator();

		String currentWindow;

		while(iterator.hasNext()){
			currentWindow=iterator.next().toString();
			System.out.println(currentWindow);
			driver.manage().window().maximize();
			if(!currentWindow.equals(homepage)){

				driver.switchTo().window(currentWindow);
				driver.manage().window().maximize();
			}
		}


	}

	public void meal_plan() throws InterruptedException{

    		driver.switchTo().frame(0);
    		driver.switchTo().frame(1); 
    		driver.findElement(trigger).click();

    		driver.findElement(ppty_details).click();
    		driver.findElement(meal_pla).click();
    		Thread.sleep(1000);
    		String mealco= driver.findElement(list_row1).getText();
    		System.out.println("The current meal plan is: "+mealco);
    		//System.out.println("Meal plan code already present: "+mealco);

    		if(mealco.contains("Breakfast")){
    			driver.findElement(list_row1).click();
    			//Thread.sleep(1000);
    			driver.findElement(edit).click();
    			driver.findElement(save).click();
    			driver.findElement(refresh).click();
    			System.out.println("Meal code successfully Edited");

    		}
    		else{

    			driver.findElement(new_create).click();
    			driver.findElement(By.name("GOLD$_InternalTitle")).sendKeys("Breakfast");
    			driver.findElement(By.name("GOLD$_Caption2")).sendKeys("Breakfast");
    			driver.findElement(save).click();

    			String isFormAlreadySubmitted = driver.findElement(By.xpath("//div[@class='statusbar']")).getText();
    			System.out.println("Mealplan Capture Text is: "+isFormAlreadySubmitted);

    			if (isFormAlreadySubmitted.equals("Successfully saved.")){
    				System.out.println("Mealplan successfully Created");
    			}
    			else{
    				System.out.println("Meal plan Already Created and the exection is Failed");
    			}

    			driver.findElement(refresh).click();	
    			Thread.sleep(1200);
    		}

            //Thread.sleep(5000);
        

    
		//Thread.sleep(1000);
		

	}

	public void rateplan() throws InterruptedException{


		driver.findElement(trigger).click();
		driver.findElement(By.xpath("//*[contains(text(),'Workbench')]")).click();

		driver.findElement(rateplan_mana).click();
		Thread.sleep(1000);

		String Rateco= driver.findElement(By.xpath("//*[contains(@id,'listrow11')]")).getText();
		System.out.println("Rate code already present: "+Rateco);

		if(Rateco.contains("SEL-TEST")){

			driver.findElement(By.xpath("//*[contains(@id,'listrow11')]")).click();
			//Thread.sleep(1000);
			driver.findElement(Edit_ppty).click();
			driver.findElement(save_ppty).click();
			driver.findElement(refresh).click();
			System.out.println("Rate code successfully Created");
		}
		else{
			driver.findElement(new_create).click();
			driver.findElement(By.name("GOLD$_Code")).sendKeys("SEL-TEST");
			driver.findElement(By.name("GOLD$_InternalCaption")).sendKeys("SEL-TEST");	
			driver.findElement(By.name("GOLD$_Public")).click();
			driver.findElement(By.name("GOLD$_Active")).click();
			driver.findElement(By.name("GOLD$_Caption2")).sendKeys("SEL-TEST");
			driver.findElement(By.name("GOLD$_Description2")).sendKeys("SEL-TEST");
			driver.findElement(save_ppty).click();
			driver.findElement(refresh).click();
			System.out.println("New Rate code successfully Created");
			Thread.sleep(1500);
		}


	}

	public void tax_create() throws InterruptedException{



		driver.findElement(trigger).click();
		driver.findElement(By.xpath("//*[contains(text(),'Extra')]")).click();
		driver.findElement(By.xpath(" //*[contains(text(),'Taxes')]")).click();
		Thread.sleep(1000);



		String tax=driver.findElement(list_row1).getText();
		System.out.println("Tax code already present: "+tax);

		if(tax.contains("TAX")){

			driver.findElement(list_row1).click();
			driver.findElement(edit).click();
			driver.findElement(By.name("GOLD$_InternalTitle")).clear();
			driver.findElement(By.name("GOLD$_TaxRate")).clear();
			driver.findElement(By.name("GOLD$_Caption2")).clear();

			driver.findElement(By.name("GOLD$_InternalTitle")).sendKeys("TAX");
			driver.findElement(By.name("GOLD$_TaxRate")).sendKeys("10");
			driver.findElement(By.name("GOLD$_Caption2")).sendKeys("TAX");
			driver.findElement(save).click();
			driver.findElement(refresh).click();	

		}
		else{


			driver.findElement(new_create).click();
			driver.findElement(By.name("GOLD$_InternalTitle")).sendKeys("TAX");
			driver.findElement(By.name("GOLD$_TaxRate")).sendKeys("10");
			driver.findElement(By.name("GOLD$_Caption2")).sendKeys("TAX");
			driver.findElement(save).click();
			driver.findElement(refresh).click();
		}


	}

	public void occup_policy() throws InterruptedException{


		driver.findElement(trigger).click();
		driver.findElement(By.xpath("//*[contains(text(),'Workbench')]")).click();
		driver.findElement(By.xpath("//*[contains(text(), 'Policy Manager')]")).click(); 

		// driver.findElement(By.name("GOLD$_ShowMonth")).sendKeys("June 2021");
		driver.findElement(refresh).click();
		Thread.sleep(1000);

		driver.findElement(By.xpath("//*[@title='Occupancy Restriction']")).click();
		driver.findElement(By.name("GOLD$_occupancypolicyFrom")).sendKeys(dtf.format(localDate));
		driver.findElement(By.name("GOLD$_occupancypolicyUntil")).sendKeys(date);

		Select ratecode=new Select (driver.findElement(By.name("GOLD$_occupancypolicyRPId")));

		ratecode.selectByVisibleText("SEL-TEST");  
		//ratecode.selectByIndex(2);

		Select roomcode=new Select (driver.findElement(By.name("GOLD$_occupancypolicyRoomtypeId")));
		roomcode.selectByVisibleText("DELUXE");  
		//roomcode.selectByIndex(1);

		driver.findElement(By.name("GOLD$_occupancypolicyMaxAdultsWithNoChildren")).sendKeys("1");
		driver.findElement(By.name("GOLD$_Action(Saveoccupancypolicy)")).click();
		System.out.println("Successfully set Restriction in Policy Manager Screen");

	}


	public void roomcreate() throws InterruptedException{




		driver.findElement(trigger).click();

		driver.findElement(By.xpath("//*[contains(text(),'Property Details')]")).click();
		driver.findElement(By.xpath(" //*[contains(text(),'Room Types')]")).click();

		//driver.findElement(By.id("listrow7"));
		//String room=driver.findElement(By.xpath("//*[@id='listrow7']//td[1][contains(@title,'ROOM10')]")).getText();

		String room=driver.findElement(By.xpath("//*[contains(@title,'DELUXE')]")).getText();
		//System.out.println("Room found out is : "+room);

		if (room.contains("DELUXE")) {

			System.out.println("Room code already present");
			driver.findElement(By.xpath("//*[contains(@title,'DELUXE')]")).click();
			/*String tes=driver.findElement(By.xpath("//*[contains(@id,'pome5')]")).getText();
System.out.println("Successfully contains toom title as Room10 "+tes); */

			//driver.findElement(By.xpath("//*[@id='pome5']//span[text()='Delete']")).click();
			driver.findElement(By.xpath("//*[@id='pome0']//span[text()='Edit properties']")).click();

			driver.findElement(save_ppty).click();
			driver.findElement(refresh).click();
		}
		else{
			//driver.findElement(By.xpath("//*[@id,'pome5']//*[contains(text(),'Delete')] ")).click();
			/*Alert alert=driver.switchTo().alert();
alert.accept();
System.out.println("Room code successfully deleted:"+room);
			 */
			Thread.sleep(1000);

			driver.findElement(refresh).click();

			driver.findElement(new_create).click();
			driver.findElement(By.name("GOLD$_InternalCaption")).sendKeys("DELUXE");
			driver.findElement(By.name("GOLD$_MinPersons")).sendKeys("1");
			driver.findElement(By.name("GOLD$_MaxPersons")).sendKeys("5");
			driver.findElement(By.name("GOLD$_Caption2")).sendKeys("DELUXE");
			driver.findElement(save_ppty).click();
			System.out.println("Successfully created Room Type");
			driver.findElement(refresh).click();

		}

	}


	public void setrate() throws InterruptedException{

		driver.findElement(trigger).click();
		WebElement workbench1= driver.findElement(By.xpath("//*[contains(text(), 'Workbench')]"));
		workbench1.click();

		driver.findElement(By.xpath("//*[contains(text(), 'Rate Manager')]")).click(); 
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[contains(@title, 'Set Rate')]")).click();
		driver.findElement(By.name("GOLD$_SetRateFrom")).sendKeys(dtf.format(localDate));
		driver.findElement(By.name("GOLD$_SetRateUntil")).sendKeys(date);

		Select rate=new Select((driver.findElement(By.name("GOLD$_SetRateRateplanId"))));
		rate.selectByVisibleText("SEL-TEST");

		Select roomcode1=new Select (driver.findElement(By.name("GOLD$_SetRateRoomtypeId")));
		roomcode1.selectByVisibleText("DELUXE");

		driver.findElement(By.name("GOLD$_SetRateSellRate")).sendKeys("130");

		driver.findElement(By.name("GOLD$_SetRateNetRate")).sendKeys("100");

		driver.findElement(By.name("GOLD$_Action(SaveSetRate)")).click();


		System.out.println("Successfully set Rate in Rate Manager Screen");
		Thread.sleep(1000);


	}


	public void Inv_Set() throws InterruptedException{


		driver.findElement(trigger).click();
		driver.findElement(By.xpath("//*[contains(text(),'Workbench')]")).click();
		driver.findElement(By.xpath("//*[contains(text(), 'Inventory Manager')]")).click(); 
		// Thread.sleep(1000);
		driver.findElement(By.xpath("//*[contains(@title, 'Block')]")).click();



		driver.findElement(By.name("GOLD$_blockFrom")).sendKeys(dtf.format(localDate));
		//driver.findElement(By.name("GOLD$_blockFrom")).sendKeys("2021-06-03");
		driver.findElement(By.name("GOLD$_blockUntil")).sendKeys(date);

		Select Inv=new Select((driver.findElement(By.name("GOLD$_blockRateplanId"))));
		Inv.selectByVisibleText("SEL-TEST");

		String roblo=driver.findElement(By.xpath("//*[contains(text(),'DELUXE')]")).getText();
		System.out.println("Room code selected from inventory manager Screen is: "+roblo);

		Actions actions = new Actions(driver);
		actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		//driver.findElement(By.xpath("//*[@class='scrollbox']//*[@name='GOLD$_blockBlock53985Block'] ")).sendKeys("45");
		//driver.findElement(By.name("GOLD$_blockBlock53990Block")).sendKeys("45");GOLD$_blockBlock58329Block
		driver.findElement(By.name("GOLD$_blockBlock58329Block")).sendKeys("45");   

		Thread.sleep(1000);


		//  driver.findElement(By.name("GOLD$_blockBlock53980Block")).sendKeys("45");


		driver.findElement(By.name("GOLD$_Action(Saveblock)")).click();
		System.out.println("inventory Block Set Successfully");

		Thread.sleep(100);

	}

}
