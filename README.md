# java-was-2023

Java Web Application Server 2023

## 프로젝트 정보

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was
를 참고하여 작성되었습니다.

## step-1 index.html 응답

> **키워드**<br>
> thread, concurrent, HTTP

### 구현 기능

- [x] 자바 스레드, concurrent 패키지 학습
    - [📖 Java Concurrency](https://github.com/mingulmangul/be-was/wiki/Java-Concurrency)
- [x] 소켓 연결 스레드 생성 시 concurrent 패키지를 사용하도록 변경
    - Thread(`thread.start(Runnable)`) ➡️ concurrent(`executor.execute(Runnable)`
- [x] 정적 파일 응답 (`/index.html`로 접속 시 프로젝트 내 `index.html` 파일을 읽어와 응답)
    - HTTP request 로그 출력
    - HTTP request 파싱
    - 파일 검색 후 읽기
    - HTTP response 메세지 작성 후 클라이언트에게 응답
- [x] 프로젝트 구조 개선
    1. 코드의 의미가 명확해지도록 별도의 클래스에 데이터를 감싸서 저장하였다.

        - `HttpRequest`, `HttpResponse`, `HttpMethod`, `HttpHeaderFields`, `URL`, `HttpStatus` 클래스를 만들어 HTTP 메세지 정보를 담았다.

    2. 유지보수에 좋은 구조로 개선하기 위해 요청을 처리하는 과정의 역할을 쪼개 클래스를 분리하였다.

        - `FrontController`가 중심이 되어 각 과정을 수행하는 클래스와 통신한다.
        - 관련 클래스에 대한 설명
            - `RequestHandler`: 클라이언트로부터 HTTP 요청을 받고 FrontController가 작성한 response를 바탕으로 HTTP 응답으로 보낸다.
            - `FrontController`: request를 받고 response를 작성하는 전체 과정을 관리한다. request, response를 받아서 요청에 따른 컨트롤러를 찾고 실행한 후 알맞은
          view(파일)을 찾아 읽고 response에 작성한다.
            - `Controller`: url별 로직을 수행할 컨트롤러는 Controller 인터페이스를 구현해야 한다.
            - `ControllerMapper`: 특정 url을 처리하는 컨트롤러를 찾는다.
            - `ViewResolver`: view(파일)의 논리 이름을 물리 이름으로 바꾼다.

## step-2 GET으로 회원가입

> **키워드**<br>
> HTTP GET

- [x] 프로젝트 개선
    1. 웹 서버 시작 시 모든 컨트롤러를 스캔해 인스턴스 생성 후 저장해 관리하도록 변경
        - Reflection을 이용해 `@Controller` 클래스 어노테이션을 찾아 읽도록 구현하였다.
    2. 웹 서버 시작 시 모든 컨트롤러 메소드를 스캔해 url 및 http method와 매핑 및 저장하도록 변경
        - `@RequestMapping` 메소드 어노테이션을 구현하고 Reflection을 이용하였다.
    3. 정적 파일 요청을 처리하는 로직과 컨트롤러 호출이 필요한 요청을 처리하는 로직을 분리
        - `StaticFileResolver` : 정적 파일 요청이 들어오면, `/resource`에서 파일을 찾아 바로 리턴한다.
        - `ControllerResolver` : 그 외 요청이 들어오면, 해당 요청과 매핑되는 컨트롤러 메소드를 찾아 실행한다.
    4. 클라이언트에게 응답 메세지를 보내는 코드의 중복 제거
        - 응답 메세지 전송은 `HttpResponse`에서만 수행하도록 정리
- [x] GET으로 회원가입
    - GET 파라미터 파싱 (`URL` 객체에 저장)
    - 해당 요청을 처리하는 컨트롤러 메소드에 필요한 매개변수 주입
        - `ControllerResolver`에서 메소드를 호출할 때 `@RequestParam` 어노테이션이 붙은 매개변수에 필요한 값(파싱한 요청 파라미터 값)을 주입한다.
    - `User` 생성 후 DB에 회원 정보 저장
- [ ] 단위 테스트

## step-3 다양한 컨텐츠 타입 지원

> **키워드**<br>
> HTTP Response<br>
> MIME

- [x] 다양한 컨텐츠 타입 지원
    - 서버의 `/static`에 위치한 정적 리소스에 대한 요청 처리
