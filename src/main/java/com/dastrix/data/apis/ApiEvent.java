package com.dastrix.data.apis;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
@Data
public class ApiEvent {
    private Long id;
    private String name;
    private Timestamp kickoff;
    private League league;
    private List<Market> markets;
}
