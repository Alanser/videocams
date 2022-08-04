package com.example.videocams;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.stubbing.answers.AnswersWithDelay;
import org.mockito.internal.stubbing.answers.Returns;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest
class CameraServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CameraService cameraService;

    @Test
    void getCameraDataResponseListAsynchronously_andAssertIfTakeLessTimeThanSynchronously() {
        CameraDataUrls[] dataUrls = new CameraDataUrls[10];
        for (int i = 0; i < 10; i++) {
            dataUrls[i] = new CameraDataUrls(i + 1, "http://some-url", "http://some-url");
        }

        when(restTemplate.getForEntity(anyString(), eq(CameraDataUrls[].class))).thenReturn(new ResponseEntity<>(dataUrls, HttpStatus.OK));

        mockRestReturnWithDelay(1000, new CameraSourceData()); // for each source data request delay is 1 second
        mockRestReturnWithDelay(2000, new CameraTokenData()); // for each token data request delay is 2 second

        // With synchronously will be take 10 * 1 sec + 10 * 2 sec = 30 second.
        // Assert if it take less time (approximately it will take 6 sec on my machine)
        assertTimeout(Duration.ofSeconds(30), () -> cameraService.aggregateAsynchronously("some url"));
    }

    private <T> void mockRestReturnWithDelay(int delay, T returned) {
        doAnswer(new AnswersWithDelay(delay, new Returns(new ResponseEntity<>(returned, HttpStatus.OK))))
                .when(restTemplate).getForEntity(anyString(), eq(returned.getClass()));
    }


}