package com.example.videocams;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CameraDataResponse {
    private long id;
    private CameraSourceData.UrlType urlType;
    private String videoUrl;
    private String value;
    private int ttl;

    public static CameraDataResponse from(CameraData cameraData) {
        return new CameraDataResponse(cameraData.getId(), cameraData.getSourceData().getUrlType(),
                cameraData.getSourceData().getVideoUrl(), cameraData.getTokenData().getValue(),
                cameraData.getTokenData().getTtl());
    }
}
