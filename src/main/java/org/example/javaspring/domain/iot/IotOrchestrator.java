package org.example.javaspring.domain.iot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;

import org.example.javaspring.domain.iot.device.Device;
import org.example.javaspring.domain.iot.device.DeviceType;
import org.example.javaspring.domain.iot.device.InfraredCensor;
import org.example.javaspring.domain.iot.device.TemperatureCensor;
import org.example.javaspring.domain.iot.model.Process;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IotOrchestrator {

    private final CheckpointService checkpointService;
    private final EndpointService endpointService;
    private final EntrypointService entrypointService;

    private final Map<DeviceType, Device> deviceMap = new HashMap<>();
    private final Map<Long, Process> processMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        deviceMap.put(DeviceType.ENRTYPOINT_INFRARED_CENSOR, new InfraredCensor());
        deviceMap.put(DeviceType.TEMPERATURE_CENSOR, new TemperatureCensor());
        deviceMap.put(DeviceType.ENDPOINT_INFRARED_CENSOR, new InfraredCensor());
    }

    public Device getDevice(DeviceType deviceType) {
        switch (deviceType) {
            case ENRTYPOINT_INFRARED_CENSOR:
                return deviceMap.get(DeviceType.ENRTYPOINT_INFRARED_CENSOR);
            case TEMPERATURE_CENSOR:
                return deviceMap.get(DeviceType.TEMPERATURE_CENSOR);
            case ENDPOINT_INFRARED_CENSOR:
                return deviceMap.get(DeviceType.ENDPOINT_INFRARED_CENSOR);
        }
    }

    public void perform(Device device) {
        switch (device.getDeviceType()) {
            case ENRTYPOINT_INFRARED_CENSOR:
                Process process = entrypointService.initialize(device);
                processMap.put(device.getLineId(), process);
                break;
            case TEMPERATURE_CENSOR:
                Process process = processMap.get(device.getLineId());
                checkpointService.perform(device, process);
                break;
            case ENDPOINT_INFRARED_CENSOR:
                Process process = processMap.get(device.getLineId());
                endpointService.perform(device, process);
                break;
        }
    }
}
