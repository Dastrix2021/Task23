package com.dastrix.data.selenium;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class Coefficient {
    private String name;
    private List<Market> market;
}
