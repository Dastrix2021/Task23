package com.dastrix.drivers;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Collections;
public class Driver {
    public final static Byte WAIT_CONNECTION_TIME = 30;
    private static final String CHROME_DRIVER_PATH = "chromedriver.exe";
    public static ChromeDriver createDriver() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.addArguments(
                "--headless",
                "--window-size=1920,1200",
                "--ignore-certificate-errors",
                "--enable-javascript");
        return new ChromeDriver(options);
    }
    public static WebDriverWait createWaitDriver(ChromeDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_CONNECTION_TIME));
    }
    public static void close(ChromeDriver driver) {
        driver.getDevTools().close();
        driver.close();
        driver.quit();
    }
}
