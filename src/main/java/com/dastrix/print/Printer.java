package com.dastrix.print;

import com.dastrix.data.apis.ApiEvent;
import com.dastrix.data.selenium.Event;

import java.util.List;
public interface Printer {
    void print(List<Event> events);
    void print(ApiEvent event);

}
