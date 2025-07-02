package org.example.javaspring.domain.greet;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.javaspring.domain.greet.proto.GreeterServiceGrpc;
import org.example.javaspring.domain.greet.proto.HelloReply;
import org.example.javaspring.domain.greet.proto.HelloRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class GreetClient {

    @Value("${my.grpc.client.host:localhost}")
    private String grpcHost;

    @Value("${my.grpc.client.port:6565}")
    private int grpcPort;

    private ManagedChannel channel;
    private GreeterServiceGrpc.GreeterServiceStub asyncStub;

    @PostConstruct
    public void init() {
        channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .usePlaintext()
                .build();

        asyncStub = GreeterServiceGrpc.newStub(channel);

        log.info("gRPC client initialized - {}:{}", grpcHost, grpcPort);
    }

    @PreDestroy
    public void destroy() {
        if (channel != null && !channel.isShutdown()) {
            try {
                channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
                log.info("gRPC client channel shutdown completed");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("gRPC client shutdown interrupted", e);
            }
        }
    }

    /**
     * 사용자 정보 업데이트 (비동기 양방향 스트리밍)
     */
    public void updateUserStream(HelloRequest request,
                                 StreamObserver<HelloReply> responseObserver) {
        CountDownLatch finishLatch = new CountDownLatch(1);

        StreamObserver<HelloRequest> requestObserver = asyncStub.sayHello(
                new StreamObserver<HelloRequest>() {
                    @Override
                    public void onNext(HelloRequest response) {
                        log.info("Received message : {}", response.getName());
                        if (responseObserver != null) {
                            responseObserver.onNext(response);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        log.error("Error in update stream", t);
                        if (responseObserver != null) {
                            responseObserver.onError(t);
                        }
                        finishLatch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        log.info("Update stream completed");
                        if (responseObserver != null) {
                            responseObserver.onCompleted();
                        }
                        finishLatch.countDown();
                    }
                }
        );

        try {
            // 요청들을 스트림으로 전송
            for (UpdateUserRequest request : updateRequests) {
                requestObserver.onNext(request);
                Thread.sleep(100); // 시뮬레이션을 위한 지연
            }

            requestObserver.onCompleted();

            // 완료 대기
            finishLatch.await(30, TimeUnit.SECONDS);

        } catch (Exception e) {
            logger.error("Error in update user stream", e);
            requestObserver.onError(e);
        }
    }

}
