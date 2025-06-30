package org.example.javaspring.domain.routeguide;

import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RouteGuideClient {
    @Value("${my.grpc.client.host}")
    private String host;
    @Value("${my.grpc.client.port}")
    private int port;

    @Bean
    public ManagedChannel channel() {
        return Grpc.newChannelBuilderForAddress(
                host, port, InsecureChannelCredentials.create()
        ).build();
    }

    @Bean
    public RouteGuideGrpc.RouteGuideStub stub(ManagedChannel channel) {
        return RouteGuideGrpc.newStub(channel);
    }

    @Bean
    public RouteGuideGrpc.RouteGuideBlockingStub blockingStub(ManagedChannel channel) {
        return RouteGuideGrpc.newBlockingStub(channel);
    }
}
