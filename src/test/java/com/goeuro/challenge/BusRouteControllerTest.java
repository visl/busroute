package com.goeuro.challenge;

import com.goeuro.challenge.helper.BusRouteHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * BusRouteControllerTest.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(BusRouteController.class)
public class BusRouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusRouteHelper helper;

    @Test
    public void getRouteBtwStations() throws Exception {
        when(helper.routeExistsForStations(11, 12)).thenReturn(true);

        this.mockMvc.perform(get("/api/direct?dep_sid=11&arr_sid=12")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"dep_sid\":11,\"arr_sid\":12,\"direct_bus_route\":true}")));

    }

}