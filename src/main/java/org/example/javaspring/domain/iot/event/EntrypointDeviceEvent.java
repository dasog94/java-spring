package org.example.javaspring.domain.iot.event;

import org.example.javaspring.domain.iot.model.device.Device;
import org.example.javaspring.domain.iot.model.device.Device.DeviceReading;

public class EntrypointDeviceEvent extends DeviceEvent {
    
    public EntrypointDeviceEvent(Object source, Device.EntrypointDevice device, DeviceReading reading) {
        super(source, device, reading);
    }

    public Device.EntrypointDevice getEntrypointDevice() {
        return (Device.EntrypointDevice) getDevice();
    }
} 