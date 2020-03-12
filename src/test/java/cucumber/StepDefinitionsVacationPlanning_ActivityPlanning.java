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
	private static final String ACTIVITY_PLANNING_URL = "http://localhost:8080/activityPlanning.html";

	private static final String tomato = "rgb(255, 99, 71)";
	private static final String normalInputColor = "rgb(206, 212, 218)";

	private final WebDriver driver = new ChromeDriver();
	
	@Given("I am on the Vacation Planning page")
	public void i_am_on_the_Vacation_Plannin_page() {
	    driver.get(VACATION_PLANNING_URL);
	}
	
	@Given("I am on the Activity Planning page")
	public void i_am_on_the_Activity_Plannin_page() {
	    driver.get(ACTIVITY_PLANNING_URL);
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
	    sleep(5000);
	}
	
	@Then("I should not see an illegal error value for {string}")
	public void i_should_not_see_an_illegal_error_value_for(String inputId) {
		assertEquals(driver.findElement(By.id(inputId)).getCssValue("border-color"), normalInputColor);
	}
	
	@Then("I should see table header {string}")
	public void i_should_see_table_header(String headerText) {
	    List<WebElement> headers = driver.findElements(By.className("header"));
	    boolean headerFound = false;
	    for(WebElement header : headers) {
	    	if(header.getText().equals(headerText)) {
	    		headerFound = true;
	    		break;
	    	}
	    }
	    assertTrue(headerFound);
	}
	
	@When("I click the distance header")
	public void i_click_the_distance_header() {
		driver.findElement(By.id("distance")).click();
	}
	
	@Then("the distances should be in {string} order")
	public void the_distances_should_be_in_order(String order) {
	    List<WebElement> distances = driver.findElements(By.className("distance"));
	    boolean sorted = true;
	    int num1, num2;
	    for(int i = 0; i < distances.size() - 1; i++) {
	    	num1 = Integer.parseInt(distances.get(i).getText());
	    	num2 = Integer.parseInt(distances.get(i + 1).getText());
	    	if(order.contentEquals("ascending")) {
	    		if(num1 > num2) {
	    			sorted = false;
	    			break;
	    		}
	    	}
	    	else {
	    		if(num1 < num2) {
	    			sorted = false;
	    			break;
	    		}
	    	}
	    }
	    assertTrue(sorted);
	}
	
	@Then("the first location favorite changer should say {string}")
	public void the_first_location_favorite_changer_should_say(String text) {
	    WebElement favBtn = driver.findElement(By.className("fav-btn"));
	    assertEquals(text, favBtn.getText());
	}
	
	@When("I click the favorites changer of the first location")
	public void i_click_the_favorites_changer_of_the_first_location() {
		driver.findElement(By.className("fav-btn")).click();
	}
	
	@Then("I should see activity autocomplete option {string}")
	public void i_should_see_activity_autocomplete_option(String string) {
	    WebElement option = driver.findElement(By.linkText("skiing"));
	    System.out.println("option: " + option.getClass());
	}
	
	@When("I click the activity autocomplete option {string}")
	public void i_click_the_activity_autocomplete_option(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@Then("the activity input should say {string}")
	public void the_activity_input_should_say(String string) {
	    assertEquals(string, driver.findElement(By.id("activity")));
	}
	
	@When("I click the input {string}")
	public void i_click_the_input(String string) {
	    driver.findElement(By.id(string)).click();
	}
	
	@After()
	public void after() {
//		sleep(5000);
		driver.quit();
	}

}
