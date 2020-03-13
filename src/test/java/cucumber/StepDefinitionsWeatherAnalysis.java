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
		assertTrue(driver.findElement(By.className("list-item-city")).getAttribute("class").contains("active"));
	}

	@Then("AIP: I should see {string} element")
	public void aipIShouldSeeIdElement(String string) {
		assertTrue(driver.findElements(By.id(string)).size() > 0);
	}

	@Then("AIP: I should see {int} {string} elements")
	public void aipIShouldSeeElements(int num, String string) {
		assertEquals(num, driver.findElements(By.className(string)).size());
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
		assertEquals("true", driver.findElement(By.id("dialog-confirm")).getAttribute("data-displayed"));
	}

	@When("I click {string} from the confirmation box")
	public void aipIClickFromTheConfirmationBox(String string) {
		int selectedOption = string.equals("Yes") ? 0 : 1;
		driver.findElements(By.className("ui-corner-all")).get(0).click();
	}

	@Then("The city should be removed from the list")
	public void theCityShouldBeRemovedFromTheList() {
		assertEquals(0, driver.findElements(By.className("active")).size());
	}

	@Then("The message on the popup box should be Are you sure you want to remove <city name> from favorites?")
	public void theMessageOnThePopupBoxShouldBe() {
		String string = "Are you sure you want to remove ";
		string += driver.findElement(By.className("active")).getAttribute("data-city");
		string += " from favorites?";
		assertEquals(string, driver.findElement(By.id("dialog-remove-confirm-message")).getText());
	}

	@Then("The options should be {string} and {string}")
	public void theOptionsShouldBeAnd(String string1, String string2) {
		List<WebElement> options = driver.findElements(By.className("ui-corner-all"));
		Boolean matches = options.get(0).getText().equals("Yes")
				&& options.get(0).getText().equals("Cancel");
		assertTrue(matches);
	}

	@Then("The city should be remain in the favorites city list")
	public void theCityShouldBeRemainInTheFavoritesCityList() {
		assertTrue(driver.findElements(By.className("active")).size() > 0);
	}

	@Then("The units for the temperatures in the weather analysis area should be changed")
	public void theUnitsForTheTemperaturesInTheWeatherAnalysisAreaShouldBeChanged() {
		String string = driver.findElement(By.id("current-temp-val")).getText();
		assertEquals("F", string.charAt(string.length() - 1));
	}

}
