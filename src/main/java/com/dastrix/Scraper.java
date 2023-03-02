package com.dastrix;

import com.dastrix.constants.ByConstants;
import com.dastrix.constants.PathsConstants;
import com.dastrix.constants.AuthorizationConstants;
import com.dastrix.data.Coefficient;
import com.dastrix.data.Event;
import com.dastrix.data.Market;
import com.dastrix.data.date.Date;
import com.dastrix.drivers.Driver;
import com.dastrix.utils.DriverUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Scraper {
    DriverUtil navigate = new DriverUtil();
    private static final Byte THREAD_NUM = 3;
    static ExecutorService executors = Executors.newFixedThreadPool(THREAD_NUM);
    public void getAuth(ChromeDriver driver) {
        navigate.getPage(driver, AuthorizationConstants.LOGIN_URL);
        navigate.safeClick(driver, ByConstants.BUTTON_EMAIL);
        navigate.putKey(driver, ByConstants.INPUT_LOGIN, AuthorizationConstants.EMAIL);
        navigate.putKey(driver, ByConstants.INPUT_PASSWORD, AuthorizationConstants.PASSWORD);
        navigate.buttonRegClick(driver, ByConstants.BUTTON_SUBMIT);
        navigate.safeClickWithSleep(driver, ByConstants.LINES, PathsConstants.AFTER_LINE_URL);
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
            driver.navigate().to(PathsConstants.AFTER_LINE_URL);
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
                driver.navigate().to(PathsConstants.AFTER_LINE_URL);
                w.until(ExpectedConditions.urlToBe(PathsConstants.AFTER_LINE_URL));
            } catch (WebDriverException | IndexOutOfBoundsException e) {
                e.printStackTrace();
                driver.quit();
            }
            return event;
        }, executors);
    }
    public static void executorClose() {
        executors.shutdown();
    }
}