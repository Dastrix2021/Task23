package com.dastrix.services;

import com.dastrix.api.config.JsonConfigure;
import com.dastrix.api.requests.Client;
import com.dastrix.constants.ApiConstants;
import com.dastrix.data.apis.ApiEvent;
import com.dastrix.data.apis.League;
import com.dastrix.data.apis.Sport;
import lombok.AllArgsConstructor;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
@AllArgsConstructor
public class EventApiService {
    private final Client client;
    private final JsonConfigure<Sport[]> sportConfigure = new JsonConfigure<>(Sport[].class);
    private final JsonConfigure<League> leagueConfigure = new JsonConfigure<>(League.class);
    private final JsonConfigure<ApiEvent> eventConfigure = new JsonConfigure<>(ApiEvent.class);

    public List<Sport> getSports() {
        CompletableFuture<String> response = client.httpClient
                .sendAsync(client.build(ApiConstants.SPORT), HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
        return Stream.of(response).map(r -> r.thenApply(sportConfigure::get))
                .map(CompletableFuture::join)
                .flatMap(Arrays::stream)
                .filter(n -> isSport(n.getName()))
                .toList();
    }
    public List<ApiEvent> getEvents(List<Long> eventsId) {
        return eventsId.stream()
                .map(id -> client.build(String.format(ApiConstants.EVENT, id.toString())))
                .map(r -> client.httpClient.sendAsync(r, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body))
                .map(r -> r.thenApply(eventConfigure::get))
                .map(CompletableFuture::join).toList();
    }
    public Stream<League> getLeagues(Sport sport) {
        return sport.getRegions().stream()
                .flatMap(region -> region.getLeagues().stream().filter(League::getTop))
                .map(league -> client.build(String.format(ApiConstants.LEAGUE, league.getId().toString())))
                .map(r -> client.httpClient.sendAsync(r, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body))
                .map(response -> response.thenApply(leagueConfigure::get))
                .map(CompletableFuture::join);
    }
    public Optional<ApiEvent> getFirstEvent(League league) {
        return league.getEvents().stream().findFirst();
    }

    public List<Optional<ApiEvent>> events() {
        return getSports().stream()
                .flatMap(this::getLeagues)
                .map(this::getFirstEvent)
                .toList();
    }
    public List<Long> getEventsId(List<Optional<ApiEvent>> events) {
        List<Long> id = new ArrayList<>();
        events.forEach(o -> o.ifPresent(e -> id.add(e.getId())));
        return id;
    }
    public boolean isSport(String sportName) {
        return switch (sportName) {
            case ApiConstants.SOCCER,
                    ApiConstants.TENNIS,
                    ApiConstants.HOKEY,
                    ApiConstants.BASKETBALL -> true;
            default -> false;
        };
    }
}
