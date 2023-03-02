package com.dastrix.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
public interface DriversUtils {
    void getPage(ChromeDriver ch, String path);
    void putKey(ChromeDriver ch, By by, String key);
    void safeClick(ChromeDriver ch, By by);
    void buttonRegClick(ChromeDriver ch, By by);
    void safeLinks(ChromeDriver ch, By by);
    void safeSportClick(ChromeDriver ch, String sport);
    void safeClickWithSleep(ChromeDriver ch, By by, String expectedUrl);
    Long getMatchId(WebDriver driver);
}
