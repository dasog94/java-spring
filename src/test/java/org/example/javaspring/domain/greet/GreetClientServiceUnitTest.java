package org.example.javaspring.domain.greet;

import org.example.javaspring.domain.greet.proto.HelloRequest;
import org.example.javaspring.domain.greet.proto.HelloResponse;
import org.example.javaspring.domain.greet.proto.GreeterServiceGrpc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("GreetClientService Unit Tests")
class GreetClientServiceUnitTest {

    @Mock
    private GreeterServiceGrpc.GreeterServiceStub asyncStub;

    @Mock
    private GreeterServiceGrpc.GreeterServiceBlockingStub blockingStub;

    private GreetClientService greetClientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        greetClientService = new GreetClientService(asyncStub, blockingStub);
    }

    @Test
    @DisplayName("Should call blockingStub.sayHello with correct parameters")
    void testSendUnaryBlockingCallsCorrectMethod() {
        // Given: Mock response
        HelloResponse mockResponse = HelloResponse.newBuilder()
                .setGreetingMessage("Hello, test")
                .setQuestionMessage("What do you do for fun?")
                .build();

        when(blockingStub.sayHello(any(HelloRequest.class))).thenReturn(mockResponse);

        // When: Call sendUnaryBlocking
        assertDoesNotThrow(() -> {
            greetClientService.sendUnaryBlocking();
        });

        // Then: Verify blockingStub.sayHello was called
        verify(blockingStub, times(1)).sayHello(any(HelloRequest.class));
    }

    @Test
    @DisplayName("Should create HelloRequest with correct values")
    void testSendUnaryBlockingCreatesCorrectRequest() {
        // Given: Mock response
        HelloResponse mockResponse = HelloResponse.newBuilder()
                .setGreetingMessage("Hello, test")
                .setQuestionMessage("What do you do for fun?")
                .build();

        when(blockingStub.sayHello(any(HelloRequest.class))).thenReturn(mockResponse);

        // When: Call sendUnaryBlocking
        greetClientService.sendUnaryBlocking();

        // Then: Verify the request was created with correct values
        verify(blockingStub).sayHello(argThat(request -> 
            "test".equals(request.getName()) &&
            request.getAge() == 10 &&
            "Hello, Glad to meet you.".equals(request.getMessage())
        ));
    }

    @Test
    @DisplayName("Should handle gRPC exceptions gracefully")
    void testSendUnaryBlockingHandlesExceptions() {
        // Given: Mock exception
        when(blockingStub.sayHello(any(HelloRequest.class)))
                .thenThrow(new RuntimeException("gRPC error"));

        // When & Then: Should throw exception
        assertThrows(RuntimeException.class, () -> {
            greetClientService.sendUnaryBlocking();
        }, "Should throw exception when gRPC call fails");
    }

    @Test
    @DisplayName("Should return response from gRPC call")
    void testSendUnaryBlockingReturnsResponse() {
        // Given: Mock response
        HelloResponse expectedResponse = HelloResponse.newBuilder()
                .setGreetingMessage("Hello, test")
                .setQuestionMessage("What do you do for fun?")
                .build();

        when(blockingStub.sayHello(any(HelloRequest.class))).thenReturn(expectedResponse);

        // When: Call sendUnaryBlocking
        greetClientService.sendUnaryBlocking();

        // Then: Verify the response was received (method completes without exception)
        verify(blockingStub, times(1)).sayHello(any(HelloRequest.class));
    }

    @Test
    @DisplayName("Should handle null response from gRPC")
    void testSendUnaryBlockingHandlesNullResponse() {
        // Given: Mock null response
        when(blockingStub.sayHello(any(HelloRequest.class))).thenReturn(null);

        // When & Then: Should handle null response gracefully
        assertDoesNotThrow(() -> {
            greetClientService.sendUnaryBlocking();
        }, "Should handle null response without throwing exception");
    }
} 