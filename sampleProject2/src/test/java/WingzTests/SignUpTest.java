package WingzTests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import Utils.BaseTest;
import com.aventstack.extentreports.*;
import java.time.Duration;

public class SignUpTest extends BaseTest {

    private WebDriver webdriver;
    private ExtentTest test;

    @BeforeMethod
    public void setUp() {
        // Set up ExtentReports
        setupExtentReports();
        test = extent.createTest("SignUpTest", "Verify SignUp functionality");

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
    public void signUpTest() {  // Changed method name to follow Java conventions (lowercase)
        
            // Step 1 - Go to the URL
            String url = "https://auth.wingz.me/auth/signup";
            webdriver.get(url);
            test.info("Navigated to " + url, 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Navigated_To_URL")).build());

            // Step 2 - Verify if SignUp page is displayed
            String signUpFormXpath = "//h2[text()='Sign Up']";
            WebElement signUpPageElement = webdriver.findElement(By.xpath(signUpFormXpath));
            Assert.assertTrue(signUpPageElement.isDisplayed(), "SignUp page is not displayed");
            test.pass("SignUp page is displayed", 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "SignUp_Page_Displayed")).build());

            // Step 3 - Enter First Name
            String firstName = "Joshua";
            String firstNameFieldXpath = "//input[@name='firstName']";
            WebElement firstNameField = webdriver.findElement(By.xpath(firstNameFieldXpath));
            firstNameField.sendKeys(firstName);
            test.pass("Entered Firstname", 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Entered_Firstname")).build());

            // Step 4 - Enter Email Address
            String emailAddress = "newtestemail77@gmail.com";
            String emailAddressFieldXpath = "//input[@name='email']";
            WebElement emailAddressField = webdriver.findElement(By.xpath(emailAddressFieldXpath));
            emailAddressField.sendKeys(emailAddress);
            test.pass("Entered Email Address", 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Entered_Email_Address")).build());

            // Step 5 - Enter password
            String password = "DummyP@assword";
            String passwordFieldXpath = "//input[@name='password']";
            WebElement passwordField = webdriver.findElement(By.xpath(passwordFieldXpath));
            passwordField.sendKeys(password);
            test.pass("Entered Password", 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Entered_Password")).build());

            // Step 6 - Click on mobile phone dropdown
            String countryCodeDropdownXpath = "//div[@class='country-codes-popover ng-binding' and contains(text(), 'US')]";
            WebElement countryCodeButton = webdriver.findElement(By.xpath(countryCodeDropdownXpath));
            countryCodeButton.click();
            test.info("Clicked on the country code button", 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Country_Code_Button_Clicked")).build());
            
            // Step 7 - Enter country name
            String countryName = "Philippines";
            String searchCountryFieldXpath = "//input[@placeholder='Search Country ...' and @type='text']";
            WebElement searchCountryField = webdriver.findElement(By.xpath(searchCountryFieldXpath));
            searchCountryField.sendKeys(countryName);
            test.pass("Entered Country Name", 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Entered_Country_Name")).build());

            
            // Step 8 - Select Country
            String countryPHXpath = "//a[contains(text(), 'Philippines (+63)')]";
            WebElement countryCodePH = webdriver.findElement(By.xpath(countryPHXpath));
            countryCodePH.click();
            test.info("Selected Country", 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Country_Selected")).build());

            // Step 9 - Enter mobile number
            String mobilePhone = "9065556969";
            String mobilePhoneFieldXpath = "//input[@name='phoneNumber.number' and @type='tel']";
            WebElement mobilePhoneField = webdriver.findElement(By.xpath(mobilePhoneFieldXpath));
            mobilePhoneField.sendKeys(mobilePhone);
            test.pass("Entered Phone Number", 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Entered_Phone_Number")).build());
 
            
            // Step 10 - Select Terms box            
            String termsBoxXpath = "//input[@id='tosAccepted']";
            WebElement termsBoxButton = webdriver.findElement(By.xpath(termsBoxXpath));
            termsBoxButton.click();
            test.info("Clicked on terms box", 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Terms_Box_Clicked")).build());
  
            // Step 11 - Click on submit button
            String submitButtonXpath = "//button[contains(text(), 'Sign Up')]";
            WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(10));
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(submitButtonXpath)));
            submitButton.click();
            test.info("Clicked on submit button", 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Submit_Button_Clicked")).build());
  
            // Step 12 - Verify book now page is displayed
            String bookNowXpath = "//a[contains(text(), 'Book Now')]";
            WebElement bookNowElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(bookNowXpath)));
            Assert.assertTrue(bookNowElement.isDisplayed(), "Book now page is not displayed");
            test.pass("Book now page is displayed", 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Book_Now_Page_Displayed")).build());

            
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