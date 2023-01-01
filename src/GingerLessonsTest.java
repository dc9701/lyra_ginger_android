import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class GingerLessonsTest extends BaseTest {
    private AndroidDriver driver;
    private final String SEARCH_ACTIVITY = ".app.SearchInvoke";
    private final String ALERT_DIALOG_ACTIVITY = ".app.AlertDialogSamples";

    private final String PACKAGE = "com.ginger";
    private final String MAIN_ACTIVITY = "com.ginger.MainActivity";

    @BeforeClass
    public void setUp() throws IOException {
        /* DCC
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "../apps");
        File app = new File(appDir.getCanonicalPath(), "ApiDemos-debug.apk");
        */

        DesiredCapabilities capabilities = new DesiredCapabilities();
        /*
        'deviceName' capability only affects device selection if you run the test in a cloud
        environment. It has no effect if the test is executed on a local machine.
        */
        capabilities.setCapability("deviceName", "emulator-5554");

        /*
        It makes sense to set device udid if there are multiple devices/emulators
        connected to the local machine. Run `adb devices -l` to check which devices
        are online and what are their identifiers.
        If only one device is connected then this capability might be omitted
        */
        // capabilities.setCapability("udid", "ABCD123456789");

        /*
        It is recommended to set a full path to the app being tested.
        Appium for Android supports application .apk and .apks bundles.
        If this capability is not set then your test starts on Dashboard view.
        It is also possible to provide an URL where the app is located.
        */
        // DCC capabilities.setCapability("app", app.getAbsolutePath());

        /*
        By default Appium tries to autodetect the main application activity,
        but if you app's very first activity is not the main one then
        it is necessary to provide its name explicitly. Check
        https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/android/activity-startup.md
        for more details on activities selection topic.
        */
        // capabilities.setCapability("appActivity", "com.myapp.SplashActivity");
        // capabilities.setCapability("appPackage", "com.myapp");
        // capabilities.setCapability("appWaitActivity", "com.myapp.MainActivity");
        // DCC capabilities.setCapability("app", "com.ginger");
        capabilities.setCapability("appPackage", "com.ginger");
        capabilities.setCapability("appActivity", "com.ginger.MainActivity");
        capabilities.setCapability("appWaitActivity", "com.ginger.MainActivity");

        /*
        Appium for Android supports multiple automation backends, where
        each of them has its own pros and cons. The default one is UIAutomator2.
        */
        capabilities.setCapability("automationName", "UIAutomator2");
        // capabilities.setCapability("automationName", "Espresso");

        /*
        There are much more capabilities and driver settings, that allow
        you to customize and tune your test to achieve the best automation
        experience. Read http://appium.io/docs/en/writing-running-appium/caps/
        and http://appium.io/docs/en/advanced-concepts/settings/
        for more details.

        Feel free to visit our forum at https://discuss.appium.io/
        if you have more questions.
        */

        driver = new AndroidDriver(getServiceUrl(), capabilities);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test()
    public void testSendKeys() {
        // DCC driver.startActivity(new Activity(PACKAGE, SEARCH_ACTIVITY));
        driver.startActivity(new Activity(PACKAGE, MAIN_ACTIVITY));
        WebElement searchBoxEl = (WebElement) driver.findElement(By.id("txt_query_prefill"));
        searchBoxEl.sendKeys("Hello world!");
        WebElement onSearchRequestedBtn = (WebElement) driver.findElement(By.id("btn_start_search"));
        onSearchRequestedBtn.click();
        WebElement searchText = (WebElement) new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/search_src_text")));
        String searchTextValue = searchText.getText();
        Assert.assertEquals(searchTextValue, "Hello world!");
    }

    @Test
    public void testOpensAlert() {
        // Open the "Alert Dialog" activity of the android app
        driver.startActivity(new Activity(PACKAGE, ALERT_DIALOG_ACTIVITY));

        // Click button that opens a dialog
        WebElement openDialogButton = (WebElement) driver.findElement(By.id("io.appium.android.apis:id/two_buttons"));
        openDialogButton.click();

        // Check that the dialog is there
        WebElement alertElement = (WebElement) driver.findElement(By.id("android:id/alertTitle"));
        String alertText = alertElement.getText();
        Assert.assertEquals(alertText, "Lorem ipsum dolor sit aie consectetur adipiscing\nPlloaso mako nuto siwuf cakso dodtos anr koop.");
        WebElement closeDialogButton = (WebElement) driver.findElement(By.id("android:id/button1"));

        // Close the dialog
        closeDialogButton.click();
    }
}