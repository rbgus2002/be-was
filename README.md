# java-was-2023

Java Web Application Server 2023

## 미션 수행 목표
- 깃허브 위키에 개념 학습 내용 정리 
  - [X] : 웹 서버
  - [X] : 스레드
  - [X] : 자바 Concurrent 패키지
  - [ ] : HTTP
- [X] : 서버 접속 시 src/main/resources/templates 디렉토리의 파일을 읽어 클라이언트에 응답
- [X] : HTTP Request 내용을 읽고 logger로 출력
- [X] : 자바 Concurrent 패키지를 사용하도록 변경

## 미션 수행 기록
- RequestParser가 requestLine과 requestHeaders에 대해 파싱
- 파싱한 내용으로 HttpRequest 객체 생성
- HttpRequest의 show 메서드를 통해 Request 내용 출력
- 자바 concurrent 패키지의 Executors 클래스를 통해 FixedThreadPool 생성
- Runtime.getRuntime().availableProcessors()을 통해 JVM에서 실행 가능한 프로세스 수로 스레드 풀의 스레드 수로 지정했지만,
  어떤 것들을 고려해서 쓰레드의 개수를 지정해야할 지 잘 모르겠다.
- 또한, 다양한 Executors 클래스의 쓰레드 풀 종류 중 FixedThreadPool을 현재 프로젝트에 적용했을 때 어떤 고유한 장점을 가지는지 잘 모르겠다.   

## 추가 구현 및 공부 필요 사항
- 테스트 코드 작성!
- 깃허브 위키에 HTTP 개념 정리
- 스레드 풀의 스레드 개수 지정할 때 고려해야할 사항들 찾아보기
- 현재 프로젝트와 적합한 스레드풀과 이유

