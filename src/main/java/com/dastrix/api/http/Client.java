package com.dastrix.api.http;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Client {
    public final static Byte THREAD_NUM = 3;
    public final static Byte WAIT_CONNECTION_TIME = 30;
    public static final ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUM);
    public final HttpClient httpClient = HttpClient
            .newBuilder()
            .executor(executor)
            .connectTimeout(Duration.ofSeconds(WAIT_CONNECTION_TIME))
            .build();
    public HttpRequest build(String u) {
        return HttpRequest.newBuilder()
                .uri(URI.create(u))
                .header("x-app-language", "ru_UA")
                .GET()
                .build();
    }
}
