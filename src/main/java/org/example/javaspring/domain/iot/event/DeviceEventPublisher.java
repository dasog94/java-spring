package org.example.javaspring.domain.iot.event;

import lombok.RequiredArgsConstructor;
import org.example.javaspring.domain.iot.model.device.Device;
import org.example.javaspring.domain.iot.model.device.Device.DeviceReading;
import org.example.javaspring.infra.event.IotEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceEventPublisher {

    private final IotEventPublisher iotEventPublisher;
    
    public void publishDeviceEvent(Device device, DeviceReading reading) {
        DeviceEvent event = createDeviceEvent(device, reading);
        iotEventPublisher.publishEvent(event);
    }
    
    private DeviceEvent createDeviceEvent(Device device, DeviceReading reading) {
        if (device instanceof Device.EntrypointDevice entrypointDevice) {
            return new EntrypointDeviceEvent(this, entrypointDevice, reading);
        }
        else if (device instanceof Device.CheckpointDevice checkpointDevice) {
            return new CheckpointDeviceEvent(this, checkpointDevice, reading);
        }
        else if (device instanceof Device.EndpointDevice endpointDevice) {
            return new EndpointDeviceEvent(this, endpointDevice, reading);
        }

        throw new IllegalArgumentException("Unknown device type: " + device.getDeviceType());
    }
} 