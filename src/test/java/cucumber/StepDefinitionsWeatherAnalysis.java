package cucumber;

import io.cucumber.java.After;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.*;

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
		assertTrue(driver.findElement(By.id("favorite-cities-list")).getText().equals("");
	}
	@Then("I should see a text {string} in {string}")
	public void iShouldSeeAText(String string1, String string2) {
		assertEquals(string1, driver.findElement(By.className(string2)).getText());
	}
	@Then("I should not see {string} element")
	public void iShouldNotSeeElement(String string) {
		assertEquals(0, driver.findElement(By.id(string)).getSize());
	}
	@When("The favorite cities list has one or more items")
	public void theFavoriteCitiesListHasOneOrMoreItems() {
		assertFalse(driver.findElement(By.id("favorite-cities-list")).getText().equals(""));
	}
	@When("I click a city from the favorite cities list")
	public void iClickACityFromTheFavoriteCitiesList() {
		driver.findElement(By.className("list-item-city")).click();
	}
	@Then("I should see the city is selected")
	public void iShouldSeeTheCityIsSelected() {
		assertTrue(driver.findElement(By.className("list-item-city")).getAttribute("class").contains("active"));
	}
}