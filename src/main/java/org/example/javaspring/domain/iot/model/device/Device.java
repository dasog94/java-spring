package org.example.javaspring.domain.iot.device;

public interface Device {
    DeviceType getDeviceType();
    void perform();

    public static enum DeviceType {
        ENRTYPOINT_INFRARED_CENSOR,

        TEMPERATURE_CENSOR,

        ENDPOINT_INFRARED_CENSOR,
    }
}