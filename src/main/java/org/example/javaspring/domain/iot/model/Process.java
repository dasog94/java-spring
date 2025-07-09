package org.example.javaspring.domain.iot.model;

import lombok.Builder;
import lombok.Getter;
import org.example.javaspring.domain.iot.model.device.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class Process {
    private UUID id;
    private Long lineId;
    @Builder.Default
    private List<DeviceResult> deviceResults = new ArrayList<>();
    
    @Getter
    @Builder
    public static class DeviceResult {
        private Device device;
        private Device.CheckResult checkResult;
    }

    public void addDeviceResult(Device device, Device.CheckResult checkResult) {
        deviceResults.add(DeviceResult.builder().device(device).checkResult(checkResult).build());
    }

    public boolean isAllDevicePass() {
        return deviceResults.stream().allMatch(deviceResult -> deviceResult.getCheckResult() == Device.CheckResult.PASS);
    }
}   