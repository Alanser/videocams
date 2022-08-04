package com.example.videocams;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CameraData {
    private long id;
    private CameraSourceData sourceData;
    private CameraTokenData tokenData;
}
