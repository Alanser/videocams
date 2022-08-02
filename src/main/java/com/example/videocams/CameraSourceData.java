package com.example.videocams;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CameraSourceData {
    private UrlType urlType;
    private String videoUrl;

    public enum UrlType {
        LIVE,
        ARCHIVE
    }
}
