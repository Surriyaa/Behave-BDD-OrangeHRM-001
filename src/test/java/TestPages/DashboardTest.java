package TestPages;

import com.bridgelabz.pages.Dashboard;
import com.bridgelabz.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;


public class DashboardTest {
    WebDriver driver;
    Dashboard dashboard;
    LoginPage loginPage;

    // Test data
    String url = "https://bl-learnerpractice-app-stg-187791816934.asia-south1.run.app/";
    String email = "lakshmi.muthyalppa@bridgelabz.com";
    String password = "Lakshmi@132";     // Replace with password

    @BeforeClass
    public void setUp() {
        // Set path to chromedriver if needed
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }


    @Test
    public void validateAttendanceDisplay() {
        loginPage = new LoginPage(driver);
        loginPage.loginWithGoogle(url, email, password);

        // Wait for page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.titleContains("BL Learner"));

        // Initialize Dashboard
        dashboard = new Dashboard(driver);

        // Validate welcome message (optional but helpful check)
        boolean isWelcome = dashboard.isWelcomeMessageVisible();
        Assert.assertTrue(isWelcome, "Welcome message not visible - login might have failed.");

        // Validate and print attendance table
        boolean hasData = dashboard.printAttendanceTableData();
        Assert.assertTrue(hasData, "Attendance table is empty or not displayed.");
    }
@Test
public void clickTodaysPracticeAndVerifyQRPage() {
    loginPage = new LoginPage(driver);
    loginPage.loginWithGoogle(url, email, password);

    // Wait for page to load
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    wait.until(ExpectedConditions.titleContains("BL Learner"));

    // Initialize Dashboard
    dashboard = new Dashboard(driver);

    // Perform action and verify
    boolean isQRVisible = dashboard.clickTodaysPracticeAndVerifyQRPage();
    System.out.println("QR scan message visible: " + isQRVisible);
    Assert.assertTrue(isQRVisible, "QR code scan message not displayed.");
}
@Test
public void verifyallpracticesubmission(){
    loginPage = new LoginPage(driver);
    loginPage.loginWithGoogle(url, email, password);

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    wait.until(ExpectedConditions.titleContains("BL Learner"));

    dashboard = new Dashboard(driver);
    boolean result = dashboard.clickAllCodingPractices();

    System.out.println("Practice flow result: " + result);
    Assert.assertTrue(result, "All Practice flow failed – Submit not successful.");
}

@Test
public void verifyProfiledownloadqr()
{
    loginPage =new LoginPage(driver);
    loginPage.loginWithGoogle(url,email,password);
    WebDriverWait wait =new WebDriverWait(driver,Duration.ofSeconds(10));
    wait.until(ExpectedConditions.titleContains("BL Learner"));
    dashboard=new Dashboard(driver);
    boolean result= dashboard.verifyQrCodeDownload();
    Assert.assertTrue(result,"Failed to download ");
}
@Test
public void verifysignout(){
    loginPage =new LoginPage(driver);
    loginPage.loginWithGoogle(url,email,password);
    WebDriverWait wait =new WebDriverWait(driver,Duration.ofSeconds(10));
    wait.until(ExpectedConditions.titleContains("BL Learner"));
    dashboard=new Dashboard(driver);
    boolean result= dashboard.validateSignout();
    Assert.assertTrue(result,"Failed to Signout ");
}
@Test
public void validateAllTopics(){
    loginPage =new LoginPage(driver);
    loginPage.loginWithGoogle(url,email,password);
    WebDriverWait wait =new WebDriverWait(driver,Duration.ofSeconds(10));
    wait.until(ExpectedConditions.titleContains("BL Learner"));
    dashboard=new Dashboard(driver);
    boolean result= dashboard.validateSingleReviewedTopic();
    Assert.assertTrue(result,"Failed to display review ");
}
@Test
public void verifyALLtopicpdf(){
    loginPage =new LoginPage(driver);
    loginPage.loginWithGoogle(url,email,password);
    WebDriverWait wait =new WebDriverWait(driver,Duration.ofSeconds(10));
    wait.until(ExpectedConditions.titleContains("BL Learner"));
    dashboard=new Dashboard(driver);
    boolean result= dashboard.validatePdfAccessForLinkedList1();
    Assert.assertTrue(result,"Failed to access ");
}

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
