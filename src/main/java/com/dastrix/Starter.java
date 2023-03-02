package com.dastrix;

import com.dastrix.constants.PathsConstants;
import com.dastrix.data.Event;
import com.dastrix.drivers.Driver;
import com.dastrix.print.Printer;
import com.dastrix.print.EventPrinter;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
public class Starter {
    ChromeDriver driver;
    private final Byte THREAD_NUM = 3;
    ExecutorService executors = Executors.newFixedThreadPool(THREAD_NUM);
    Printer printer = new EventPrinter();
    Scraper scraper = new Scraper();
    CompletableFuture<List<Event>> allEventsFuture;
    List<String> sportList() {
        List<String> events = new ArrayList<>();
        events.add(PathsConstants.SOCCER);
        events.add(PathsConstants.BASKETBALL);
        events.add(PathsConstants.TENNIS);
        events.add(PathsConstants.HOCKEY);
        return events;
    }
    public void scrap() {
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
        executors.shutdown();
    }
}
