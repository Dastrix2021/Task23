package com.dastrix.drivers.utils;

import com.dastrix.constants.ByConstants;
import com.dastrix.drivers.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class DriverUtil {
    public void getPage(ChromeDriver ch, String path) {
        WebDriverWait w = Driver.createWaitDriver(ch);
        ch.get(path);
        w.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }
    public void putKey(ChromeDriver ch, By by, String key) {
        WebDriverWait w = Driver.createWaitDriver(ch);
        try {
            ch.findElement(by).sendKeys(key);
            w.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        } catch (WebDriverException e) {
            e.printStackTrace();
            ch.quit();
        }
    }
    public void safeClick(ChromeDriver ch, By by) {
        WebDriverWait w = Driver.createWaitDriver(ch);
        try {
            w.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ByConstants.CHECK_BODY));
            w.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
            w.until(ExpectedConditions.elementToBeClickable(by));
            ch.findElement(by).click();
            w.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
            w.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ByConstants.CHECK_BODY));
        } catch (WebDriverException e) {
            e.printStackTrace();
            ch.quit();
        }
    }
    public void safeLinks(ChromeDriver ch, By by) {
        WebDriverWait w = Driver.createWaitDriver(ch);
        try {
            w.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ByConstants.CHECK_BODY));
            w.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
            var first = ch.findElement(by).getAttribute("href");
            ch.navigate().to(first);
            w.until(ExpectedConditions.urlToBe(first));
            w.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
            w.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ByConstants.CHECK_BODY));
        } catch (WebDriverException e) {
            e.printStackTrace();
            ch.quit();
        }
    }
    public void safeSportClick(ChromeDriver ch, String sport) {
        WebDriverWait w = Driver.createWaitDriver(ch);
        try {
            w.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ByConstants.CHECK_BODY));
            w.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
            ch.navigate().to(sport);
            w.until(ExpectedConditions.urlToBe(sport));
            w.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
            w.until(ExpectedConditions.visibilityOfElementLocated(ByConstants.CHECK_BODY));
        } catch (WebDriverException e) {
            e.printStackTrace();
            ch.quit();
        }
    }
    public void buttonRegClick(ChromeDriver ch, By by) {
        WebDriverWait w = Driver.createWaitDriver(ch);
        try {
            w.until(ExpectedConditions.elementToBeClickable(by));
            ch.findElement(by).click();
            w.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ByConstants.CHECK_BODY));
            w.until(ExpectedConditions.visibilityOfElementLocated(ByConstants.VISIBILITY_BALANCE));
        } catch (WebDriverException e) {
            e.printStackTrace();
            ch.quit();
        }
    }

    public void safeClickWithSleep(ChromeDriver ch, By by, String expectedUrl) {
        WebDriverWait w = Driver.createWaitDriver(ch);
        try {
            w.until(ExpectedConditions.visibilityOfElementLocated(by));
            w.until(ExpectedConditions.presenceOfElementLocated(by));
            ch.findElement(by).click();
            w.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ByConstants.CHECK_BODY));
            w.until(ExpectedConditions.urlToBe(expectedUrl));
        } catch (WebDriverException e) {
            e.printStackTrace();
            ch.quit();
        }
    }
    public Long getMatchId(WebDriver driver) {
        WebDriverWait waitDriver = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitDriver.until(webDriver -> ((JavascriptExecutor)webDriver).executeScript("return document.readyState").equals("complete"));
        String str = "";
        try {
            str = driver.getCurrentUrl().replaceAll("[^0-9]", "")
                    .substring(0, 16);
        } catch (WebDriverException e) {
            e.printStackTrace();
            driver.close();
            driver.quit();
        }
        return Long.parseLong(str);
    }
}
