package cucumber;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StepDefinitionsVacationPlanning {

	private static final String ROOT_URL = "http://localhost:8080/";
	private static final String PAGE_URL = "http://localhost:8080/vacationPlanning.html";


	private final WebDriver driver = new ChromeDriver();
	
	@Given("I am on the Vacation Planning page")
	public void i_am_on_the_Vacation_Plannin_page() {
	    driver.get(PAGE_URL);
	}
	
	@Then("I should see VP text box {string}")
	public void i_should_see_VP_text_box(String string) {
		assertEquals("input", driver.findElement(By.id(string)).getTagName());
	}
	
	@Then("I should see VP button with text {string}")
	public void i_should_see_VP_button_with_text(String string) {
	    assertEquals(string, driver.findElement(By.id("submit")).getAttribute("value"));
	}
	
	@When("I submit the form")
	public void i_submit_the_form() {
		driver.findElement(By.id("submit")).click();
	}

	@Then("I should see a VP illegal value error for {string}")
	public void i_should_see_a_VP_illegal_value_error_for(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@After()
	public void after() {
		driver.quit();
	}

}
