package cucumber;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

public class StepDefinitionsVacationPlanning_ActivityPlanning {
	
	private static void sleep(int millis) {
		try {
	    	Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static final String ROOT_URL = "http://localhost:8080/";
	private static final String VACATION_PLANNING_URL = "http://localhost:8080/vacationPlanning.html";

	private static final String tomato = "rgb(255, 99, 71)";
	private static final String normalInputColor = "rgb(206, 212, 218)";

	private final WebDriver driver = new ChromeDriver();
	
	@Given("I am on the Vacation Planning page")
	public void i_am_on_the_Vacation_Plannin_page() {
	    driver.get(VACATION_PLANNING_URL);
	}
	
	@Then("I should see a text box {string}")
	public void i_should_see_a_text_box(String string) {
		assertEquals("input", driver.findElement(By.id(string)).getTagName());
	}
	
	@Then("I should see a button with text {string}")
	public void i_should_see_a_button_with_text(String string) {
	    assertEquals(string, driver.findElement(By.id("submit")).getText());
	}
	
	@When("I submit the form")
	public void i_submit_the_form() {
		driver.findElement(By.id("submit")).click();
		sleep(2000);
	}

	@Then("I should see an illegal value error for {string}")
	public void i_should_see_an_illegal_value_error_for(String string) {
		// show that input has correct highlight
	    WebElement input = driver.findElement(By.id(string));
	    String borderColor = input.getCssValue("border-color");
	    assertEquals(borderColor, tomato);
	    
	    // show that error message is displayed
	    List<WebElement> errors = driver.findElements(By.className("error-text"));
	    boolean errorFound = false;
	    String expectedError = "illegal value for input " + string;
	    for(WebElement error : errors) {
	    	String errorText = error.getText();
	    	if(errorText.equals(expectedError)) {
	    		errorFound = true;
	    		break;
	    	}
	    }
	    assertTrue(errorFound);
	}
	
	@When("I enter {string} into input {string}")
	public void i_enter_into_input(String text, String inputId) {
	    driver.findElement(By.id(inputId)).sendKeys(text);
	}
	
	@Then("I should not see an illegal error value for {string}")
	public void i_should_not_see_an_illegal_error_value_for(String inputId) {
		assertEquals(driver.findElement(By.id(inputId)).getCssValue("border-color"), normalInputColor);
	}
	
	@After()
	public void after() {
//		sleep(5000);
		driver.quit();
	}

}
