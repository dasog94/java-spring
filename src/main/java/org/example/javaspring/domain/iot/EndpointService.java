package org.example.javaspring.domain.iot;

import lombok.RequiredArgsConstructor;
import org.example.javaspring.domain.iot.model.Process;
import org.example.javaspring.domain.iot.model.device.Device;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EndpointService {

    private final ProcessStateService processStateService;

    public void perform(Device.EndpointDevice device) {
        Process process = processStateService.getProcess(device.getLineId());
        boolean allDevicePass = process.isAllDevicePass();
        device.performEndpoint();
        if (allDevicePass) {
            processStateService.removeProcess(device.getLineId());
        }
    }
}
