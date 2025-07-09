package org.example.javaspring.domain.iot;

import lombok.RequiredArgsConstructor;
import org.example.javaspring.domain.iot.model.Process;
import org.example.javaspring.domain.iot.model.device.Device;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckpointService {

    private final ProcessStateService processStateService;

    public void perform(Device.CheckpointDevice device) {
        Process process = processStateService.getProcess(device.getLineId());
        device.performCheckpoint(process);
    }
}
