package com.dastrix.data.selenium;

import com.dastrix.data.date.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private Long id;
    private List<String> sportInfo;
    private List<Coefficient> coefficient;
    private Date date;
}
