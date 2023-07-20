# java-was-2023
Java Web Application Server 2023


# Step2
## 미션 수행 목표
- [ ] : HTTP GET 프로토콜 개념 이해 및 정리
- [X] : "회원가입" 메뉴를 클릭하면 회원 가입 폼으로 이동
- [X] : 폼을 통해서 회원 가입을 할 수 있어야 한다.
  - [X] : 가입 버튼을 클릭하면 특정 형태로 사용자가 입력한 값이 서버로 전달
  - [X] : 사용자가 입력한 값을 파싱해서 model.User 클래스에 저장
- [ ] : Junit을 활용한 단위 테스트 적용
- 

## 미션 수행 기록
- 모든 클라이언트 요청에 대해 우선 검문하는 프론트 컨트롤러 구현
- HandlerMapper에서 핸들러를 발견하지 못하면 정적인 데이터이므로 정적인 데이터에 관한 응답 반환
- 핸들러(컨트롤러)를 발견하면 해당 핸들러를 실행시키고 적절한 응답 반환
- 현재 응답 객체를 생성하고, 응답할 때의 로직이 많이 지저분한 것 같아서 역할 분리가 필요하다.
- 테스트 코드를 하나도 작성하지 않았다.. 어떤 식으로 테스트해야 할까?? 


# Step1
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

## 추가 구현 및 공부 필요
- 테스트 코드 작성!
- 깃허브 위키에 HTTP 개념 정리
- 스레드 풀의 스레드 개수 지정할 때 고려해야할 사항들 찾아보기
- 현재 프로젝트와 적합한 스레드풀과 이유

