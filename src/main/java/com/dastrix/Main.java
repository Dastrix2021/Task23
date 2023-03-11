package com.dastrix;
import com.dastrix.starters.ApiStarter;
import com.dastrix.starters.SeleniumStarter;
import com.dastrix.starters.Starter;
public class Main {
    public static void main(String[] args) {
        Starter starter = new ApiStarter();
        starter.scrape();
    }
}