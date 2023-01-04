package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    protected WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        if (! homePageTitle.isDisplayed()) {
            throw new IllegalStateException("This is not the HomePage;" +
                    " current page is: " + driver.getCurrentUrl());
        }
    }

    @FindBy(how = How.XPATH, using = "//android.view.View[contains(@content-desc,\"Home\")]")
    public WebElement homePageTitle;

    @FindBy(how = How.XPATH, using = "//android.view.View[contains(@content-desc,\"EXPLORE ALL\")]")
    public WebElement homeExploreAll;

    @FindBy(how = How.XPATH, using = "//android.view.View[contains(@content-desc,\"Viewed\")]")
    public WebElement homeViewed;

    @FindBy(how = How.XPATH, using = "//android.widget.Button[contains(@content-desc,\"Begin\")]")
    public WebElement lessonPanelBegin;

    @FindBy(how = How.XPATH, using = "//android.widget.Button[contains(@content-desc,\"back\")]")
    public WebElement lessonPanelBack;

    @FindBy(how = How.XPATH, using = "//android.widget.Button[contains(@content-desc,\"close\")]")
    public WebElement lessonPanelClose;

    @FindBy(how = How.XPATH, using = "//android.widget.Button[contains(@content-desc,\"next\")]")
    public WebElement lessonPanelNext;

    @FindBy(how = How.XPATH, using = "//android.widget.Button[contains(@content-desc,\"Finish\")]")
    public WebElement lessonPanelFinish;

    public String jsonLessons = """
    [
        {
            "title": "Getting Started: What is Ginger coaching?",
            "panels": [
                {"assertText": "Getting Started: What is Ginger Coaching?", "navigation": [{"action": ["Begin"], "panel": 1}]},
                {"assertText": "What is Ginger Coaching?", "navigation": []},
                {"assertText": "What types of topics and issues do coaches typically help with?", "navigation": []},
                {"assertText": "What does a coaching session look like?", "navigation": []},
                {"assertText": "How can I make the most of coaching?", "navigation": []},
                {"assertText": "Want to read more about Ginger coaching and therapy?", "navigation": [
                    {"action": ["Tap here to finish", "next"], "panel": 6},
                    {"action": ["Read more", "next"], "panel": 7}
                ]},
                {"assertText": "Time to get started!", "navigation": [{"action": ["Finish"], "panel": 0}]},
                {"assertText": "Here’s a bit more about Ginger coaching", "navigation": []},
                {"assertText": "Therapy or psychiatry sessions may be available to you", "navigation": [{"action": ["Finish"], "panel": 0}]}
            ]
        },
        {
            "title": "Take a Mental Health Break From the News",
            "panels": [
                {"assertText": "Take a Mental Health Break From the News",  "navigation": [{"action": ["Begin"], "panel": 1}]},
                {"assertText": "(1) Pay attention to how the news makes you feel as you engage with it, and afterward.", "navigation": []},
                {"assertText": "(2) Limit the number of times a day you look at the news.", "navigation": []},
                {"assertText": "(3) Find pleasant distractions.", "navigation": [{"action": ["Finish"], "panel": 0}]}
            ]
        },
        {
            "title": "Identifying Automatic Thoughts",
            "panels": [
                {"assertText": "Identifying Automatic Thoughts", "navigation": [{"action": ["Begin"], "panel": 1}]},
                {"assertText": "Choose one of these automatic thoughts that you've had.", "navigation": [
                    {"action": ["My life is a mess", "next"], "panel": 2},
                    {"action": ["That person must not like me", "next"], "panel": 3},
                    {"action": ["I'll never find someone", "next"], "panel": 4},
                    {"action": ["I'm a failure", "next"], "panel": 5},
                    {"action": ["No one understands me", "next"], "panel": 6}
                ]},
                {"assertText": "It assumes that your life is a mess", "navigation": [{"action": ["next"], "panel": 7}]},
                {"assertText": "You’re supposed to have coffee with an old friend.", "navigation": [{"action": ["next"], "panel": 7}]},
                {"assertText": "You’re studying at a coffee shop and you spot a cute stranger.", "navigation": [{"action": ["next"], "panel": 7}]},
                {"assertText": "It assumes that the reason you didn’t get the job was your fault", "navigation": [{"action": ["next"], "panel": 7}]},
                {"assertText": "You’re on the phone with a friend", "navigation": [{"action": ["next"], "panel": 7}]},
                {"assertText": "Tracking Automatic Thoughts", "navigation": [{"action": ["Finish"], "panel": 0}]}
            ]
        }
    ]            
    """;

}
