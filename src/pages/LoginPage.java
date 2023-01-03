package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    protected WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        if (!loginButton.isDisplayed()) {
            throw new IllegalStateException("This is not the LoginPage;" +
                    " current page is: " + driver.getCurrentUrl());
        }
    }

    /*
     * Page actions used in tests.
     */

    public void login(String userEmail, String userPassword) {
        loginButton.click();
        emailAddress.click(); 
        emailAddress.sendKeys(userEmail);
        nextButton.click();
        password.click();
        password.sendKeys(userPassword);
        continueButton.click();
        notNowButton.click();           // Dismiss sign up for notifications dialog.
    }

    /*
     * PageFactory elements used in page actions and test assertions.
     */

    @FindBy(how = How.XPATH, using = "//android.widget.Button[contains(@content-desc,'Continue')]")
    public WebElement continueButton;

    @FindBy(how = How.XPATH, using = "//android.widget.EditText[contains(@text,'Email')]")
    public WebElement emailAddress;

    @FindBy(how = How.XPATH, using = "//android.widget.Button[contains(@content-desc,'Log in')]")
    public WebElement loginButton;

    @FindBy(how = How.XPATH, using = "//android.widget.Button[contains(@content-desc,'Next')]")
    public WebElement nextButton;

    @FindBy(how = How.XPATH, using = "//android.widget.Button[contains(@content-desc,'Not now')]")
    public WebElement notNowButton;

    @FindBy(how = How.XPATH, using = "//android.widget.EditText[contains(@text,'Password')]")
    public WebElement password;

}
