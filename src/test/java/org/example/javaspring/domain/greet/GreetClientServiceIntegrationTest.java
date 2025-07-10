package org.example.javaspring.domain.greet;

import org.example.javaspring.domain.greet.proto.HelloRequest;
import org.example.javaspring.domain.greet.proto.HelloResponse;
import org.example.javaspring.domain.greet.proto.GreeterServiceGrpc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GreetClientServiceIntegrationTest {

    @Autowired
    private GreetClientService greetClientService;

    @Autowired
    private GreeterServiceGrpc.GreeterServiceBlockingStub blockingStub;

    @Test
    @DisplayName("Should successfully call sendUnaryBlocking method")
    void testSendUnaryBlocking() {
        // Given: The application context is loaded and gRPC server is running
        
        // When & Then: Call sendUnaryBlocking method without throwing exception
        assertDoesNotThrow(() -> {
            greetClientService.sendUnaryBlocking();
        }, "sendUnaryBlocking should execute without throwing any exception");
    }

    @Test
    @DisplayName("Should make direct gRPC call and receive valid response")
    void testDirectGrpcCall() {
        // Given: A HelloRequest with test data
        HelloRequest request = HelloRequest.newBuilder()
                .setName("test")
                .setAge(10)
                .setMessage("Hello, Glad to meet you.")
                .build();

        // When: Make direct gRPC call
        HelloResponse response = blockingStub.sayHello(request);

        // Then: Verify response is not null and contains expected data
        assertNotNull(response, "Response should not be null");
        assertNotNull(response.getGreetingMessage(), "Greeting message should not be null");
        assertNotNull(response.getQuestionMessage(), "Question message should not be null");
        assertTrue(response.getGreetingMessage().contains("test"), 
                "Greeting message should contain the name 'test'");
        assertEquals("What do you do for fun?", response.getQuestionMessage(),
                "Question message should match expected value");
    }

    @Test
    @DisplayName("Should handle multiple calls to sendUnaryBlocking")
    void testSendUnaryBlockingWithMultipleCalls() {
        // Given: The service is available
        
        // When & Then: Verify the method can be called multiple times
        assertDoesNotThrow(() -> {
            // First call
            greetClientService.sendUnaryBlocking();
            
            // Second call should also work
            greetClientService.sendUnaryBlocking();
            
            // Third call for thorough testing
            greetClientService.sendUnaryBlocking();
        }, "Multiple calls to sendUnaryBlocking should work without issues");
    }

    @Test
    @DisplayName("Should handle different request parameters in gRPC calls")
    void testDirectGrpcCallWithDifferentParameters() {
        // Given: Different HelloRequest parameters
        HelloRequest request1 = HelloRequest.newBuilder()
                .setName("alice")
                .setAge(25)
                .setMessage("Hello from Alice")
                .build();

        HelloRequest request2 = HelloRequest.newBuilder()
                .setName("bob")
                .setAge(30)
                .setMessage("Hello from Bob")
                .build();

        // When: Make gRPC calls with different parameters
        HelloResponse response1 = blockingStub.sayHello(request1);
        HelloResponse response2 = blockingStub.sayHello(request2);

        // Then: Verify both responses are valid and contain correct data
        assertNotNull(response1, "First response should not be null");
        assertNotNull(response2, "Second response should not be null");
        
        assertTrue(response1.getGreetingMessage().contains("alice"), 
                "First response should contain 'alice'");
        assertTrue(response2.getGreetingMessage().contains("bob"), 
                "Second response should contain 'bob'");
        
        assertEquals("What do you do for fun?", response1.getQuestionMessage(),
                "Both responses should have the same question message");
        assertEquals("What do you do for fun?", response2.getQuestionMessage(),
                "Both responses should have the same question message");
    }

    @Test
    @DisplayName("Should verify gRPC service is properly configured")
    void testGrpcServiceConfiguration() {
        // Given: The test context is loaded
        
        // When & Then: Verify that required beans are properly injected
        assertNotNull(greetClientService, "GreetClientService should be injected");
        assertNotNull(blockingStub, "GreeterServiceBlockingStub should be injected");
        
        // Verify that the service can make a basic call
        assertDoesNotThrow(() -> {
            HelloRequest request = HelloRequest.newBuilder()
                    .setName("config-test")
                    .setAge(1)
                    .setMessage("Configuration test")
                    .build();
            
            HelloResponse response = blockingStub.sayHello(request);
            assertNotNull(response, "Response should be received for configuration test");
        }, "Basic gRPC call should work to verify configuration");
    }
} 