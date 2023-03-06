package com.dastrix.data.apis;

import lombok.Data;

import java.util.List;
@Data
public class Region {
    private String name;
    private List<League> leagues;
}
