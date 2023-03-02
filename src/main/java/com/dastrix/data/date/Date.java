package com.dastrix.data.date;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
@Data
@AllArgsConstructor
public class Date implements DateTime {
    private String date;
    private String time;
    @Override
    public String getDateTime() {
        try {
            String dateTime = date + " " + time;
            String utc = getFormat(dateTime).parse(dateTime).toInstant().atZone(TimeZone.getTimeZone("UTC").toZoneId()).toString();
            return utc.replaceFirst("T", " ")
                    .replace('Z', ' ')
                    .replaceAll("[]\\[]", "");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public SimpleDateFormat getFormat(String s) throws ParseException {
        Locale locale = new Locale("ru", "RU");
        if (s.matches("[^а-яА-Я]+"))
            return new SimpleDateFormat("dd.MM.yyyy HH:mm", locale);
        return new SimpleDateFormat("dd MMMM yyyy HH:mm", locale);
    }
}
