package carReg.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.CarDetailsPage;
import utils.ReadTextFile;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

public class carValuationTest {
	WebDriver driver;
	CarDetailsPage carPage;

	String baseURL = "https://motor.confused.com/CarDetails?nt=1";

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get(baseURL);
		carPage = new CarDetailsPage(driver);
	}

	@Test
	public void testCarValuation() throws IOException, InterruptedException {
		String[] carReg = ReadTextFile.readCarRegFromTextFile("src/test/resources/car_input - V6.txt");
		Map<String, String> carOutPutDetails = ReadTextFile.carOutputDetails("src/test/resources/car_output - V6.txt");

		for (String reg : carReg) {
			System.out.println("Testing car registration: " + reg);
			carPage.enterCarRegistration(reg);
			carPage.clickFindCarButton();
			Thread.sleep(500);		

			if (carPage.isVehicleSummaryNotDisplayed()) {
				String actualCarReg = carPage.getCarReg();
				String actualCarMake = carPage.getCarMake();
				String actualCarModel = carPage.getCarModel();
				String actualCarYear = carPage.getCarYear();
				System.out.println("Vehicle summary is displayed for: " + actualCarReg);

				String expectedDetails = carOutPutDetails.get(actualCarReg).trim();
				String actualDetails = actualCarMake.trim() + ", " + actualCarModel.trim() + ", "
						+ actualCarYear.trim();

				System.out.println("Expected: '" + expectedDetails + "'");
				System.out.println("Actual:   '" + actualDetails + "'");
				Assert.assertTrue(expectedDetails.contains(expectedDetails));
				carPage.clickOnChangeVehicleLink();

			} else {
			
				String carRegNotFound = carPage.getCarRegNotFound();
				String expectedErrorMsg = "We couldn't find a vehicle with the registration ";
				Assert.assertTrue(carRegNotFound.contains(expectedErrorMsg));
				carPage.enterCarRegistration(reg);
				carPage.clickFindCarButton();
				
			}
			
		}
	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
