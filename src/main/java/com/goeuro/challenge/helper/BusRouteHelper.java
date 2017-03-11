package com.goeuro.challenge.helper;

/**
 * BusRouteHelper interface.
 */
public interface BusRouteHelper {

    /**
     * Checks if input stations are correct and if any of the bus route contains
     * both of them.
     *
     * @param departureStation
     * @param arrivalStation
     * @return true if given stations belong to the bus route
     */
    boolean routeExistsForStations(int departureStation, int arrivalStation);
}
