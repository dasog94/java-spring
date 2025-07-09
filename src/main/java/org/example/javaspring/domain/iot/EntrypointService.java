package org.example.javaspring.domain.iot;

import lombok.RequiredArgsConstructor;
import org.example.javaspring.domain.iot.device.Device;
import org.example.javaspring.infra.event.IotEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntrypointService {

    private final IotEventPublisher iotEventPublisher;

    public void perform(Device device) {
    }
}
