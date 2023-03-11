package com.dastrix.services;

import com.dastrix.constants.ByConstants;
import com.dastrix.constants.UrlConstants;
import com.dastrix.data.selenium.Date;
import com.dastrix.data.selenium.Coefficient;
import com.dastrix.data.selenium.Event;
import com.dastrix.data.selenium.Market;
import com.dastrix.drivers.Driver;
import com.dastrix.drivers.utils.DriverUtil;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
public class EventSeleniumService {
    public static final String EMAIL = "some@gmail.com";
    public static final String PASSWORD = "p4s$wOrD";
    DriverUtil navigate = new DriverUtil();
    public void getAuth(ChromeDriver driver) {
        navigate.getPage(driver, UrlConstants.LOGIN_URL);
        navigate.safeClick(driver, ByConstants.BUTTON_EMAIL);
        navigate.putKey(driver, ByConstants.INPUT_LOGIN, EMAIL);
        navigate.putKey(driver, ByConstants.INPUT_PASSWORD, PASSWORD);
        navigate.buttonRegClick(driver, ByConstants.BUTTON_SUBMIT);
        navigate.safeClickWithSleep(driver, ByConstants.LINES, UrlConstants.AFTER_LINE_URL);
    }
    public void getSport(ChromeDriver driver, String sport) {
            navigate.safeSportClick(driver, sport);
    }
    public Set<String> listLinks(ChromeDriver driver) {
        Set<String> linksList = new HashSet<>();
        WebDriverWait w = Driver.createWaitDriver(driver);
        try {
            var s = w.until(ExpectedConditions.visibilityOfElementLocated(ByConstants.CHECK_GROUP_SOWN)).isDisplayed();
            if (!s)
                return linksList;
            navigate.safeLinks(driver, ByConstants.FIRST_IN_TOP_LEAGUE);
            driver.findElements(ByConstants.HREF)
                    .forEach(e -> linksList.add(e.getAttribute("href")));
        } catch (NoSuchElementException e) {
            System.out.println("Відсутня топ ліга: " + driver.getCurrentUrl());
            driver.navigate().to(UrlConstants.AFTER_LINE_URL);
            w.until(ExpectedConditions.visibilityOfElementLocated(ByConstants.CHECK_SLIP_VIEW));
            return linksList;
        }
        return linksList;
    }
    public CompletableFuture<Event> getCompletableEvent(ChromeDriver driver, String url) {
        return CompletableFuture.supplyAsync(() -> {
            Event event = null;
            WebDriverWait w = Driver.createWaitDriver(driver);
            try {
                List<String> header = new ArrayList<>();
                List<Coefficient> cfList = new ArrayList<>();
                driver.navigate().to(url);
                w.until(ExpectedConditions.urlToBe(url));
                w.until(ExpectedConditions.visibilityOfElementLocated(ByConstants.CHECK_CONTENT));
                var id = navigate.getMatchId(driver);
                var date = driver.findElement(ByConstants.HEADLINE).getText();
                var time = driver.findElement(ByConstants.HEADLINE_INFO_TIME).getText();
                w.until(ExpectedConditions.visibilityOfElementLocated(ByConstants.CHECK_BRED));
                driver.findElements(ByConstants.CHECK_BRED).forEach(e -> header.add(e.getText()));
                driver.findElements(ByConstants.WRAPPER).forEach(webElement -> {
                    List<Market> marketList = new ArrayList<>();
                    w.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
                    var title = webElement.findElement(ByConstants.TITLE_LABEL).getText();
                    webElement.findElements(ByConstants.RUNNER_HOLDER).
                            forEach(el -> {
                                var left = el.findElement(ByConstants.CF_LEFT).getText();
                                var right = el.findElement(ByConstants.CF_RIGHT).getText();
                                if (!left.contains("-"))
                                    marketList.add(new Market(left, right));
                            });
                    cfList.add(new Coefficient(title, marketList));
                });
                event = new Event(id, header, cfList, new Date(date, time));
                driver.navigate().to(UrlConstants.AFTER_LINE_URL);
                w.until(ExpectedConditions.urlToBe(UrlConstants.AFTER_LINE_URL));
            } catch (WebDriverException | IndexOutOfBoundsException e) {
                e.printStackTrace();
                driver.quit();
            }
            return event;
        });
    }
}