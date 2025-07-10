package org.example.javaspring.infra.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.javaspring.domain.greet.GreetServerService;
import org.example.javaspring.domain.greet.proto.GreeterServiceGrpc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class GrpcClient {

    @Value("${my.grpc.client.host:localhost}")
    private String grpcHost;

    @Value("${my.grpc.client.port:6565}")
    private int grpcPort;

    @Bean
    public ManagedChannel grpcChannel() throws InterruptedException {
        return ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .usePlaintext()
                .build();
    }

    @Bean
    public GreeterServiceGrpc.GreeterServiceBlockingStub grpcStub(ManagedChannel channel) {
        log.info("gRPC client initialized - {}:{}", grpcHost, grpcPort);
        return GreeterServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public GreeterServiceGrpc.GreeterServiceStub grpcAsyncStub(ManagedChannel channel) {
        log.info("gRPC client initialized - {}:{}", grpcHost, grpcPort);
        return GreeterServiceGrpc.newStub(channel);
    }

    @PreDestroy
    public void destroy() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(GrpcClient.class);
        ManagedChannel grpcChannel = ac.getBean(ManagedChannel.class);

        if (!grpcChannel.isShutdown()) {
            try {
                grpcChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
                log.info("gRPC client channel shutdown completed");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("gRPC client shutdown interrupted", e);
            }
        }
    }
}
