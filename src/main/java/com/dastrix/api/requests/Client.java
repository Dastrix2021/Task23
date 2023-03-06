package com.dastrix.api.requests;

import com.dastrix.constants.ApiConstants;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Client {
    public static final ExecutorService executor = Executors.newFixedThreadPool(ApiConstants.THREAD_NUM);
    public final HttpClient httpClient = HttpClient
            .newBuilder()
            .executor(executor)
            .connectTimeout(Duration.ofSeconds(ApiConstants.WAIT_CONNECTION_TIME))
            .build();
    public HttpRequest build(String u) {
        return HttpRequest.newBuilder()
                .uri(URI.create(u))
                .header(ApiConstants.X_APP_LANGUAGE, ApiConstants.RU_UA)
                .GET()
                .build();
    }
}
