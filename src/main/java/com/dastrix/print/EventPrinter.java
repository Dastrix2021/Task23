package com.dastrix.print;

import com.dastrix.data.Event;
import java.util.List;
public class EventPrinter implements Printer {
    @Override
    public void print(List<Event> event) {
        event.forEach(e -> {
            var size = e.getSportInfo().size();
            var first = e.getSportInfo().subList(0, size - 1);
            var last = e.getSportInfo().get(size - 1);

            first.forEach(el -> System.out.print(el + " "));
            System.out.printf("%n\t%s, %s, %s%n" , last, e.getDate().getDateTime(), e.getId());

            e.getCoefficient().forEach(c -> {
                System.out.printf("\t\t%s%n", c.getName());
                c.getMarket().forEach(m -> {
                    System.out.printf("\t\t\t%s, %s%n", m.getLeft(), m.getRight());
                });
            });
        });
    }
}
