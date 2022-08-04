package com.example.videocams;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CameraSourceData {
    private UrlType urlType;
    private String videoUrl;

    public enum UrlType {
        LIVE,
        ARCHIVE
    }
}
