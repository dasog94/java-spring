package org.example.javaspring.domain.greet;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.example.javaspring.domain.greet.proto.GreeterServiceGrpc;
import org.example.javaspring.domain.greet.proto.HelloRequest;
import org.example.javaspring.domain.greet.proto.HelloResponse;
import org.lognet.springboot.grpc.GRpcService;

@Slf4j
@GRpcService
public class GreetServerService extends GreeterServiceGrpc.GreeterServiceImplBase {
    private final String[] QUESTION_MESSAGES = {
            "What do you do for fun?",
            "What kind of books do you like?",
            "What kind of food do you like?",
            "What is your favoite color?",
            "What is your favoite sports?"
    };


    // Unary RPC
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        log.info("=== Get Request");
        log.info("=== Request Data => [{}]", request);

        // 응답 데이터 셋업
        HelloResponse response = HelloResponse.newBuilder()
                .setGreetingMessage("Hello, %s".formatted(request.getName()))  // .proto에 정의한 response value
                .setQuestionMessage("What do you do for fun?")  // .proto에 정의한 response value
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // Server Streaming RPC
    @Override
    public void lotsOfReplies(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        log.info("=== Get Request");
        log.info("=== Request Data => [{}]", request);

        // 응답 데이터 셋업
        for (String questionMessage: QUESTION_MESSAGES) {
            HelloResponse response = HelloResponse.newBuilder()
                    .setGreetingMessage("Hello, %s".formatted(request.getName()))
                    .setQuestionMessage(questionMessage)
                    .build();

            responseObserver.onNext(response);
        }

        responseObserver.onCompleted();
    }
}
