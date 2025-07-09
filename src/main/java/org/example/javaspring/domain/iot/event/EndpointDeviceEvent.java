package org.example.javaspring.domain.iot.event;

import lombok.Getter;
import org.example.javaspring.domain.iot.model.device.Device;
import org.example.javaspring.domain.iot.model.device.Device.DeviceReading;

@Getter
public class EndpointDeviceEvent extends DeviceEvent {
    
    public EndpointDeviceEvent(Object source, Device.EndpointDevice device, DeviceReading reading) {
        super(source, device, reading);
    }

    public Device.EndpointDevice getEndpointDevice() {
        return (Device.EndpointDevice) getDevice();
    }
} 