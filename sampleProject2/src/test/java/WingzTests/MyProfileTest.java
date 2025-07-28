package WingzTests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import Utils.BaseTest;
import com.aventstack.extentreports.*;
import org.testng.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.function.Function;

public class MyProfileTest extends BaseTest {

    private WebDriver webdriver;
    private ExtentTest test;

    @BeforeMethod
    public void setUp() {
        // Set up ExtentReports
        setupExtentReports();
        test = extent.createTest("MyProfileTest", "Verify My Profile functionality");

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        webdriver = new ChromeDriver();
        webdriver.manage().window().maximize();
        try {
            String screenshotPath = captureScreenshot(webdriver, "Browser_Launched");
            test.info("Browser launched and maximized",
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception e) {
            test.fail("Browser status update failed: " + e.getMessage());
        }
    }

    @Test(priority = 2)
    public void myProfileTest() {
        try {
            // Step 1 - Sign In
            performSignIn(webdriver, "newtestemail77@gmail.com", "DummyP@assword");
            // Wait for Book Now link to confirm sign-in
            WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'Book Now')]")));
            test.pass("Signed in successfully",
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Signed_In")).build());

            // Step 2: Click to account page
            String accountXpath = "//a[normalize-space()='Account' and contains(@href, '/account')]";
            WebElement accountPage = webdriver.findElement(By.xpath(accountXpath));
            accountPage.click();
            // Wait for Edit Account page heading
            String editAccountPageXpath = "//h2[normalize-space()='Edit Account']";
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(editAccountPageXpath)));
            test.info("Navigated to account page",
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Account_Page_Navigated")).build());

            // Step 3: Verify Edit Account page
            WebElement editAccountPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(editAccountPageXpath)));
            Assert.assertTrue(editAccountPage.isDisplayed(), "Edit Account page is not displayed");
            test.pass("Edit Account page is displayed", 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Edit_Account_Page_Displayed")).build());

            // Step 4: Navigate to My Profile page
            String myProfilePageClickXpath = "//a[normalize-space()='My Profile' and contains(@href, '/account/profile')]";
            WebElement myProfilePageClick = webdriver.findElement(By.xpath(myProfilePageClickXpath));
            myProfilePageClick.click();
            // Wait for My Profile page heading
            String myProfilePageXpath = "//h2[normalize-space()='My Profile']";
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(myProfilePageXpath)));
            test.info("Navigated to My Profile page",
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "My_Profile_Page_Navigated")).build());

            // Step 5: Upload image
            String imageInputXpath = "//input[@type='file' and @accept='image/*']";
            WebElement imageInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(imageInputXpath)));
            String imagePath = System.getProperty("user.dir") + "\\src\\test\\resources\\test-images\\TestImage2.png"; // Make sure this file exists
            imageInput.sendKeys(imagePath);
            // Wait for the file input's value to be updated (file name present)
            try {
                wait.until(new Function<WebDriver, Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        WebElement input = driver.findElement(By.xpath("//input[@type='file' and @accept='image/*']"));
                        return !input.getAttribute("value").isEmpty();
                    }
                });
            } catch (Exception ex) {
                // Some browsers may not populate the value attribute for security reasons; optionally log a warning here
            }
            test.info("Image uploaded: " + imagePath,
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Image_Uploaded")).build());

            // Step 6: Verify My Profile page and get first name value
            WebElement myProfilePage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(myProfilePageXpath)));
            Assert.assertTrue(myProfilePage.isDisplayed(), "My Profile page is not displayed");
            test.pass("My Profile page is displayed", 
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "My_Profile_Page_Displayed")).build());
            // Get and log the first name value
            String firstNameFieldXpath = "//input[@name='firstName']";
            WebElement firstNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(firstNameFieldXpath)));
            String firstNameValue = firstNameField.getAttribute("value");
            test.info("First name in the field: " + firstNameValue,
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "First_Name_Field_Value")).build());

            // Step 7: Select Gender
            String genderXpath = "//span[contains(@class, 'wz-label') and normalize-space()='Female']";
            WebElement genderClick = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(genderXpath)));
            genderClick.click();
            // Wait for gender to be selected (if possible, e.g., class change)
            Thread.sleep(500); // Small delay to allow UI update
            test.info("Gender is selected",
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Gender_Selected")).build());

            // Step 8: Input a new value into the first name field
            String newFirstName = "Wilson";
            firstNameField.clear();
            firstNameField.sendKeys(newFirstName);
            // Wait for the value to be updated in the field
            wait.until(ExpectedConditions.attributeToBe(firstNameField, "value", newFirstName));
            test.info("First name field updated to: " + newFirstName,
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "First_Name_Field_Updated")).build());
           
            // Step 9: Enter Last Name
            String lastNameFieldXpath = "//input[@name='lastName']";           
            WebElement lastNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(lastNameFieldXpath)));
            String newLastName = "Cab";
            lastNameField.clear();
            lastNameField.sendKeys(newLastName);
            // Wait for the value to be updated in the field
            wait.until(ExpectedConditions.attributeToBe(lastNameField, "value", newLastName));
            test.info("Last name field updated to: " + newLastName,
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Last_Name_Field_Updated")).build());
      
            // Step 10: Enter Home City
            String homeCityFieldXpath = "//input[@name='location' and @placeholder='Home city']";
            WebElement homeCityField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(homeCityFieldXpath)));
            String newHomeCity = "San Francisco";
            homeCityField.clear();
            homeCityField.sendKeys(newHomeCity);
            // Wait for the value to be updated in the field
            wait.until(ExpectedConditions.attributeToBe(homeCityField, "value", newHomeCity));
            test.info("Home city field updated to: " + newHomeCity,
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Home_City_Field_Updated")).build());
      
            // Step 11: Enter Bio
            String bioFieldXpath = "//textarea[@name='bio']";
            WebElement bioField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(bioFieldXpath)));
            String newBio = "This is my new bio for testing purposes.";
            bioField.clear();
            bioField.sendKeys(newBio);
            // Wait for the value to be updated in the field
            wait.until(ExpectedConditions.attributeToBe(bioField, "value", newBio));
            test.info("Bio field updated to: " + newBio,
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Bio_Field_Updated")).build());
        
            // Step 12: Click Save button
            String saveButtonXpath = "//button[normalize-space()='Save' and @type='submit']";
            WebElement saveBtnClick = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(saveButtonXpath)));
            saveBtnClick.click();
            // Wait for save button to be clicked
            Thread.sleep(500); // Small delay to allow UI update
            test.info("Save Button Clicked",
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Save_Button_Click")).build());

            // Step 13: Verify all data are saved
            String confirmSavedXpath = "//button[contains(text(), 'Saved !')]";
            WebDriverWait longWait = new WebDriverWait(webdriver, Duration.ofSeconds(30));
            longWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(confirmSavedXpath)));
            Thread.sleep(500); // Small delay to allow UI update
            test.info("Confirmed Saved",
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(webdriver, "Confirmed_Saved")).build());

        
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
