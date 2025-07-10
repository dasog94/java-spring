# GreetClientService Integration Tests

이 디렉토리에는 `GreetClientService`의 `sendUnaryBlocking` 메서드를 테스트하는 통합 테스트가 포함되어 있습니다.

## 테스트 파일

- `GreetClientServiceIntegrationTest.java`: `@SpringBootTest`를 사용한 통합 테스트

## 테스트 실행 방법

### 1. 전체 테스트 실행
```bash
./gradlew test
```

### 2. 특정 테스트 클래스만 실행
```bash
./gradlew test --tests "GreetClientServiceIntegrationTest"
```

### 3. 특정 테스트 메서드만 실행
```bash
./gradlew test --tests "GreetClientServiceIntegrationTest.testSendUnaryBlocking"
```

## 테스트 구성

### 테스트 설정
- `@SpringBootTest`: 전체 Spring Boot 애플리케이션 컨텍스트를 로드
- `@ActiveProfiles("test")`: test 프로파일 사용
- `@TestPropertySource`: gRPC 포트 및 클라이언트 설정

### 테스트 케이스

1. **testSendUnaryBlocking()**: `sendUnaryBlocking` 메서드가 예외 없이 실행되는지 확인
2. **testDirectGrpcCall()**: 직접 gRPC 호출을 통해 응답 데이터 검증
3. **testSendUnaryBlockingWithMultipleCalls()**: 여러 번의 호출이 정상적으로 작동하는지 확인
4. **testDirectGrpcCallWithDifferentParameters()**: 다양한 파라미터로 gRPC 호출 테스트
5. **testGrpcServiceConfiguration()**: gRPC 서비스 설정이 올바른지 확인

## 요구사항

- Java 21
- Spring Boot 3.3.1
- gRPC Spring Boot Starter
- JUnit 5

## 문제 해결

### Java 환경 문제
JAVA_HOME이 설정되지 않은 경우:
```bash
export JAVA_HOME=/path/to/your/java
export PATH=$JAVA_HOME/bin:$PATH
```

### gRPC 포트 충돌
테스트 실행 시 포트 6565가 사용 중인 경우, `application-test.yml`에서 포트를 변경하세요.

## 테스트 결과 확인

테스트가 성공적으로 실행되면:
- gRPC 서버가 시작됨
- 클라이언트가 서버에 연결됨
- `sendUnaryBlocking` 메서드가 정상적으로 호출됨
- 응답 데이터가 예상대로 반환됨 