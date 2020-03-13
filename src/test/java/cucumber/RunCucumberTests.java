<<<<<<< HEAD
package cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * Run all the cucumber tests in the current package.
 */
@RunWith(Cucumber.class)
@CucumberOptions(strict = true, features = { "src/test/resources/cucumber/searchPage.feature" })
public class RunCucumberTests {

	@BeforeClass
	public static void setup() {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		WebDriverManager.chromedriver().setup();
	}

}
||||||| merged common ancestors
package cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * Run all the cucumber tests in the current package.
 */
@RunWith(Cucumber.class)
@CucumberOptions(strict = true, features = { "src/test/resources/cucumber/searchPage.feature" })
@CucumberOptions(strict = true, features = { "src/test/resources/cucumber/hello.feature" })
public class RunCucumberTests {

	@BeforeClass
	public static void setup() {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		WebDriverManager.chromedriver().setup();
	}

}
=======
package cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * Run all the cucumber tests in the current package.
 */
@RunWith(Cucumber.class)
@CucumberOptions(strict = true, features = { 
		"src/test/resources/cucumber/hello.feature", 
		"src/test/resources/cucumber/searchPage.feature" 
		})
public class RunCucumberTests {

	@BeforeClass
	public static void setup() {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		WebDriverManager.chromedriver().setup();
	}

}
>>>>>>> b0575581d8cd49955d5338a74e803a71001afaff
