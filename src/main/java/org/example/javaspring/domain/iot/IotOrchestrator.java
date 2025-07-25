package org.example.javaspring.domain.iot;

import lombok.RequiredArgsConstructor;
import org.example.javaspring.domain.iot.event.DeviceEventPublisher;
import org.example.javaspring.domain.iot.model.device.Device;
import org.example.javaspring.domain.iot.model.device.Device.DeviceReading;
import org.example.javaspring.domain.iot.model.device.Device.DeviceType;
import org.example.javaspring.domain.iot.model.device.InfraredCensor;
import org.example.javaspring.domain.iot.model.device.TemperatureCensor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class IotOrchestrator {

    private final DeviceEventPublisher deviceEventPublisher;
    private final Map<DeviceType, Device> deviceMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        deviceMap.put(DeviceType.ENRTYPOINT_INFRARED_CENSOR, InfraredCensor.EntrypointInfraredCensor.builder().build());
        deviceMap.put(DeviceType.TEMPERATURE_CENSOR, TemperatureCensor.builder().build());
        deviceMap.put(DeviceType.ENDPOINT_INFRARED_CENSOR, InfraredCensor.EndpointInfraredCensor.builder().build());
    }

    public void perform(DeviceType deviceType, DeviceReading reading) {
        Device device = deviceMap.get(deviceType);
        if (device == null) {
            throw new IllegalArgumentException("Device type not found: " + deviceType);
        }

        if (device instanceof Device.EntrypointDevice) {
            deviceEventPublisher.publishDeviceEvent(device, reading);
        } 
        else if (device instanceof Device.CheckpointDevice) {
            deviceEventPublisher.publishDeviceEvent(device, reading);
        }
        else if (device instanceof Device.EndpointDevice) {
            deviceEventPublisher.publishDeviceEvent(device, reading);
        }
    }
}