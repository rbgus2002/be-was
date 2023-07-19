# java-was-2023

Java Web Application Server 2023

## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.


## JAVA Thread Model

### 자바의 초기 Thread 모델 - Green Thread Model(Many To One)

Green Thread는 사용자 수준에서 생성되는 스레드로 VM 또는 런타임에 의해 관리되기 때문에 OS와 독립적으로 동작한다. 이는 CPU가 단일 코어일 때 설계된 Many to One Thread Model이다.
이러한 특성으로 동기화와 자원 공유의 관점에서 이점이 있지만 OS 수준에서는 하나의 프로세스로 인식 되기 때문에 멀티코어 환경에서 이점을 살릴 수 없다는 단점이 있다.

### 자바의 현재 Thread 모델 - Native Thread Model(Many To Many)

Native Thread는 멀티코어 환경에 맞춰 설계된 Many to Many Thread Model로 OS 수준에서 생성되고 관리되는 쓰레드이다.
OS단에서 직접 자원의 공유와 동기화 작업을 처리하기 때문에 멀티코어 환경에서 유리하다는 이점이 있다. 하지만 하나의 Native Thread만을 Green Thread Model에 비해 자원 공유와 동기화 문제로 인한 성능 저하를 발생시킬 이슈가 많다.




## Java Multi Thread

### Thread, Runnable(java.lang.Thread, java.lang.Runnable)

자바의 초기부터 멀티스레드 프로그래밍을 지원하기 위해 스레드의 생성과 실행을 관리하는 클래스를 Thread, 각 스레드에서 실행될 작업을 정의한 인터페이스를 Runnable이라고 한다.
이를 활용하여 사용자 수준의 스레드를 생성하고 실행할 수 있다. 하지만 스레드 풀 관리, 동기화 처리 등 여러가지 유틸리티를 사용하기 위해서는 개발자가 이를 직접 구현해야하는 어려움이 있다. 

### Concurrent Package(java.util.concurrent)

Thread와 Runnable 사용 시 필요로 하는 여러가지 유틸리티를 제공할 수 있도록 Java5 부터 Concurrent 패키지 지원하고 있다. 
이는 사용자 수준의 스레드를 효과적으로 관리할 수 있는 여러가지 클래스와 인터페이스를 제공하여 멀티스레딩 환경 개발에 도움되는 패키지이다.