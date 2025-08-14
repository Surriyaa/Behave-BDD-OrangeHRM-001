package TestPages;
import com.bridgelabz.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginPageTest {
    WebDriver driver;
    LoginPage loginPage;

    // Test Data
    String url = "https://bl-learnerpractice-app-stg-187791816934.asia-south1.run.app/";
    String email = "lakshmi.muthyalppa@bridgelabz.com";
    String password = "Lakshmi@132";

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);

    }

    @Test
    public void verifySuccessfulLogin() {
        loginPage.loginWithGoogle(url, email, password);
        // Wait for a known post-login element (profile icon)
        // Wait for page title to contain "BL Learner"
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        boolean isTitleCorrect = wait.until(ExpectedConditions.titleIs("BL Learner"));

        // Assertion to confirm title
        Assert.assertTrue(isTitleCorrect, "❌ Login failed or not redirected to 'BL Learner' page.");
        System.out.println("✅ Login successful - Page title is 'BL Learner'.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
