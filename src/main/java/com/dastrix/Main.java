package com.dastrix;
import org.openqa.selenium.WebDriverException;
public class Main {
    public static void main(String[] args) throws WebDriverException {
        //Before scraping, look at AuthorizationConstants
        Starter starter = new Starter();
        starter.scrap();
    }
}