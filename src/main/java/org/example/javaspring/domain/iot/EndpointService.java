package org.example.javaspring.domain.iot;

import lombok.RequiredArgsConstructor;
import org.example.javaspring.domain.iot.model.ProcessState;
import org.example.javaspring.domain.iot.model.device.Device;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EndpointService {

    private final ProcessStateService processStateService;

    public void perform(Device.EndpointDevice device) {
        ProcessState processState = processStateService.getProcess(device.getLineId());
        boolean allDevicePass = processState.isAllDevicePass();
        device.performEndpoint();
        if (allDevicePass) {
            processStateService.removeProcess(device.getLineId());
        }
    }
}
