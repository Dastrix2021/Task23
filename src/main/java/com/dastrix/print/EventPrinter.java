package com.dastrix.print;

import com.dastrix.data.apis.ApiEvent;
import com.dastrix.data.selenium.Event;

import java.util.List;

public class EventPrinter implements Printer {
    @Override
    public void print(List<Event> events) {
        events.forEach(e -> {
            var size = e.getSportInfo().size();
            var first = e.getSportInfo().subList(0, size - 1);
            var last = e.getSportInfo().get(size - 1);
            first.forEach(el -> System.out.print(el + " "));
            System.out.printf("%n\t%s, %s, %s%n", last, e.getDate().getDateTime(), e.getId());
            e.getCoefficient().forEach(c -> {
                System.out.printf("\t\t%s%n", c.getName());
                c.getMarket().forEach(m -> System.out.printf("\t\t\t%s, %s%n", m.getLeft(), m.getRight()));
            });
        });
    }
    @Override
    public void print(ApiEvent e) {
        var sport = e.getLeague().getSport().getName();
        var region = e.getLeague().getRegion().getName();
        var league = e.getLeague().getName();
        var name = e.getName();
        var time = e.getKickoff().toString();
        var id = e.getId();
        System.out.printf("%s, %s %s%n", sport, region, league);
        System.out.printf("\t%s, %s, %d%n", name, time, id);
        e.getMarkets().forEach(m -> {
            System.out.printf("\t\t%s%n", m.getName());
            m.getRunners().forEach(r ->
                    System.out.printf("\t\t\t%s, %s, %d%n",
                            r.getName(), r.getPrice(), r.getId())
            );
        });
    }
}
