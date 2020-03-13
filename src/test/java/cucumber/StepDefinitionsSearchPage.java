package cucumber;

import io.cucumber.java.After;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StepDefinitionsSearchPage {

	private static final String ROOT_URL = "http://0.0.0.0:8080/";
	private static final String PAGE_URL = "http://0.0.0.0:8080/main-search.html"; // FEEL FREE TO CHANGE

	public final WebDriver driver = new ChromeDriver();

	@Given("I am on the search page")
	public void i_am_on_the_search_page() {
		driver.get(PAGE_URL);
	}	
	@Given("unit is F")
	public void current_unit_is_F() {
		WebElement units = driver.findElement(By.id("units"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", 
				units,  
                "innerHTML",
                "°F");
	}
	@Given("temperature has value")
	public void temperature_has_value() {
		WebElement temp = driver.findElement(By.id("currTemperature"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", 
				temp,  
                "innerHTML",
                "25");
	}
	

	@When("I reload page")
	public void i_reload_page() {
		driver.navigate().refresh();
	}
	@When("I start typing in textbox")
	public void i_start_typing() {
		WebElement textbox = driver.findElement(By.id("locationText"));
		textbox.sendKeys("a");
	}
	
	@When("There is legal input in textbox")
	public void there_is_legal_input_in_textbox() {
		WebElement textbox = driver.findElement(By.id("locationText"));
		textbox.sendKeys("Los Angeles");
	}
	@When("There is illegal input in textbox")
	public void there_is_illegal_input_in_textbox() {
		WebElement textbox = driver.findElement(By.id("locationText"));
		textbox.sendKeys("123");
	}
	
	@When("I click {string}")
	public void i_click_show_weather(String string) {
		driver.findElement(By.id(string)).click();
	}

	@Then("I should see a single textbox")
	public void i_should_see_textbox() {
		assertEquals("input", driver.findElement(By.id("locationText")).getTagName());
	}
	@Then("The textbox should have default focus")
	public void the_textbox_should_have_default_focus() {
		assertTrue(driver.findElement(By.id("locationText")).equals(driver.switchTo().activeElement()));
	}
	@Then("The textbox should have the prompt text Enter location \\(city or zip)")
	public void the_textbox_should_have_the_prompt_text() {
		assertEquals("Enter location (city or zip)", driver.findElement(By.id("locationText")).getAttribute("placeholder"));
	}
	@Then("Prompt text should disappear")
	public void prompt_text_should_disappear() {
		WebElement textbox = driver.findElement(By.id("locationText"));
		assertEquals("", textbox.getAttribute("placeholder"));
	}
	@Then("Weather data is displayed in weather display area")
	public void weather_data_us_displayed_in_weather_display_area() {
	}
	@Then("Display \"No weather data found\" in weather display area")
	public void display_no_weather_data_found() {
		
	}
	@Then("I should see {string} {string}")
	public void i_should_see_type_string(String type, String string) {
		assertEquals(type, driver.findElement(By.id(string)).getTagName());
	}
	@Then("Unit should change to C")
	public void unit_should_be_C() {
		assertEquals("°F", driver.findElement(By.id("units")).getAttribute("innerHTML"));
	}
	@Then("Temperature should change correctly")
	public void temp_should_change() {
		assertEquals("77", driver.findElement(By.id("currTemperature")).getAttribute("innerHTML"));
	}
	@Then("I should see navigation bar")
	public void should_see_navbar() {
		assertTrue(driver.findElement(By.id("nav")).isDisplayed());
	}

	
	@After()
    public void after() {
        driver.quit();
    }


}
