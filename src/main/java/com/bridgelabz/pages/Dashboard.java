package com.bridgelabz.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;

import java.time.Duration;
import java.util.List;
import java.io.File;

public class Dashboard {
    WebDriver driver;
    WebDriverWait wait;

    By attendanceTable = By.xpath("//table[@aria-label='customized table']");
    By tableRows = By.xpath("//table[@aria-label='customized table']/tbody/tr");
    By welcomeMsg = By.xpath("//p[contains(text(), 'Welcome')]");
    By todaysPractice = By.xpath("//span[normalize-space()=\"Today's Practice\"]");
    By scanMessage = By.xpath("//p[contains(text(), 'Please scan QR code')]");
    By allpractice=By.xpath("//span[contains(text(),'All Coding Practices')]");
    By dayorder4=By.xpath("//span[normalize-space()='Day : Day Order 4']");
    By submitpractice= By.xpath("//div[@id='data-6-content']//button[normalize-space()='SUBMIT PRACTICE']");
    By linkfield=By.xpath("//fieldset[contains(@class,'MuiOutlinedInput-notchedOutline')][legend/span[text()='Practice Link']]");
    By submitBtn = By.xpath("//div[contains(@class,'MuiBox-root') and .//h5[text()='Submit Practice']]//button[normalize-space()='Submit']");
    By showqr=By.xpath("//li[p[text()='Show QR']]");
    By profile=By.xpath("//button[@aria-haspopup='true' and @aria-controls='primary-search-account-menu']");
    By learnerqr=By.xpath("//img[contains(@alt,'QR')]");
    By downloadbtn=By.xpath("//button[contains(text(),'Download')]");
    By signoutbtn=By.xpath("//li[p[normalize-space()='Sign Out']]");
    By alltopic=By.xpath("//div[.//span[text()='All Topics'] and contains(@class,'nav-item')]");

