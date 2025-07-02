package org.example.javaspring.domain.greet;

import io.grpc.stub.StreamObserver;
import org.example.javaspring.domain.greet.proto.GreeterServiceGrpc;
import org.example.javaspring.domain.greet.proto.HelloReply;
import org.example.javaspring.domain.greet.proto.HelloRequest;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class GreetService extends GreeterServiceGrpc.GreeterServiceImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        try {
            HelloReply reply = HelloReply.newBuilder()
                    .setMessage("Hello " + request.getName())
                    .build();

            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
                            .withDescription("Inner Server Error")
                            .withCause(e)
                            .asRuntimeException()
            );
        }
    }
}
