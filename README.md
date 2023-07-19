# java-was-2023

Java Web Application Server 2023

## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

## step-1 index.html 응답

> **키워드**<br>
> thread, concurrent, HTTP

### 구현 기능

- [ ] 자바 스레드, concurrent 패키지 학습
  - [📖 Java Concurrency](https://github.com/mingulmangul/be-was/wiki/Java-Concurrency)
- [ ] 소켓 연결 스레드 생성 시 concurrent 패키지를 사용하도록 변경
  - Thread(`thread.start(Runnable)`) ➡️ concurrent(`executor.execute(Runnable)`
- [ ] 정적 파일 응답 (`/index.html`로 접속 시 프로젝트 내 `index.html` 파일을 읽어와 응답)
  - HTTP request 로그 출력
  - HTTP request 파싱
  - 파일 검색 후 읽기
  - HTTP response 메세지 작성 후 클라이언트에게 응답
- [ ] 프로젝트 구조 개선
  1. 코드의 의미가 명확해지도록 별도의 클래스에 데이터를 감싸서 저장하였다.
    - `HttpRequest`, `HttpResponse`, `HttpMethod`, `HttpHeaderFields`, `URL`, `HttpStatus` 클래스를 만들어 HTTP 메세지 정보를 담았다.
  2. 유지보수에 좋은 구조로 개선하기 위해 요청을 처리하는 과정의 역할을 쪼개 클래스를 분리하였다.
    - `FrontController`가 중심이 되어 각 과정을 수행하는 클래스와 통신한다.
    - 관련 클래스에 대한 설명
      - `RequestHandler`: 클라이언트로부터 HTTP 요청을 받고 FrontController가 작성한 response를 바탕으로 HTTP 응답으로 보낸다.
      - `FrontController`: request를 받고 response를 작성하는 전체 과정을 관리한다. request, response를 받아서 요청에 따른 컨트롤러를 찾고 실행한 후 알맞은 view(파일)을 찾아 읽고 response에 작성한다.
      - `Controller`: url별 로직을 수행할 컨트롤러는 Controller 인터페이스를 구현해야 한다.
      - `ControllerMapper`: 특정 url을 처리하는 컨트롤러를 찾는다.
      - `ViewResolver`: view(파일)의 논리 이름을 물리 이름으로 바꾼다.
