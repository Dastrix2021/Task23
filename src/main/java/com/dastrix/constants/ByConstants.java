package com.dastrix.constants;

import org.openqa.selenium.By;
public class ByConstants {
    public static final By BUTTON_EMAIL = By.cssSelector("button.tabs-button-type--uppercase:nth-child(3)");
    public static final By INPUT_LOGIN = By.xpath("//input[starts-with(@id,'login-')]");
    public static final By INPUT_PASSWORD = By.xpath("//input[starts-with(@id,'password-')]");
    public static final By BUTTON_SUBMIT = By.cssSelector("button.login__button:nth-child(1)");
    public static final By LINES = By.cssSelector(".top-sportline-prematch-events");
    public static final By FIRST_IN_TOP_LEAGUE = By.cssSelector("a[class^='list-item__inner']");
    public static final By CHECK_BODY = By.className("wrapper");
    public static final By HREF = By.cssSelector("a[class^='sport-event']");
    public static final By CHECK_SLIP_VIEW = By.className("bet-slip-view__body");
    public static final By CHECK_BRED = By.className("breadcrumb__title-text");
    public static final By WRAPPER = By.className("sport-event-details-market-group__wrapper");
    public static final By TITLE_LABEL = By.className("sport-event-details-market-group__title-label");
    public static final By RUNNER_HOLDER = By.className("sport-event-details-item__runner-holder");
    public static final By CF_LEFT = By.cssSelector("span[class$='coefficient--left']");
    public static final By CF_RIGHT = By.cssSelector("span[class$='coefficient--right']");
    public static final By CHECK_CONTENT = By.className("sport-event-details-market-group__content");
    public static final By HEADLINE = By.xpath("//span[@class='headline-info__day']");
    public static final By HEADLINE_INFO_TIME = By.className("headline-info__time");
    public static final By CHECK_GROUP_SOWN = By.className("group--shown");
    public final static By VISIBILITY_BALANCE = By.className("balance__text");
}
