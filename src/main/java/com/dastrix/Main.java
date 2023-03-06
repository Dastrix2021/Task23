package com.dastrix;
import com.dastrix.starters.ApiStarter;
import com.dastrix.starters.SeleniumStarter;
import com.dastrix.starters.Starter;
import org.openqa.selenium.WebDriverException;
public class Main {
    public static void main(String[] args) throws WebDriverException {
        Starter starter = new ApiStarter();
        starter.scrape();
    }
}