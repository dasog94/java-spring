package org.example.javaspring.domain.iot.model.device;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class InfraredCensor implements Device {
    private static final int MIN_DISTANCE = 10;

    private final DeviceType deviceType;
    private final Long lineId;

    @Override
    public Device.CheckResult check(DeviceReading reading) {
        if (!(reading instanceof InfraredReading infraredReading)) {
            throw new IllegalArgumentException("Invalid reading type for InfraredCensor");
        }

        if (infraredReading.isInRange()) {
            return CheckResult.PASS;
        } else {
            return CheckResult.FAIL;
        }
    }

    @Getter
    public static class EntrypointInfraredCensor extends InfraredCensor implements Device.EntrypointDevice {

        @Builder
        public EntrypointInfraredCensor(DeviceType deviceType, Long lineId) {
            super(deviceType, lineId);
        }

        @Override
        public void performEntrypoint() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'performEntrypoint'");
        }
    }

    @Getter
    public static class EndpointInfraredCensor extends InfraredCensor implements Device.EndpointDevice {

        @Builder
        public EndpointInfraredCensor(DeviceType deviceType, Long lineId) {
            super(deviceType, lineId);
        }

        @Override
        public void performEndpoint() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'performEndpoint'");
        }
    }

    public static class InfraredReading implements Device.DeviceReading {
        private final int distance;
        private final OffsetDateTime createdAt;

        @Builder
        public InfraredReading(int distance) {
            this.distance = distance;
            this.createdAt = OffsetDateTime.now(ZoneOffset.UTC);
        }

        public boolean isInRange() {
            return distance >= MIN_DISTANCE;
        }
    }
}