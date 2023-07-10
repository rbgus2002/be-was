# java-was-2023

Java Web Application Server 2023

## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

---
## 학습내용(added by jst0951)
### step1
- Concurrent

참조 : https://ddd4117.github.io/2021/05/java-concurrent-%ED%8C%A8%ED%82%A4%EC%A7%80-%EC%A0%95%EB%A6%AC/

병렬 프로그래밍을 위한 기능들을 모아놓은 패키지이다. 각 기능은 아래와 같다.

Locks : 공유자원에 관한 기능들이 포함되어 있다.(mutex를 위한 lock() 등)

Atomic : 멀티쓰레드 환경에서 최신 데이터임이 보장되는 변수 생성

Executors : 쓰레드 관리

Queue : 멀티 쓰레드에 안전한 Queue 제공

Synchronizers : 특수 목적 동기화 처리용 클래스 모음

- Executor

사용해야 되는 이유 : https://cornswrold.tistory.com/538

참조1 : https://emong.tistory.com/221

참조2 : https://yonguri.tistory.com/136

참조3 : https://blog.voidmainvoid.net/312

참조4 : https://cornswrold.tistory.com/538

Executors는 쓰레드 풀 생성 및 관리를 간단히 처리할 수 있도록 한다.(쓰레드 재사용으로 인한 오버헤드 감소, 쓰레드 폭증으로 인한 애플리케이션 성능 저하 등)

Executors 클래스에는 ExecutorService의 구현체에 관련된 메서드들이 존재하는데, 이 중 Executor가 스스로 관리하는 스레드풀을 생성하기 위한 newXXXThreadPool() 메서드들이 존재한다.

각 스레드풀 종류에 따른 특성은 다음과 같다

singleThreadExecutor : 스레드를 하나만 생성(1개로 유지)

fixedThreadPool : 주어진 스레드 갯수만큼 생성하고, 스레드가 종료되면 다시 생성

cachedThreadPool : 처리할 스레드가 많아지면 그만큼 증가(최대 갯수까지), 60초간 작업이 없으면 다시 줄임

workStealingPool : cpu의 쓰레드 갯수에 맞춰 생성

또한, ExecutorService 객체에 작업을 넘기기 위해선 Runnable 객체를 넘겨주면 되는데, execute() 혹은 submit() 메서드를 활용하게 된다.

execute : 리턴 값 없음(작업 처리 결과를 받지 못함), 예외가 발생하면 해당 스레드를 스레드 풀에서 제거함

submit : Future 리턴(Future를 통해 작업 처리 결과를 알 수 있음), 예외가 발생해도 스레드는 종료되지 않고 다른 작업에 재사용될 수 있음.

따라서 일반적으론 submit을 활용하는 것이 좋다.

---