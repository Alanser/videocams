package com.example.videocams;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CameraDataResponse {
    private long id;
    private CameraSourceData.UrlType urlType;
    private String videoUrl;
    private String value;
    private int ttl;
}
