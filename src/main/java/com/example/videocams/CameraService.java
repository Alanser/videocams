package com.example.videocams;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CameraService {

    private final RestTemplate restTemplate;

    public CameraService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<CameraDataUrls> getAllCameraDataUrls(String url) {
        ResponseEntity<CameraDataUrls[]> response = restTemplate.getForEntity(url, CameraDataUrls[].class);
        return response.getStatusCode() == HttpStatus.OK ? Arrays.asList(Objects.requireNonNull(response.getBody())) : null;
    }

    public List<CameraDataResponse> aggregateAsynchronously(String url) {
        List<CameraDataUrls> dataUrls = getAllCameraDataUrls(url);

        Map<Long, CameraData> cameraDataMap = dataUrls.stream()
                .collect(Collectors.toMap(CameraDataUrls::getId, c -> {
                    CameraData cd = new CameraData();
                    cd.setId(c.getId());
                    return cd;
                }));

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        dataUrls.forEach(cameraDataUrls -> {
            CameraData cameraData = cameraDataMap.get(cameraDataUrls.getId());
            futures.add(CompletableFuture.runAsync(() -> cameraData.setSourceData(getDataFrom(cameraDataUrls.getSourceDataUrl(), CameraSourceData.class)))
                    .thenRunAsync(() -> cameraData.setTokenData(getDataFrom(cameraDataUrls.getTokenDataUrl(), CameraTokenData.class))));
        });

        futures.forEach(CompletableFuture::join);

        return cameraDataMap.values().stream()
                .map(CameraDataResponse::from)
                .toList();
    }

    public <T> T getDataFrom(String url, Class<T> tClass) {
        ResponseEntity<T> response = restTemplate.getForEntity(url, tClass);
        return response.getStatusCode() == HttpStatus.OK ? response.getBody() : null;
    }

}
