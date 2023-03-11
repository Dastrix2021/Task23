package com.dastrix.starters;

import com.dastrix.constants.UrlConstants;
import com.dastrix.data.selenium.Event;
import com.dastrix.drivers.Driver;
import com.dastrix.print.EventPrinter;
import com.dastrix.print.Printer;
import com.dastrix.services.EventSeleniumService;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
public class SeleniumStarter implements Starter {
    public final static Byte THREAD_NUM = 3;
    ChromeDriver driver;
    ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUM);
    Printer printer = new EventPrinter();
    EventSeleniumService scraper = new EventSeleniumService();
    CompletableFuture<List<Event>> allEventsFuture;
    List<String> sportList() {
        List<String> events = new ArrayList<>();
        events.add(UrlConstants.SOCCER);
        events.add(UrlConstants.BASKETBALL);
        events.add(UrlConstants.TENNIS);
        events.add(UrlConstants.HOCKEY);
        return events;
    }
    @Override
    public void scrape() {
        driver = Driver.createDriver();
        scraper.getAuth(driver);
        sportList().forEach(event -> {
            scraper.getSport(driver, event);
            Set<String> link = scraper.listLinks(driver);
            if (!link.isEmpty()) {
                List<CompletableFuture<Event>> eventsList = new ArrayList<>();
                link.forEach(l -> {
                    eventsList.add(scraper.getCompletableEvent(driver, l));
                    CompletableFuture<Void> eventsFutures = CompletableFuture.allOf(
                            eventsList.toArray(new CompletableFuture[0]));
                    allEventsFuture = eventsFutures
                            .thenApply(v -> eventsList.stream()
                                    .map(CompletableFuture::join)
                                    .collect(Collectors.toList()));
                    try {
                        allEventsFuture.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            allEventsFuture.thenAccept(s -> printer.print(s));
        });
        Driver.close(driver);
        executor.shutdown();
    }
}
