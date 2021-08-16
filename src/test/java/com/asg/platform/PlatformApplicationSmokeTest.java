package com.asg.platform;

import com.asg.platform.controller.RootOrganismController;
import com.asg.platform.controller.SecondaryOrganismsController;
import com.asg.platform.controller.StatusTrackingController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class PlatformApplicationSmokeTest {

    @Autowired
    StatusTrackingController statusTrackingController;

    @Autowired
    SecondaryOrganismsController organismController;

    @Autowired
    RootOrganismController rootOrganismController;

    @Test
    void contextLoads() throws Exception {
        assertThat(statusTrackingController).isNotNull();
        assertThat(organismController).isNotNull();
        assertThat(rootOrganismController).isNotNull();
    }
}
