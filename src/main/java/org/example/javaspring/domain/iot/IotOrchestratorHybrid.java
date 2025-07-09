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
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IotOrchestratorHybrid {

    private final EntrypointService entrypointService;
    private final CheckpointService checkpointService;
    private final EndpointService endpointService;
    private final DeviceEventPublisher deviceEventPublisher;
    private final Map<DeviceType, Device> deviceMap = new HashMap<>();

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

        // 단순한 동기 처리 - 직접 호출
        if (device instanceof Device.EntrypointDevice entrypointDevice) {
            entrypointService.initialize(entrypointDevice);
            
            // 추가 처리가 필요한 경우에만 이벤트 발행
            deviceEventPublisher.publishDeviceEvent(device, reading);
        } 
        // 복잡한 처리나 비동기 처리가 필요한 경우 - 이벤트 기반
        else if (device instanceof Device.CheckpointDevice checkpointDevice) {
            // 기본 처리
            checkpointService.perform(checkpointDevice);

            // 추가 처리를 위한 이벤트 발행
            deviceEventPublisher.publishDeviceEvent(device, reading);
        }
        else if (device instanceof Device.EndpointDevice) {
            // 기본 처리
            endpointService.perform((Device.EndpointDevice) device);

            // 추가 처리를 위한 이벤트 발행
            deviceEventPublisher.publishDeviceEvent(device, reading);
        }
    }
}