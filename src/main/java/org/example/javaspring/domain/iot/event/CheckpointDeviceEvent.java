package org.example.javaspring.domain.iot.event;

import lombok.Getter;
import org.example.javaspring.domain.iot.model.device.Device;
import org.example.javaspring.domain.iot.model.device.Device.DeviceReading;

@Getter
public class CheckpointDeviceEvent extends DeviceEvent {
    
    public CheckpointDeviceEvent(Object source, Device.CheckpointDevice device, DeviceReading reading) {
        super(source, device, reading);
    }

    public Device.CheckpointDevice getCheckpointDevice() {
        return (Device.CheckpointDevice) getDevice();
    }
} 