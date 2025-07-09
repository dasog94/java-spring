package org.example.javaspring.domain.iot;

import lombok.RequiredArgsConstructor;
import org.example.javaspring.domain.iot.model.ProcessState;
import org.example.javaspring.domain.iot.model.device.Device;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntrypointService {

    private final ProcessStateService processStateService;

    public ProcessState initialize(Device.EntrypointDevice device) {
        device.performEntrypoint();
        ProcessState processState = ProcessState.builder().id(UUID.randomUUID()).build();
        processStateService.registerProcess(device.getLineId(), processState);
        return processState;
    }
}
