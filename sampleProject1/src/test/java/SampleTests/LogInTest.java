package SampleTests;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import Utils.BaseTest;

public class LogInTest extends BaseTest {

    private WebDriver webdriver;
    private ExtentTest test;

    @BeforeMethod
    public void setUp() {
        // Set up ExtentReports
        setupExtentReports();
        test = extent.createTest("LogInTest", "Verify Login functionality");

        // Set up the ChromeDriver
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        webdriver = new ChromeDriver();
        webdriver.manage().window().maximize();
        
        try {
            String screenshotPath = captureScreenshot(webdriver, "Browser_Launched");
            test.info("Browser launched and maximized", 
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception e) {  // Catches both IOException and other potential issues
            test.fail("Browser status update failed: " + e.getMessage());
        }
    }

    @Test(priority = 1)
    public void signupTest() {  // Changed method name to follow Java conventions (lowercase)
        try {
            // Step 1 - Go to the URL
            String url = "https://www.demoblaze.com/index.html";
            webdriver.get(url);
            test.info("Navigated to " + url, 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Navigated_To_URL")).build());

            // Step 2 - Verify the home page is displayed
            String navBarXpath = "//a[@class='navbar-brand']";
            WebElement homePageElement = webdriver.findElement(By.xpath(navBarXpath));
            Assert.assertTrue(homePageElement.isDisplayed(), "Home page is not displayed");
            test.pass("Home page is displayed", 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Home_Page_Displayed")).build());

            // Step 3 - Click the Login button
            String logInBtnXpath = "//*[@id=\"login2\"]";  // Fixed escaping
            WebElement logInButton = webdriver.findElement(By.xpath(logInBtnXpath));
            logInButton.click();
            test.info("Clicked on the log in button", 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "LogIn_Button_Clicked")).build());

            // Step 4 - Verify the login modal is displayed
            String logInModalXpath = "//*[@id=\"logInModalLabel\"]";  // Fixed escaping
            WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(10));
            WebElement logInModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(logInModalXpath)));
            Assert.assertTrue(logInModal.isDisplayed(), "Login modal is not displayed");
            test.pass("Login modal is displayed", 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Login_Modal_Displayed")).build());

            // Step 5 - Enter username
            String username = "JoshCab";
            String userNameFieldXpath = "//*[@id=\'loginusername\']";
            WebElement userNameField = webdriver.findElement(By.xpath(userNameFieldXpath));
            userNameField.sendKeys(username);
            test.pass("Entered Username", 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Entered_Username")).build());

            // Step 6 - Enter password
            String password = "3210+";
            String passwordFieldXpath = "//*[@id=\'loginpassword\']";
            WebElement passwordField = webdriver.findElement(By.xpath(passwordFieldXpath));
            passwordField.sendKeys(password);
            test.pass("Entered Password", 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Entered_Password")).build());

            // Step 7 - Click on Login Button
            String logInBtn2Xpath = "//button[text()='Log in']";
            WebElement logIn2Button = webdriver.findElement(By.xpath(logInBtn2Xpath));
            logIn2Button.click();
            test.info("Clicked on the sign up button", 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Sign_Up_Button_Clicked")).build());
            
            // Step 8 - Verify Welcome on homepage
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            
            System.out.println("Alert Message: " + alertText);
            
            if (!alertText.equals("This user already exist.") && !alertText.equals("Sign up successful.")) {
                Assert.fail("Unexpected alert message: " + alertText);
            }

            alert.accept();
            test.pass("Alert handled: " + alertText, 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Alert_Handled")).build());

            
        } catch (Exception e) {
            try {
                String screenshotPath = captureScreenshot(webdriver, "Test_Failed");
                test.fail("Test failed: " + e.getMessage(), 
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (Exception ex) {
                test.fail("Test failed: " + e.getMessage() + " [Additional error: " + ex.getMessage() + "]");
            }
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        try {
            if (webdriver != null) {
                test.info("Browser closed", 
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Browser_Closed")).build());
            }
        } catch (Exception e) {
            test.warning("Failed to capture screenshot during teardown: " + e.getMessage());
        } finally {
            if (webdriver != null) {
                webdriver.quit();
            }
        }
        tearDownExtentReports();
    }
}