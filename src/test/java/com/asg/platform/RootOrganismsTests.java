package com.asg.platform;

import com.asg.platform.es.mapping.RootOrganism;
import com.asg.platform.es.service.RootSampleService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RootOrganismsTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RootSampleService rootSampleService;

//    @Test
//    void findAllRootSamples() throws Exception {
//        JSONArray emptySampleList = new JSONArray();
//        emptySampleList.add(new RootOrganism());
//
//        Optional<String> sortColumn = Optional.of("accession");
//        Optional<String> sortOrder = Optional.of("asc");
//        JSONObject mockResp = new JSONObject();
//        mockResp.put("rootSamples", null);
//        mockResp.put("count", 0);
//
//        when((rootSampleService.findAllOrganisms(0,10, sortColumn, sortOrder))).thenReturn((JSONArray) emptySampleList);
//        this.mockMvc.perform(get("/root_organisms")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString(mockResp.toString())));
//    }

    @Test
    void getOrganismDetails() throws Exception {
        RootOrganism organism = new RootOrganism();
        Optional<String> sortColumn = Optional.of("accession");
        Optional<String> sortOrder = Optional.of("asc");
        JSONObject mockResp = new JSONObject();
        mockResp.put("rootSamples", null);
        mockResp.put("count", 0);
        when((rootSampleService.findRootSampleByOrganism(""))).thenReturn(organism);

        this.mockMvc.perform(get("/root_organisms/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(mockResp.toString())));
    }

}
