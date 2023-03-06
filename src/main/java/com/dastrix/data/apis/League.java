package com.dastrix.data.apis;

import lombok.Data;

import java.util.List;
@Data
public class League {
    private Long id;
    private String name;
    private Boolean top;
    private Region region;
    private Sport sport;
    private List<ApiEvent> events;
}
