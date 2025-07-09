package org.example.javaspring.domain.iot.device;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemperatureCensor implements Device {
    private final DeviceType deviceType;
}