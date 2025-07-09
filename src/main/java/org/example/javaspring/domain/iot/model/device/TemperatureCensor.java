package org.example.javaspring.domain.iot.model.device;

import lombok.Builder;
import lombok.Getter;
import org.example.javaspring.domain.iot.model.Process;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Getter
@Builder
public class TemperatureCensor implements Device.CheckpointDevice {
    private final int MIN_TEMPERATURE = 10;
    private final int MAX_TEMPERATURE = 30;

    private final DeviceType deviceType;
    private final Long lineId;

    @Override
    public CheckResult check(DeviceReading reading) {
        if (!(reading instanceof TemperatureReading temperatureReading)) {
            throw new IllegalArgumentException("Invalid reading type for TemperatureCensor");
        }

        if (temperatureReading.isInRange(MIN_TEMPERATURE, MAX_TEMPERATURE)) {
            return CheckResult.PASS;
        } else {
            return CheckResult.FAIL;
        }
    }

    @Override
    public void performCheckpoint(Process process) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'performCheckpoint'");
    }

    @Getter
    public static class TemperatureReading implements Device.DeviceReading {
        private final int temperature;
        private final OffsetDateTime createdAt;

        @Builder
        public TemperatureReading(int temperature) {
            this.temperature = temperature;
            this.createdAt = OffsetDateTime.now(ZoneOffset.UTC);
        }

        public boolean isInRange(int min, int max) {
            return temperature >= min && temperature <= max;
        }
    }
}