    // Constructor
    public Dashboard(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isWelcomeMessageVisible() {
        try {
            System.out.println("Waiting for welcome message...");
            wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMsg));
            WebElement msg = driver.findElement(welcomeMsg);
            System.out.println("Found element text: " + msg.getText());
            return msg.isDisplayed();
        } catch (Exception e) {
            System.out.println("Welcome message not found: " + e.getMessage());
            return false;
        }
    }
    public boolean printAttendanceTableData() {
        try {
            System.out.println("Validating attendance table...");

            // Wait for table
            WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(attendanceTable));
            List<WebElement> rows = driver.findElements(tableRows);

            if (rows.isEmpty()) {
                System.out.println("No attendance data found.");
                return false;
            }

            System.out.println("Attendance Table:");
            System.out.printf("%-12s %-15s %-15s %-15s%n", "Date", "Check-In", "Check-Out", "Duration");

            for (WebElement row : rows) {
                List<WebElement> columns = row.findElements(By.tagName("td"));
                if (columns.size() == 4) {
                    String date = columns.get(0).getText().trim();
                    String checkIn = columns.get(1).getText().trim();
                    String checkOut = columns.get(2).getText().trim();
                    String duration = columns.get(3).getText().trim();

                    System.out.printf("%-12s %-15s %-15s %-15s%n", date, checkIn, checkOut, duration);
                } else {
                    System.out.println("Unexpected column count in a row: " + columns.size());
                }
            }

            return true;

        } catch (Exception e) {
            System.out.println("Error while reading attendance table: " + e.getMessage());
            return false;
        }
    }

    public boolean clickTodaysPracticeAndVerifyQRPage() {
        try {
            // Click on Today's Practice
            wait.until(ExpectedConditions.elementToBeClickable(todaysPractice)).click();

            // Wait for the scan QR message to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(scanMessage));

            // Fetch and print message
            WebElement message = driver.findElement(scanMessage);
            System.out.println("Found element text: " + message.getText());

            return message.isDisplayed();
        } catch (Exception e) {
            System.out.println("Scanning Option not found: " + e.getMessage());
            return false;
        }

    }
    public boolean clickAllCodingPractices() {
        try {
            System.out.println("Clicking All Practices...");
            WebElement allPracticeBtn = wait.until(ExpectedConditions.elementToBeClickable(allpractice));
            scrollAndHighlight(allPracticeBtn);
            allPracticeBtn.click();

            System.out.println("Clicking Day 4...");
            WebElement day4Btn = wait.until(ExpectedConditions.elementToBeClickable(dayorder4));
            scrollAndHighlight(day4Btn);
            day4Btn.click();
            Thread.sleep(1000);

            System.out.println("Clicking inner Submit Practice button...");
            WebElement submitBtnpractice = wait.until(ExpectedConditions.elementToBeClickable(submitpractice));
            scrollAndHighlight(submitBtnpractice);
            safeClick(submitBtnpractice);
            Thread.sleep(1000);

            System.out.println("Waiting for Practice Link field...");
            WebElement linkFieldElement = wait.until(ExpectedConditions.presenceOfElementLocated(linkfield));
            scrollAndHighlight(linkFieldElement);
            Actions actions = new Actions(driver);
            //optional delay for focus

            System.out.println("Entering practice document link...");
            ((JavascriptExecutor) driver).executeScript(
                    "const field = arguments[0];" +
                            "field.focus();" +
                            "field.value = '';" +
                            "field.dispatchEvent(new Event('input', { bubbles: true }));",
                    linkFieldElement
            );
            ((JavascriptExecutor) driver).executeScript(
                    "const field = arguments[0];" +
                            "field.focus();" +
                            "field.value = arguments[1];" +
                            "field.dispatchEvent(new Event('input', { bubbles: true }));",
                    linkFieldElement,
                    "https://docs.google.com/document/d/1EMsLMhVS8MFOTjnkZd75j8I2wZ_kq78yJwKKTrcXKrs/edit?usp=sharing"
            );

            Thread.sleep(1000);

            System.out.println("Clicking final Submit...");
            WebElement finalSubmitBtn = wait.until(ExpectedConditions.elementToBeClickable(submitBtn));
            scrollAndHighlight(finalSubmitBtn);
            safeClick(finalSubmitBtn);

            System.out.println("All Practice flow completed successfully.");
            return true;

        } catch (Exception e) {
            System.out.println("Error in clickAllCodingPractices(): " + e.getMessage());
            return false;
        }
    }
    public void scrollAndHighlight(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Scroll the element into view
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
        // Optionally highlight the element with a yellow border
        js.executeScript("arguments[0].style.border='2px solid yellow'", element);
    }
    private void safeClick(WebElement element) {
        try {
            element.click();
        } catch (Exception clickEx) {
            System.out.println("Standard click failed, trying JavaScript click...");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public boolean verifyQrCodeDownload() {
        try {
            // Step 1: Click on Profile Icon

            WebElement profileIcon = wait.until(ExpectedConditions.elementToBeClickable(profile));
            Thread.sleep(5000);
            profileIcon.click();
            System.out.println("Clicked on profile icon.");

            // Step 2: Click on "Show QR Code" from menu
            WebElement showQrOption = wait.until(ExpectedConditions.elementToBeClickable(showqr));
            showQrOption.click();
            System.out.println("Clicked on 'Show QR'.");

            // Step 3: Wait for QR Code modal and Download button
            WebElement qrImage = wait.until(ExpectedConditions.visibilityOfElementLocated(learnerqr));
            WebElement downloadBtn = wait.until(ExpectedConditions.elementToBeClickable(downloadbtn));
            System.out.println("QR image and download button are visible.");

            // Step 4: Clear previous downloaded file if exists (optional)
            String downloadPath = System.getProperty("user.home") + "\\Downloads\\";
            String fileName = "LakshmiM_lakshmi.muthyalppa@bridgelabz.com.png"; // Assuming filename
            File file = new File(downloadPath + fileName);
            if (file.exists()) {
                file.delete();
            }

            // Step 5: Click download
            downloadBtn.click();
            System.out.println("Clicked download button.");

            // Step 6: Wait for file to appear in Downloads
            int waitTime = 0;
            while (!file.exists() && waitTime < 10000) {
                Thread.sleep(1000);
                waitTime += 1000;
            }

            if (file.exists()) {
                System.out.println("QR Code downloaded successfully at: " + file.getAbsolutePath());
                return true;
            } else {
                System.out.println("QR Code not downloaded.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error verifying QR Code download: " + e.getMessage());
            return false;
        }
    }

    public boolean validateSignout(){
       try {
           // Step 1: Click on Profile Icon
           WebElement profileIcon = wait.until(ExpectedConditions.elementToBeClickable(profile));
           profileIcon.click();
           System.out.println("Clicked on profile icon.");
           Thread.sleep(1000);
           // Step 1: Click on Profile Icon
           WebElement signout = wait.until(ExpectedConditions.elementToBeClickable(signoutbtn));
           signout.click();
           Thread.sleep(1000);
           System.out.println("Clicked on Signout.");
           return true;
       } catch (Exception e) {
           System.out.println("not able to signout");
           return false;
       }
    }

    public boolean validateSingleReviewedTopic() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement topics = wait.until(ExpectedConditions.elementToBeClickable(alltopic));
            topics.click();
            System.out.println("Clicked on ALL Topics.");

            // Step 2: Get Topic Name
            WebElement topicNameEl = driver.findElement(By.xpath("//span[@class='MuiTypography-root MuiTypography-body1 css-uut5kg']"));
            String topicName = topicNameEl.getText().trim();
            System.out.println("Topic Name: " + topicName);
            Thread.sleep(3000);
            // Step 3: Get Track Score
            WebElement scoreEl = driver.findElement(By.xpath("//span[contains(text(),'Track Score')]"));
            String scoreText = scoreEl.getText().replace("Track Score:", "").trim();
            System.out.println("Track Score: " + scoreText);

            // Step 4: Get Review Date
            WebElement reviewDateEl = driver.findElement(By.xpath("//span[contains(text(),'Review Date')]"));
            String reviewDateText = reviewDateEl.getText().replace("Review Date:", "").trim();
            System.out.println("Review Date: " + reviewDateText);
            Thread.sleep(3000);

            // Step 5: Click Track Score link to open review details
            scoreEl.click();
            System.out.println("Clicked on Track Score link.");

            // Step 6: Wait for Track Score Details section to be visible
            WebElement detailsBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'MuiBox-root') and @tabindex='-1']")));

            System.out.println("------ Track Score Details ------");

            // Topic Name
            String topic = detailsBox.findElement(By.xpath(".//p[text()='Topic Name']/following::p[1]")).getText();
            System.out.println("Topic Name: " + topic);

            // Track Score
            String trackScore = detailsBox.findElement(By.xpath(".//p[text()='Track Score']/following::p[1]")).getText();
            System.out.println("Track Score: " + trackScore);

            // Technical Ability Score
            String technical = detailsBox.findElement(By.xpath(".//p[text()='Technical Ability Score']/following::p[1]")).getText();
            System.out.println("Technical Ability Score: " + technical);

            // Learnability Score
            String learnability = detailsBox.findElement(By.xpath(".//p[text()='Learnability Score']/following::p[1]")).getText();
            System.out.println("Learnability Score: " + learnability);

            // Communicability Score
            String communicability = detailsBox.findElement(By.xpath(".//p[text()='Communicability Score']/following::p[1]")).getText();
            System.out.println("Communicability Score: " + communicability);

            // Review Comment
            String comment = detailsBox.findElement(By.xpath(".//p[text()='Review Comment']/following-sibling::div")).getText();
            System.out.println("Review Comment: " + comment);

            // Review Date
            String finalReviewDate = detailsBox.findElement(By.xpath(".//strong[text()='Review Date : ']/parent::span")).getText();
            System.out.println("Review Date: " + finalReviewDate);

            System.out.println("--------------------------------");

            return true; // success

        } catch (Exception e) {
            System.out.println("Error while validating reviewed topic: " + e.getMessage());
            return false; // failure
        }
    }
    public boolean validatePdfAccessForLinkedList1() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Click on "All Topics"
            WebElement topics = wait.until(ExpectedConditions.elementToBeClickable(alltopic));
            topics.click();
            System.out.println("Clicked on ALL Topics.");

            // Step 2: Click the topic "Linked List1"
            WebElement topicButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[.//span[text()='Linked List1']]")));
            topicButton.click();
            System.out.println("Clicked on topic: Linked List1");

            // Step 3: Wait for iframe(s) to appear
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));

            // Step 4: Locate and click all clickable iframe containers (not the iframe itself)
            List<WebElement> iframeContainers = driver.findElements(
                    By.xpath("//div[iframe and contains(@style, 'cursor: pointer')]"));

            if (iframeContainers.isEmpty()) {
                System.out.println("No clickable PDF containers found.");
                return false;
            }

            int index = 1;
            for (WebElement container : iframeContainers) {
                try {
                    container.click();
                    System.out.println("Clicked PDF container " + index);
                } catch (Exception e) {
                    System.out.println("Failed to click PDF container " + index + ": " + e.getMessage());
                }
                index++;
            }

            return true; //  SUCCESS case

        } catch (Exception e) {
            System.out.println("Exception while validating PDF access: " + e.getMessage());
            return false; //FAILURE case
        }
    }

}
