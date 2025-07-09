package org.example.javaspring.infra.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IotEventPublisher {

    private final ApplicationEventPublisher eventPublisher;
    
    public void publishEvent(ApplicationEvent event) {
        eventPublisher.publishEvent(event);
    }
}