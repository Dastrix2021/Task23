package com.dastrix.starters;

import com.dastrix.api.requests.Client;
import com.dastrix.print.EventPrinter;
import com.dastrix.print.Printer;
import com.dastrix.services.EventApiService;
import lombok.RequiredArgsConstructor;

import static com.dastrix.api.requests.Client.executor;
@RequiredArgsConstructor
public class ApiStarter implements Starter {
    Client client = new Client();
    EventApiService service = new EventApiService(client);
    Printer printer = new EventPrinter();
    @Override
    public void scrape() {
        service.getEvents(service.getEventsId(service.events()))
                .forEach(printer::print);
        executor.shutdown();
    }
}
