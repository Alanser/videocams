package com.example.videocams;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CameraDataController {

    @Value("${application.api.camera-data-request.url}")
    private String requestUrl;

    private final CameraService cameraService;

    public CameraDataController(CameraService cameraService) {
        this.cameraService = cameraService;
    }

    @GetMapping("/cameras")
    public List<CameraDataResponse> getAggregateCamerasData() {
        return cameraService.aggregateAsynchronously(requestUrl);
    }
}
