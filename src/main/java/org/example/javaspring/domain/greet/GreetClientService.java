package org.example.javaspring.domain.greet;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.javaspring.domain.greet.proto.GreeterServiceGrpc;
import org.example.javaspring.domain.greet.proto.HelloRequest;
import org.example.javaspring.domain.greet.proto.HelloResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class GreetClientService {

    private final String[] NAMES = {
            "herojoon",
            "yejin",
            "jonghoon",
            "sophia",
            "woojin"
    };

    private int MIN_AGE = 1;
    private int MAX_AGE = 100;

    private final GreeterServiceGrpc.GreeterServiceStub asyncStub;
    private final GreeterServiceGrpc.GreeterServiceBlockingStub blockingStub;



    public void sendUnaryBlocking() {
        log.info(">>> Send Call");

        HelloResponse response = blockingStub.sayHello(
                HelloRequest.newBuilder()
                        .setName("test")
                        .setAge(10)
                        .setMessage("Hello, Glad to meet you.")
                        .build()
        );

        log.info(">>> Response Data => [{}]", response);
    }

    // Server Streaming RPC
    public void sendServerStreamingBlocking() {
        log.info(">>> Send Call");

        // 요청은 하나만 보내고, 여러 개의 응답을 받는다.
        Iterator<HelloResponse> helloResponseIterator = blockingStub.lotsOfReplies(HelloRequest.newBuilder()
                .setName("herojoon")  // .proto에 정의한 request value
                .setAge(10)  // .proto에 정의한 request value
                .setMessage("Hello, Glad to meet you.")  // .proto에 정의한 request value
                .build());

        // 응답 출력
        helloResponseIterator.forEachRemaining(helloResponse -> {
            log.info(">>> Response Data => [%s]".formatted(helloResponse));
        });
    }

    // Client Streaming RPC
    public void sendClientStremingAsync() throws InterruptedException {
        log.info(">>> Send Call");

        // Create a list of HelloRequest
        List<HelloRequest> helloRequestList = new ArrayList<>();
        for (String name: NAMES) {
            helloRequestList.add(
                    HelloRequest.newBuilder()
                            .setName(name)
                            .setAge(getRandomAge())
                            .setMessage("Hello, Glad to meet you.")
                            .build()
            );
        }

        final CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<HelloResponse> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(HelloResponse helloResponse) {
                // 서버 응답 출력
                log.info(">>> Response Data => [%s]".formatted(helloResponse));
            }

            @Override
            public void onError(Throwable t) {  // 스트림에서 종료 오류 발생 시 수신
                Status status = Status.fromThrowable(t);
                log.warn(">>> Warning => [%s]".formatted(status));
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {  // 스트림이 성공적으로 완료되었다고 응답 받음
                log.info(">>> Finished.");
                finishLatch.countDown();
            }
        };

        StreamObserver<HelloRequest> requestObserver = asyncStub.lotsOfGreetings(responseObserver);
        try {
            for (HelloRequest req: helloRequestList) {
                requestObserver.onNext(req);
                log.info(">>> Req Name: " + req.getName());

                Thread.sleep(1000);
                if (finishLatch.getCount() == 0) {  // 오류 발생 시 다음 코드를 전송하더라도 처리되지 않기 때문에 전송하지 않도록 처리
                    // RPC completed or errored before we finished sending.
                    // Sending further requests won't error, but they will just be thrown away.
                    log.info(">>> Stop the next request");
                    return;
                }
            }
        } catch (RuntimeException e) {
            // Cancel RPC
            requestObserver.onError(e);
            throw e;
        }
        // Mark the end of requests
        requestObserver.onCompleted();

        // Receiving happens asynchronously
        finishLatch.await(1, TimeUnit.MINUTES);

        log.info(">>> End.");
    }

    // min 나이부터 max 나이까지 랜덤 값 return
    private int getRandomAge() {
        return (int) (Math.random() * (MAX_AGE - MIN_AGE + 1)) + MIN_AGE;
    }
}
