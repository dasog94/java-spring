package org.example.javaspring.domain.iot.event;

import lombok.Getter;
import org.example.javaspring.domain.iot.model.device.Device;
import org.example.javaspring.domain.iot.model.device.Device.DeviceReading;
import org.springframework.context.ApplicationEvent;

import java.time.OffsetDateTime;

@Getter
public abstract class DeviceEvent extends ApplicationEvent {
    
    private final Device device;
    private final DeviceReading reading;
    private final OffsetDateTime createAt;
    
    public DeviceEvent(Object source, Device device, DeviceReading reading) {
        super(source);
        this.device = device;
        this.reading = reading;
        this.createAt = OffsetDateTime.now();
    }
} 