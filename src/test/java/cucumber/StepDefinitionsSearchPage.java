package cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StepDefinitionsSearchPage {

	private static final String ROOT_URL = "http://localhost:8080/";
	private static final String PAGE_URL = "http://localhost:8080/search.html"; // FEEL FREE TO CHANGE

	public final WebDriver driver = null;// = new ChromeDriver();

	@Given("I am on the search page")
	public void i_am_on_the_search_page() {
		driver.get(PAGE_URL);
	}

	@When("I reload page")
	public void i_click_the_link() {
		driver.navigate().refresh();
	}

	@Then("I should see a single textbox")
	public void i_should_see_textbox() {
		assertEquals("input", driver.findElement(By.id("locationText")).getTagName());
	}
//	private final WebDriver driver = new ChromeDriver();


}
