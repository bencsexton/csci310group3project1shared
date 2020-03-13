package cucumber;

import io.cucumber.java.After;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class StepDefinitionsWeatherAnalysis {

	private static final String ROOT_URL = "http://localhost:8080/";
	private static final String PAGE_URL = "http://localhost:8080/weather-analysis.html";

	private final WebDriver driver = new ChromeDriver();

	@Given("I am on the Weather Analysis page")
	public void iAmOnTheWeatherAnalysisPage() {
		driver.get(PAGE_URL);
	}

	@Given("The favorite cities list has no item")
	public void theFavoriteCitiesListHasNoItem() {
		assertEquals("", driver.findElement(By.id("favorite-cities-list")).getText());
	}

	@Then("AIP: I should see {string} text in {string}")
	public void aipIShouldSeeTextIn(String string1, String string2) {
		assertEquals(string1, driver.findElement(By.className(string2)).getText());
	}

	@Then("AIP: I should not see {string} element")
	public void aipIShouldNotSeeElement(String string) {
		assertEquals(0, driver.findElements(By.id(string)).size());
	}

	@When("The favorite cities list has one or more items")
	public void theFavoriteCitiesListHasOneOrMoreItems() {
		assertTrue(driver.findElements(By.className("list-group-item")).size() > 0);
	}

	@When("I click a city from the favorite cities list")
	public void iClickACityFromTheFavoriteCitiesList() {
		driver.findElement(By.className("list-item-city")).click();
	}

	@Then("I should see the city is selected")
	public void iShouldSeeTheCityIsSelected() {
		List<WebElement> cityNames = driver.findElements(By.className("list-group-item"));
		Boolean selected = false;
		for (WebElement city : cityNames) {
			if (city.getAttribute("class").contains("active")) {
				selected = true;
				assertTrue(selected);
			};
		}
		assertTrue(selected);
	}

	@Then("AIP: I should see {string} element")
	public void aipIShouldSeeIdElement(String string) {
		assertTrue(driver.findElements(By.id(string)).size() > 0);
	}

	@Then("AIP: I should see {int} {string} elements")
	public void aipIShouldSeeElements(int num, String string) {
		assertEquals(num, driver.findElements(By.className(string)).size());
	}
	@Then("AIP: I should see at least one image in city photo area")
	public void iShouldSeeAtLeastOneImageInCityPhotoArea() {
		WebElement rootWebElement = driver.findElement(By.id("photo-section"));
		List<WebElement> childs = rootWebElement.findElements(By.xpath(".//*"));
		assertTrue(childs.size() > 0);
	}

	@Then("AIP: I should see at least {int} {string} elements")
	public void aipIShouldSeeAtLeastElements(int num, String string) {
		assertTrue(driver.findElements(By.id(string)).size() >= num);
	}

	@Then("AIP: I should see a value for {string} element")
	public void aipIShouldSeeAValueForElement(String string) {
		assertFalse(driver.findElement(By.id(string)).getText().isEmpty());
	}

	@Then("The list should be sorted alphabetically by city")
	public void theListShouldBeSortedAlphabeticallyByCity() {
		List<WebElement> cityNames = driver.findElements(By.className("list-item-city"));
		Boolean sorted = true;
		if (!cityNames.isEmpty() && cityNames.size() != 1) {
			Iterator<WebElement> iter = cityNames.iterator();
			WebElement current, previous = iter.next();
			while (iter.hasNext()) {
				current = iter.next();
				if (previous.getText().compareTo(current.getText()) > 0) {
					sorted = false;
					break;
				}
				previous = current;
			}
		}
		assertTrue(sorted);
	}
	@When("AIP: I click {string}")
	public void iClickTheRemoveFromFavoritesButtons(String string) {
		driver.findElement(By.id("remove-from-favorites-button")).click();
	}

	@Then("A confirmation popup box should be displayed before the removal")
	public void aConfirmationPopupBoxShouldBeDisplayedBeforeTheRemoval() {
		assertTrue(driver.findElement(By.id("dialog-confirm")).getAttribute("displayed").equals("true"));
	}

	@When("I click {string} from the confirmation box")
	public void aipIClickFromTheConfirmationBox(String string) {
		int selectedOption = string.equals("Yes") ? 0 : 1;
		List<WebElement> buttons = driver.findElements(By.className("dialog-confirm-buttons"));
		buttons.get(selectedOption).click();
	}

	@Then("The city should be removed from the list")
	public void theCityShouldBeRemovedFromTheList() {
		assertEquals(0, driver.findElements(By.className("active")).size());
	}

	@Then("The message on the popup box should be Are you sure you want to remove <city name> from favorites?")
	public void theMessageOnThePopupBoxShouldBe() {
		String string = "Are you sure you want to remove ";
		string += driver.findElement(By.className("active")).getAttribute("city");
		string += " from favorites?";
		assertEquals(string, driver.findElement(By.id("dialog-remove-confirm-message")).getText());
	}

	@Then("The options should be {string} and {string}")
	public void theOptionsShouldBeAnd(String string1, String string2) {
		List<WebElement> buttons = driver.findElements(By.className("dialog-confirm-buttons"));
		Boolean matches = buttons.get(0).getText().equals("Yes")
				&& buttons.get(1).getText().equals("Cancel");
		assertTrue(matches);
	}

	@Then("The city should be remain in the favorites city list")
	public void theCityShouldBeRemainInTheFavoritesCityList() {
		assertTrue(driver.findElements(By.className("active")).size() > 0);
	}

	@Then("The units for the temperatures in the weather analysis area should be changed")
	public void theUnitsForTheTemperaturesInTheWeatherAnalysisAreaShouldBeChanged() {
		String string = driver.findElement(By.id("current-temp-val")).getText();
		assertEquals('F', string.charAt(string.length() - 1));
	}

	@After()
	public void after() {
		driver.quit();
	}

}
