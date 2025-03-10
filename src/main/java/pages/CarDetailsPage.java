package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CarDetailsPage {
	WebDriver driver;
	WebDriverWait wait;

	private By carRegInput = By.id("registration-number-input");
	private By findCarButton = By.id("find-vehicle-btn");
	private By carReg = By.cssSelector("div[class='panel'] p:nth-child(1) b");
	private By carMake = By.cssSelector("div[class='panel'] p:nth-child(2) b");
	private By carModel = By.cssSelector("div[class='panel'] p:nth-child(3) b");
	private By carYear = By.cssSelector("div[class='panel'] p:nth-child(5) b");
	private By carValue = By.id("carvalue-value");
	private By carRegNotFound = By.cssSelector("div[id='vehicle-error-container'] h3");
	private By changeVehicle = By.linkText("Change vehicle");
	private By vehicleSummary = By.cssSelector("div[class='panel']");

	public CarDetailsPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public void enterCarRegistration(String regNumber) {
		driver.findElement(carRegInput).clear();
		driver.findElement(carRegInput).sendKeys(regNumber);
	}

	public void clickFindCarButton() {
		driver.findElement(findCarButton).click();
	}

	public String getCarReg() {
		return driver.findElement(carReg).getText();
	}

	public String getCarMake() {
		return driver.findElement(carMake).getText();
	}

	public String getCarModel() {
		return driver.findElement(carModel).getText();
	}

	public String getCarYear() {
		return driver.findElement(carYear).getText();
	}

	public int getCarValue() {
		String price = driver.findElement(carValue).getText();
		String carPrice = price.replace("£", "").replace(",", "").trim();
		return Integer.parseInt(carPrice);
	}

	public String getCarRegNotFound() {
		return driver.findElement(carRegNotFound).getText();
	}

	public void clickOnChangeVehicleLink() {
		driver.findElement(changeVehicle).click();
	}

	public Boolean getVehicleSummary() {
		return driver.findElement(vehicleSummary).isDisplayed();
	}

	public boolean isVehicleSummaryNotDisplayed() {
		try {
			List<WebElement> elements = driver.findElements(vehicleSummary);

			if (elements.isEmpty()) {
				return false;
			}
			boolean isDisplayed = elements.get(0).isDisplayed();
			return isDisplayed;
		} catch (Exception e) {
			return false;
		}
	}
}
