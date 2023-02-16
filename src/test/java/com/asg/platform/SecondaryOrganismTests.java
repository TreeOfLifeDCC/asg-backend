package com.asg.platform;

import com.asg.platform.es.mapping.SecondaryOrganism;
import com.asg.platform.es.service.OrganismService;
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
public class SecondaryOrganismTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    OrganismService organismService;

//    @Test
//    void getOrganismDetails() throws Exception {
//        SecondaryOrganism secondaryOrganism = new SecondaryOrganism();
//        Optional<String> sortColumn = Optional.of("accession");
//        Optional<String> sortOrder = Optional.of("asc");
//
//        when((organismService.findBioSampleByAccession("SAMEA994732"))).thenReturn(secondaryOrganism);
//
//        this.mockMvc.perform(get("/organisms/SAMEA994732")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("")));
//    }
}
