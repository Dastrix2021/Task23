package com.dastrix.data.apis;

import lombok.Data;

import java.util.List;
@Data
public class Market {
    private String name;
    private List<Runner> runners;
}
