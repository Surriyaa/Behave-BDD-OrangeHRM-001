package com.bridgelabz.pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    // Locators
    By signInWithGoogleBtn = By.xpath("//span[contains(text(),'Sign in with Google')]");
    By emailField = By.xpath("//input[@type='email']");
    By nextButton = By.xpath("//span[normalize-space()='Next']");
    By passwordField = By.name("Passwd");
    By overlayPopup = By.xpath("//div[contains(@class,'fixed') and contains(@class,'z-50')]");
    By profileIcon = By.xpath("//*[@data-testid='AccountCircleIcon']/parent::button");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void loginWithGoogle(String url, String email, String password) {
        driver.get(url);

        System.out.println("Opened URL: " + url);

        // Wait until Google Sign-In button is present
        wait.until(ExpectedConditions.presenceOfElementLocated(signInWithGoogleBtn)).click();

        // Switch to popup window

        String mainWindow = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(emailField));
        emailInput.clear();
        emailInput.sendKeys(email);
        driver.findElement(nextButton).click();

        WebElement passwordInput = wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        passwordInput.clear();
        passwordInput.sendKeys(password);
        passwordInput.sendKeys(Keys.TAB);
        driver.findElement(nextButton).click();

        driver.switchTo().window(mainWindow);

        // Try to close overlay popup if appears
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement overlay = shortWait.until(ExpectedConditions.presenceOfElementLocated(overlayPopup));
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none';", overlay);
            System.out.println("Overlay popup closed after login.");
        } catch (TimeoutException e) {
            System.out.println("No overlay popup found after login.");
        }
    }

    public boolean isLoginSuccessful() {
        return driver.getTitle().equals("BL Practice App");
    }

}
