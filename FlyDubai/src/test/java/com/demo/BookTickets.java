package com.demo;

import java.time.Duration;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v122.network.Network;
import org.openqa.selenium.devtools.v122.network.model.RequestId;
import org.openqa.selenium.devtools.v122.network.model.Response;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BookTickets {

	public static void main(String[] args) throws InterruptedException {

		String baseURL = "https://qa1-flydubai.np.flydubai.com/en/";
		String bookFlight = "//span[@class='widget-desktop-show'][normalize-space()='Book a flight']";
		String expectedTextOnHomeScreen = "Book a flight";
		String acceptAll_xpath = "//button[normalize-space()='Accept All']";
		String origin_id = "airport-origin";
		String destination_id = "airport-destination";
		String departureDate_id = "dateRangeWrapper";
		String flyPasanger_xpath = "//span[@class='bwPassenger']";
		String cabinClass_class = "economy";
		String search_xpath = "//div[@class='view-booking-btn search-book-grid d-xl-block d-lg-block d-md-block d-sm-none d-none']//input[@value='Search']";
		String selMonthsDep_xpath = "//section[@class='lightpick lightpick--2-columns']//section[1]//header[1]//div[1]//select[1]";
		String dateDeparture_xpath = "//*[contains(text(),15)]";
		String dateReturn_xpath = "//div[@class='lightpick__day is-available'][normalize-space()='5']";
		String flightResultsPage_xpath = "//label[contains(text(),'SELECT DEPARTING FLIGHT')]";
		String expectdText = "SELECT DEPARTING FLIGHT";
		String oneFareBrand = "//*[@id=\"14320815\"]/div[5]";
		String lowestFareClass = "//div[@class='Lite fare-faretype']//span[@id='span']";
		String selectReturnFlight = "//div[@id='13906814']";
		String selectReturnFare = "//*[@id=\"desktopHeader\"]/div[2]/div/div[1]/fz-desktop-availability-list/div/div/div/div/div[1]/div/div/div/div[3]/div[1]/div/fz-fare-brand-column/div/div[3]/div[2]";
		String totalPrice_xpath = "//div[@class='totalNpr']//div[@class='currencyAmount ng-star-inserted']";
		String extraPage = "//label[normalize-space()='Select extras']";
		String expectedScreenText = "Select extras";
		String selectFlighJdate = "//div[@class='calRectangle-selected Caltext-selected']//p[@class='calendar-day-class ng-star-inserted'][normalize-space()='Wednesday']";
		String select20Kg = "//label[normalize-space()='20 KG']";
		String continueToMeal = "//span[contains(text(),'Continue to meals')]";
		String continue_xpath = "//span[normalize-space()='Continue to seats']//span[@id='span']";
		String passangeFirstName = "//input[@id='First_Name']";
		String passangeLastName = "//input[@id='Last_Name']";
		String passangeEmail = "//input[@id='Email_Address']";
		String passangeGender = "//*[contains(@id,'Male')]";
		String passangeMobile = "//input[@id='Mobile_Number']";
		String code = "//div[@class='mat-form-field-flex ng-tns-c24-67']";
		String countryCode = "//span[contains(text(),'+91')]";
		String reviewBookingButton = "//div[@class='continuePayment']//button[@type='button']";
		String continueToPassangerDetails = "//span[normalize-space()='Continue to passenger details']//span[@id='span']";
		String reviewBookingPagePath = "//label[normalize-space()='Review your booking details']";
		String expectedPageReview = "Review your booking details";
		String paymentCurrancy = "//div[@class='leftbox__title']";
		String expectPaymentCurrencyPage = "Select payment currency";
		String paymentTotalOnSceen = "//span[@class=\"font20 font18 skeleton-wrapper-inline\"]";
		String continueToPayment = "//button[@class='mat-button mat-button-base reviewPagebutton']";

		WebDriverManager.chromedriver().setup();
		;
//		System.setProperty("webdriver.chrome.driver",
//				"C:/Users/Office/eclipse-workspace/PyWorkSpace/FlyDubai/src/test/resources/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();

		System.out.println("Step 1: Click on URL will open the home page");
		driver.get(baseURL);
		try {
			WebElement acceptCookies = driver.findElement(By.xpath(acceptAll_xpath));
			acceptCookies.click();
		} catch (Exception e) {
			Assert.fail("Failed to Find Accept All");
		}

		driver.manage().window().maximize();

		String actualTextonHomeScreen = null;
		try {
			WebElement textonHomeScreen = driver.findElement(By.xpath(bookFlight));
			;
			actualTextonHomeScreen = textonHomeScreen.getText();
		} catch (Exception e) {
			Assert.fail("Failed to Find Book a flight");
		}

		System.out.println("Expected text on Home scren: " + expectedTextOnHomeScreen);
		System.out.println("Actual text on Home scren: " + actualTextonHomeScreen);
		Assert.assertEquals(actualTextonHomeScreen, expectedTextOnHomeScreen, "Failed to navigated on Home Screen");
		DevTools devTool = driver.getDevTools();
		devTool.createSession();
		devTool.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		final RequestId[] requestId = new RequestId[1];

		devTool.addListener(Network.responseReceived(), responseConsumer -> {
			Response response = responseConsumer.getResponse();
			requestId[0] = responseConsumer.getRequestId();
			if (response.getUrl().contains("qa1-flights2.np.flydubai.com/api/flights/7")) {
				System.out
						.println("API Response Status: " + response.getStatus() + " " + "\nURL: " + response.getUrl());
				String responseBody = devTool.send(Network.getResponseBody(requestId[0])).getBody();
				System.out.println("API Response: \n" + responseBody);
			}

		});

		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(30000));

		System.out.println("Step 2: Enter the route, date and no of passengers");
		driver.findElement(By.id(origin_id)).sendKeys("DXB", Keys.RETURN);
		driver.findElement(By.id(destination_id)).sendKeys("MCT", Keys.RETURN);
		driver.findElement(By.id(departureDate_id)).click();
		Select sel = new Select(driver.findElement(By.xpath(selMonthsDep_xpath)));
		sel.selectByValue("4");
		driver.executeScript("window.scrollBy(0,400)");
		Thread.sleep(5000);
		WebElement datedep = driver.findElement(By.xpath(dateDeparture_xpath));
		datedep.click();
		WebElement dateReturn = driver.findElement(By.xpath(dateReturn_xpath));
		Actions actions = new Actions(driver);
		actions.moveToElement(dateReturn).click(dateReturn).build().perform();
		driver.executeScript("window.scrollBy(0,500)");
		WebElement flyPasanger = driver.findElement(By.xpath(flyPasanger_xpath));
		flyPasanger.click();
		driver.executeScript("window.scrollBy(0,500)");
		Thread.sleep(5000);
		WebElement cabinClass = driver.findElement(By.className(cabinClass_class));
		cabinClass.click();
		System.out.println("Step 3: Click on Search button");
		driver.findElement(By.xpath(search_xpath)).click();
		System.out.println("Step 4: System redirects to flight results page");
		WebElement actualScreen = driver.findElement(By.xpath(flightResultsPage_xpath));
		String actualText = actualScreen.getText();
		System.out.println("Expected text on flight results page:  " + expectdText);
		System.out.println("Actual text on flight results page: " + actualText);
		Assert.assertEquals(actualText, expectdText, "Failed to Navigate on flight results page");
		System.out.println("Step 5: Click on one of the flight tab which expands the fare brand display");
		WebElement selectFlighDet = driver.findElement(By.xpath(selectFlighJdate));
		selectFlighDet.click();

		System.out.println("Step 6: Choose one Fare brand from the results page.");
		WebElement oneFareBrandTicket = driver.findElement(By.xpath(oneFareBrand));
		oneFareBrandTicket.click();
		WebElement selectLowestFareClass = driver.findElement(By.xpath(lowestFareClass));
		selectLowestFareClass.click();
		System.out.println("Step 7: Select the onward and return flights ");

		driver.executeScript("window.scrollBy(0,550)");
		Thread.sleep(5000);
		WebElement selectReturnFlighDetail = driver.findElement(By.xpath(selectReturnFlight));
		selectReturnFlighDetail.click();
		driver.executeScript("window.scrollBy(0,550)");
		Thread.sleep(5000);
		WebElement selectReturnFareDetail = driver.findElement(By.xpath(selectReturnFare));
		selectReturnFareDetail.click();

		System.out.println("Step 8: On selection of Inbound flights, system redirects to Extras page");
		String actualScreenOnNavigation = driver.findElement(By.xpath(extraPage)).getText();
		System.out.println("Expected Text on Extra screen: " + expectedScreenText);
		System.out.println("Actual Text on Extra screen: " + actualScreenOnNavigation);

		Assert.assertEquals(actualScreenOnNavigation, expectedScreenText, "Failed to Navigate on Select extras screen");
		driver.executeScript("window.scrollBy(0,400)");
		Thread.sleep(5000);

		System.out.println("Step 9: Choose an additional baggage of 10KG to the included items");
		WebElement add20Kg = driver.findElement(By.xpath(select20Kg));
		add20Kg.click();

		String totalPrice = driver.findElement(By.xpath(totalPrice_xpath)).getText();

		System.out.println(
				"Step 10: Click on ï¿½Continue to passenger detailsï¿½ hyperlink will redirect to Passenger details page");

		WebElement continue_xpath_1 = driver.findElement(By.xpath(continue_xpath));
		continue_xpath_1.click();

		driver.executeScript("window.scrollBy(0,400)");
		Thread.sleep(5000);
		WebElement continue_xpath_2 = driver.findElement(By.xpath(continueToMeal));
		continue_xpath_2.click();

		WebElement continue_xpath_3 = driver.findElement(By.xpath(continueToPassangerDetails));
		continue_xpath_3.click();

		System.out.println("Step 11: Enter passenger information and click on ï¿½Review Bookingï¿½ button");
		WebElement passangeDetails = driver.findElement(By.xpath(passangeFirstName));
		passangeDetails.sendKeys("Varunendra");

		driver.findElement(By.xpath(passangeLastName)).sendKeys("Mishra");
		driver.findElement(By.xpath(passangeEmail)).sendKeys("emailvarunendra@gmail.com");
		driver.findElement(By.xpath(passangeGender)).click();
		driver.findElement(By.xpath(code)).click();
		driver.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath(countryCode)));

		WebElement countryCodes = driver.findElement(By.xpath(countryCode));
		countryCodes.click();

		driver.findElement(By.xpath(passangeMobile)).sendKeys("9891945353");
		driver.executeScript("window.scrollBy(0,500)");
		Thread.sleep(5000);

		WebElement reviewBookingBtn = driver.findElement(By.xpath(reviewBookingButton));
		reviewBookingBtn.click();

		System.out.println("Step 12: System navigates to ï¿½Review your booking detailsï¿½ page");
		String actualPageReview = null;
		WebElement actualPageRevi = driver.findElement(By.xpath(reviewBookingPagePath));
		actualPageReview = actualPageRevi.getText();

		System.out.println("Expected text on Review your booking screen  :" + expectedPageReview);
		System.out.println("Actual text on Review your booking screen  :" + actualPageReview);

		Assert.assertEquals(actualPageReview, expectedPageReview, "Failed to navigate on Review your booking details");

		System.out.println("Step 13: Validate and confirm ");
		driver.executeScript("window.scrollBy(0,500)");
		Thread.sleep(7000);
		WebElement continueToPayment_Btn = driver.findElement(By.xpath(continueToPayment));
		continueToPayment_Btn.click();

		System.out.println(
				"Step 14: Add validation to confirm the fare and tax components displayed in the first page is the "
						+ "same displayed in the Review page");

		String actualPaymentCurrencyPage = null;
		WebElement actualPaymentCurPage = driver.findElement(By.xpath(paymentCurrancy));

		actualPaymentCurrencyPage = actualPaymentCurPage.getText();
		Assert.assertEquals(actualPaymentCurrencyPage, expectPaymentCurrencyPage,
				"Select payment currency is not displayed");
		String actualPaymentTotalOnSceen = driver.findElement(By.xpath(paymentTotalOnSceen)).getText();
		actualPaymentTotalOnSceen = actualPaymentTotalOnSceen.trim();
		totalPrice = totalPrice.trim();
		System.out.println("Actual Total Payment On Sceen: " + actualPaymentTotalOnSceen);
		System.out.println("TotalPrice on extras screen : " + totalPrice);
		Assert.assertEquals(actualPaymentTotalOnSceen, totalPrice, "Payment is not matching with Review screen");
		driver.quit();

	}

}
