package com.asg.platform.statusUpdate.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StatusUpdateService {

    public String updateOrganismTrackingStatus(MultipartFile multipartFile) throws IOException;
}
