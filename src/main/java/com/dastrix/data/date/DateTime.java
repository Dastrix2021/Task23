package com.dastrix.data.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
public interface DateTime {
    String getDateTime();
    SimpleDateFormat getFormat(String s) throws ParseException;
}
