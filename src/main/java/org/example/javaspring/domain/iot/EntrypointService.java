package org.example.javaspring.domain.iot;

import lombok.RequiredArgsConstructor;
import org.example.javaspring.domain.iot.model.Process;
import org.example.javaspring.domain.iot.model.device.Device;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntrypointService {

    private final ProcessStateService processStateService;

    public Process initialize(Device.EntrypointDevice device) {
        device.performEntrypoint();
        Process process = Process.builder().id(UUID.randomUUID()).build();
        processStateService.registerProcess(device.getLineId(), process);
        return process;
    }
}
