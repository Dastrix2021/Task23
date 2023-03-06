package com.dastrix.data.apis;

import lombok.Data;

import java.util.List;
@Data
public class Sport {
    private String name;
    private Long id;
    private List<Region> regions;
}
