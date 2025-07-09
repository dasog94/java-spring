package org.example.javaspring.domain.iot.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.javaspring.domain.iot.CheckpointService;
import org.example.javaspring.domain.iot.EndpointService;
import org.example.javaspring.domain.iot.EntrypointService;
import org.example.javaspring.domain.iot.model.device.Device.CheckpointDevice;
import org.example.javaspring.domain.iot.model.device.Device.EndpointDevice;
import org.example.javaspring.domain.iot.model.device.Device.EntrypointDevice;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceEventHandler {

    private final EntrypointService entrypointService;
    private final CheckpointService checkpointService;
    private final EndpointService endpointService;
    
    @EventListener
    public void handleEntrypointDeviceEvent(EntrypointDeviceEvent event) {
        log.info("Handling entrypoint device event for line: {}", event.getDevice().getLineId());

        EntrypointDevice entrypointDevice = event.getEntrypointDevice();
        entrypointService.initialize(entrypointDevice);
        log.info("Process initialized for line: {}", entrypointDevice.getLineId());
    }
    
    @EventListener
    public void handleCheckpointDeviceEvent(CheckpointDeviceEvent event) {
        log.info("Handling checkpoint device event for line: {}", event.getDevice().getLineId());

        CheckpointDevice checkpointDevice = event.getCheckpointDevice();
        checkpointService.perform(checkpointDevice);
        log.info("Checkpoint performed for line: {}", checkpointDevice.getLineId());
    }
    
    @EventListener
    public void handleEndpointDeviceEvent(EndpointDeviceEvent event) {
        log.info("Handling endpoint device event for line: {}", event.getDevice().getLineId());

        EndpointDevice endpointDevice = event.getEndpointDevice();
        endpointService.perform(endpointDevice);
        log.info("Endpoint performed for line: {}", endpointDevice.getLineId());
    }
} 