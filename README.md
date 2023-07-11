# java-was-2023

Java Web Application Server 2023

## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

## 학습한 내용
### 자바 동시성 프로그래밍
- 자바1.0
  - Thread클래스와 Runnable 인터페이스 사용
  - synchronized 키워드 사용
- 자바5.0
  - ReentrantLock클래스와 Condition인터페이스
  - volatile 키워드
  - concurrent패키지
- 자바8
  - Stream API
  - CompletableFuture클래스
- 자바9
  - 리액티브 프로그래밍
  - Flow API

### concurrent패키지
- 스레드 풀
- 블로킹 큐
- 동시성 컬렉션
- Atomic Variables
- 동기화 유틸리티
- 동시성 유틸리티

### 동시성과 병렬성

- 동시적: 여러개의 논리적인 통제흐름
- 병렬적: 물리적인 병렬계산
- keyword
  - 비결정성
  - 메모리 가시성
  - 공유 메모리

### 스레드와 잠금장치

- 여러개의 스레드가 공유된 메모리에 접근할 때 문제가 발생한다.
- 이를 해결하려면 메모리에 대한 접근을 동기화 해야 한다.
- 여러 방법 중 하나는 객체 내부의 잠금장치(뮤텍스, 모니터 혹은 임계영역)를 이용하는 것


- 스레드 순서를 고려한 결과 외에도 예상치 못한 결과가 나올 수 있다.
  - 컴파일러는 코드가 실행되는 순서를 바꿈으로써 정적 최적화를 수행할 수 있다.
  - JVM은 코드가 실행되는 순서를 바꿈으로써 동적 최적화를 수행할 수 있다.
  - 코드를 실행하는 하드웨어도 코드의 순서를 바꾸는 것이 가능하다.
- 스레드가 동작한 결과가 다른 스레드에 보이지 않는 경우도 존재한다.
- 여러개의 잠금장치
  - 모든 스레드가 필요한 잠금장치를 얻을 수 없는 경우가 있다.
  - 잠금장치에 순서를 매긴다.
  - 커다란 프로그램에서는 모든 잠금장치에 순서를 매기는 것이 어렵다.
- keyword
  - 내재된 잠금장치
  - 경쟁조건
  - 메모리 가시성
  - 데드락(여러개의 잠금장치)
  - 자바 메모리 모델
    - [JSR 133-FAQ](http://www.cs.umd.edu/~pugh/java/memoryModel/jsr-133-faq.html)
    - http://www.cs.umd.edu/~pugh/java/memoryModel/
  - 다이닝 필로소퍼 문제
  - double-checked locking 안티패턴

## 동작 방식
1. 서버소켓을 열고 클라이언트를 기다린다.
2. 클라이언트가 연결되면 Runnable 인터페이스를 구현한 RequestHandler로 스레드를 만든다.
3. 스레드는 스레드 풀을 통해 관리한다.
4. Handler에서는 http get요청을 받아 이를 해석하고 response를 작성하여 클라이언트에 보낸다.