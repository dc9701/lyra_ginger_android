package tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;

import pages.LoginPage;
import pages.HomePage;

public class GingerLessonsTest extends BaseTest {
    private AndroidDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;
    private ObjectMapper objectMapper = new ObjectMapper();

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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

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

    /**
     * Navigate a few sample lessons.
     * 
     * - Navigate from Begin to every Finish endpoint (some panels allow choosing alternate paths, aka "Choice"), exiting on the final one.
	 * - For each panel, verify assertText is displayed.
	 * - When reach a Choice, exit [X] then re-open, verifying it returns to same Choice panel.
     * - Covers both forward and backward panel navigation.
     * 
     * NOTE: Lesson definitions are in src/pages/HomePage.java, jsonLessons text block.
     */
    @Test()
    public void testLessonNavigation() {
        JsonNode lessons;
        try {
            lessons = objectMapper.readTree(homePage.jsonLessons);
            
            for (JsonNode lesson : lessons) {
                Integer currentPanel = 0;
                Boolean finished = false;
                Boolean forward = true;
                Integer paths = 0;                                      // # alternate paths remaining to traverse, or zero if none.
                Stack<Integer> breadcrumbs = new Stack<Integer>();      // History of visited panels.

                homePage.homeExploreAll.click();                        // Display all viewed lessons.
                homePage.homeViewed.click();

                String lessonTitle = lesson.get("title").textValue();
                System.out.println("[DCC_DEBUG] Lesson Title: " + lessonTitle);
                driver.findElement(By.xpath(String.format("//android.view.View[contains(@content-desc,\"%s\")]", lessonTitle))).click();

                while (! finished) {
                    String panelText = lesson.get("panels").get(currentPanel).get("assertText").textValue();
                    System.out.println("  [DCC_DEBUG] Panel Text: " + panelText);

                    Assert.assertTrue(driver.findElement(By.xpath(String.format("//android.view.View[contains(@content-desc,\"%s\")]", panelText))).isDisplayed());

                    if (lesson.get("panels").get(currentPanel).get("navigation").size() > 1) {      // Reached a Choice panel.

                        if (paths == 0) {                           // First time reaching Choice panel.
                            paths = lesson.get("panels").get(currentPanel).get("navigation").size();
                            
                            // DCC - TODO: Add exit/re-enter verification.
                            
                        } else {                                    // Returned back to Choice panel after having traversed one path.
                            paths -= 1;
                        }
                        
                        // Click on current choice, then Next and update currentPanel.

                        String choiceText = lesson.get("panels").get(currentPanel).get("navigation").get(paths - 1).get("action").get(0).textValue();
                        driver.findElement(By.xpath(String.format("//android.view.View[contains(@content-desc,\"%s\")]", choiceText))).click();
                        homePage.lessonPanelNext.click();
                        currentPanel = lesson.get("panels").get(currentPanel).get("navigation").get(paths - 1).get("panel").intValue();
                        forward = true;

                    } else if (forward) {                           // Moving forward.
                        breadcrumbs.push(currentPanel);

                        if (lesson.get("panels").get(currentPanel).get("navigation").size() == 0) {
                            homePage.lessonPanelNext.click();
                            currentPanel += 1;                      // Default move forward one panel.
                        } else {
                            currentPanel = lesson.get("panels").get(currentPanel).get("navigation").get(0).get("panel").intValue();

                            if (currentPanel == 0) {                // Reached a Finish panel endpoint.
                                if (paths == 0) {
                                    homePage.lessonPanelFinish.click();
                                    finished = true;                // All done; exit lesson by clicking Finish.    
                                } else {
                                    currentPanel = breadcrumbs.pop();
                                    forward = false;
                                }
                            } else {
                                homePage.lessonPanelNext.click();   // Move forward to designated panel.
                            }
                        }
                    } 
                    
                    if (! forward) {                                // Moving backward.
                        if (breadcrumbs.size() > 0) {
                            homePage.lessonPanelBack.click();
                            currentPanel = breadcrumbs.pop();
                        } else {                                    // Reached the Beginning; switch directions.
                            forward = true;
                        }
                    }
                }
            }            
        } catch (Exception e) {
            throw new IllegalStateException("Failed to process jsonLessons:", e);
        }
    }

}