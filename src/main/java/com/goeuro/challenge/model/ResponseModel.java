package com.goeuro.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ResponseModel.
 */
public class ResponseModel {

    private final int departureStation;
    private final int arrivalStation;
    private final boolean directRoute;

    public ResponseModel(final int departureStation,
                         final int arrivalStation,
                         final boolean directRoute) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.directRoute = directRoute;
    }

    @JsonProperty("dep_sid")
    public int getDepartureStation() {
        return departureStation;
    }

    @JsonProperty("arr_sid")
    public int getArrivalStation() {
        return arrivalStation;
    }

    @JsonProperty("direct_bus_route")
    public boolean isDirectRoute() {
        return directRoute;
    }
}
