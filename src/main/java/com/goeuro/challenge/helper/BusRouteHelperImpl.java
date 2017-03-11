package com.goeuro.challenge.helper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * BusRouteHelperImpl - the logic for loading file in memory,
 * validating input and finding direct route for bus stations.
 */
@Component
public class BusRouteHelperImpl implements BusRouteHelper {

    /**
     * Path to the data file.
     */
    public static final String DATA_FILE = "dataFile";
    /**
     * A line with bus stations will have min 5 chars.
     * Eg. "1 2 3"
     */
    public static final int MIN_BUS_LINE_LENGTH = 5;

    private static final Logger logger = Logger.getLogger(BusRouteHelperImpl.class);

    /**
     * Data file in memory.
     */
    private List<List<String>> dataInMemory;

    @Autowired
    private Environment environment;

    /**
     * Converts String line into list by space regex and removes first element
     * as it is bus number.
     *
     * @param line String to convert
     * @return List<String>
     */
    private List<String> convertIntoStationList(final String line) {
        List<String> listOfStations = new ArrayList<String>();
        listOfStations.addAll(Arrays.asList(line.split(" ")));
        listOfStations.remove(0);
        return listOfStations;
    }

    @PostConstruct
    public void init() {
        final String dataFile = environment.getProperty(DATA_FILE);
        logger.debug("dataFile=" + dataFile);

        if (!StringUtils.isEmpty(dataFile)) {
            List<String> listOfLines = new ArrayList<>();
            try (Stream<String> stream = Files.lines(Paths.get(dataFile))) {
                listOfLines =
                        stream.filter(line -> line.length() > MIN_BUS_LINE_LENGTH)
                                .collect(Collectors.toList());
                dataInMemory = new ArrayList<>(listOfLines.size());
                for (String line : listOfLines) {
                    dataInMemory.add(convertIntoStationList(line));
                }
            } catch (IOException e) {
                logger.error("Failed to parse file", e);
            }
        }
    }


    @Override
    public boolean routeExistsForStations(final int departureStation,
                                          final int arrivalStation) {
        if (CollectionUtils.isEmpty(dataInMemory)) {
            logger.error("Local bus data is corrupted. Reload service and check logs.");
            return false;
        }

        if (!validInput(departureStation, arrivalStation)) {
            logger.error("Input values are incorrect " + departureStation
                    + " or " + arrivalStation);
            return false;
        }

        List<String> stations = Arrays.asList(String.valueOf(departureStation),
                String.valueOf(arrivalStation));

        for (List line : dataInMemory) {
            if (line.containsAll(stations)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if input values are valid integers and not the same.
     *
     * @param departureStation
     * @param arrivalStation
     * @return true if given values are correct
     */
    private boolean validInput(final int departureStation,
                               final int arrivalStation) {
        logger.debug("departureStation = " + departureStation);
        logger.debug("arrivalStation = " + arrivalStation);
        return departureStation > 0 && arrivalStation > 0
                && departureStation != arrivalStation;
    }
}
