package org.example.javaspring.domain.iot.model.device;

import org.example.javaspring.domain.iot.model.Process;

public interface Device {
    DeviceType getDeviceType();
    Long getLineId();
    CheckResult check(DeviceReading reading);

    interface EntrypointDevice extends Device {
        void performEntrypoint();
    }

    interface EndpointDevice extends Device {
        void performEndpoint();
    }

    interface CheckpointDevice extends Device {
        void performCheckpoint(Process process);
    }

    public static enum DeviceType {
        ENRTYPOINT_INFRARED_CENSOR,

        TEMPERATURE_CENSOR,

        ENDPOINT_INFRARED_CENSOR,
    }

    public static enum CheckResult {
        PASS,
        FAIL,
    }

    public static interface DeviceReading {

    }
}