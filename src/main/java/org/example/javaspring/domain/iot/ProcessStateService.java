package org.example.javaspring.domain.iot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.javaspring.domain.iot.model.ProcessState;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessStateService {

    private final Map<Long, ProcessState> processMap = new ConcurrentHashMap<>();

    public void registerProcess(Long lineId, ProcessState processState) {
        processMap.put(lineId, processState);
        log.info("Process registered for line: {}", lineId);
    }

    public ProcessState getProcess(Long lineId) {
        return processMap.get(lineId);
    }

    public void removeProcess(Long lineId) {
        ProcessState removed = processMap.remove(lineId);
        if (removed != null) {
            log.info("Process removed for line: {}", lineId);
        }
    }

    public boolean hasProcess(Long lineId) {
        return processMap.containsKey(lineId);
    }
} 