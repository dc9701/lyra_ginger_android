package tests;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.Thread;
import java.time.Duration;

import pages.LoginPage;
import pages.HomePage;

public class GingerLessonsTest extends BaseTest {
    private AndroidDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;

    private final String USER_EMAIL = "dcc9701@gmail.com";
    private final String USER_PASSWORD = "Ginger-dcc9701";

    /**
     * Launch app and login
     * 
     * @throws IOException
     */
    @BeforeClass
    public void setUp() throws IOException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "emulator-5554");
        capabilities.setCapability("appPackage", "com.ginger");
        capabilities.setCapability("appActivity", "com.ginger.MainActivity");
        capabilities.setCapability("appWaitActivity", "com.ginger.MainActivity");
        capabilities.setCapability("automationName", "UIAutomator2");

        driver = new AndroidDriver(getServiceUrl(), capabilities);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        loginPage = new LoginPage(driver);
        loginPage.login(USER_EMAIL, USER_PASSWORD);
        homePage = new HomePage(driver);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test()
    public void testLessonNavigation() {
        homePage.lessonGettingStarted.click();

        // DCC - Remove this sleep() try/catch...
        try {
            Thread.sleep(1000);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

}