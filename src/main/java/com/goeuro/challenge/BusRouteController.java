package com.goeuro.challenge;

import com.goeuro.challenge.helper.BusRouteHelper;
import com.goeuro.challenge.model.ResponseModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * BusRouteController - rest controller for '/api' path.
 */
@RestController("api")
public class BusRouteController {

    private static final Logger logger = Logger.getLogger(BusRouteController.class);

    @Autowired
    private BusRouteHelper helper;

    /**
     * Accepts Get request to /api/direct URL.
     *
     * @param departureStation - 32 bit integer
     * @param arrivalStation   - 32 bit integer
     * @return JSON response
     */
    @RequestMapping(name = "/direct", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseModel getRouteBtwStations(@RequestParam(value = "dep_sid") int departureStation,
                                      @RequestParam(value = "arr_sid") int arrivalStation) {
        return new ResponseModel(departureStation, arrivalStation,
                helper.routeExistsForStations(departureStation, arrivalStation));
    }
}
