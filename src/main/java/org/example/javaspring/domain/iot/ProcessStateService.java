package org.example.javaspring.domain.iot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.javaspring.domain.iot.model.Process;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessStateService {

    private final Map<Long, Process> processMap = new ConcurrentHashMap<>();

    public void registerProcess(Long lineId, Process process) {
        processMap.put(lineId, process);
        log.info("Process registered for line: {}", lineId);
    }

    public Process getProcess(Long lineId) {
        return processMap.get(lineId);
    }

    public void removeProcess(Long lineId) {
        Process removed = processMap.remove(lineId);
        if (removed != null) {
            log.info("Process removed for line: {}", lineId);
        }
    }

    public boolean hasProcess(Long lineId) {
        return processMap.containsKey(lineId);
    }
} 