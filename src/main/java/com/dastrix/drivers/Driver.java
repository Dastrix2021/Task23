package com.dastrix.drivers;

import com.dastrix.constants.ApiConstants;
import com.dastrix.constants.RegistrationConstants;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
public class Driver {
    public static ChromeDriver createDriver() {
        System.setProperty("webdriver.chrome.driver", RegistrationConstants.CHROME_DRIVER_PATH);
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
        return new WebDriverWait(driver, Duration.ofSeconds(ApiConstants.WAIT_CONNECTION_TIME));
    }
    public static void close(ChromeDriver driver) {
        driver.getDevTools().close();
        driver.close();
        driver.quit();
    }
}
