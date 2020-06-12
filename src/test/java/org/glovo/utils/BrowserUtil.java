package org.glovo.utils;

import io.cucumber.spring.CucumberTestContext;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Scope(CucumberTestContext.SCOPE_CUCUMBER_GLUE)
public class BrowserUtil {

    @Value("${headless}")
    private boolean headless;
    @Value("${browser}")
    private String browserType;
    @Value("${pageLoadTimeOutInSecs:10}")
    private long pageLoadTimeOut;
    @Value("${implicitWaitTimeOutInSecs:3}")
    private long implicitWaitTimeOutInSecs;
    @Value("${explicitWaitTimeOutInSecs:10}")
    private int explicitWaitTimeOutInSecs;
    @Value("${pollingIntervalInSecs:2}")
    private int pollingIntervalInSecs;

    private WebDriver browser;
    private WebDriverWait webDriverWait;
    private Actions actions;
    public String mainWindowHandle;

    private WebDriver getChromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(headless);
        System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriverBinaries/chromedriver.exe");
        return new ChromeDriver(chromeOptions);
    }

    public WebDriver getBrowser() {
        if (browser == null) {
            if (browserType.equalsIgnoreCase("chrome")) {
                browser = getChromeDriver();
            } else {
                browser = getChromeDriver();
            }
            browser.manage().deleteAllCookies();
            browser.manage().window().maximize();
            browser.manage().timeouts().pageLoadTimeout(pageLoadTimeOut, TimeUnit.SECONDS);
            browser.manage().timeouts().implicitlyWait(implicitWaitTimeOutInSecs, TimeUnit.SECONDS);
            mainWindowHandle = browser.getWindowHandle();
        }
        return browser;
    }

    public FluentWait<WebDriver> getWait() {
        if (webDriverWait == null) {
            webDriverWait = new WebDriverWait(getBrowser(), explicitWaitTimeOutInSecs, pollingIntervalInSecs);
            webDriverWait
                    .withTimeout(Duration.ofSeconds(explicitWaitTimeOutInSecs))
                    .pollingEvery(Duration.ofSeconds(pollingIntervalInSecs))
                    .ignoring(WebDriverException.class);
        }
        return webDriverWait;
    }

    public Actions getActions() {
        if (actions == null){
            actions = new Actions(getBrowser());
        }
        return actions;
    }
    public WebElement getElement(By by) {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
        return getWait().until(ExpectedConditions.elementToBeClickable(by));
    }

    public List<WebElement> getElements(By by) {
        return getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    public void click(By by) {
        getElement(by).click();
    }

    public void click(WebElement element) {
        getWait().until(ExpectedConditions.visibilityOf(element)).click();
    }

    public void sendKeys(By by, String text) {
        getElement(by).clear();
        getElement(by).sendKeys(text);
    }

    public void takeScreenShot(File file) throws IOException {
        File source = ((TakesScreenshot) getBrowser()).getScreenshotAs(OutputType.FILE);
        FileCopyUtils.copy(source, file);
    }

    public void switchToOriginalWindow() {
        for (String windowHandle :
                getBrowser().getWindowHandles()) {
            if (windowHandle.equals(mainWindowHandle)) {
                getBrowser().switchTo().window(windowHandle);
                return;
            }
        }
        Assertions.fail("Unable to switch to original window handle + " + mainWindowHandle);
    }
}
