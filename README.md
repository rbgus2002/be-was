# java-was-2023
Java Web Application Server 2023

# Step6
## 미션 수행 목표
- [X] : 사용자가 로그인 상태일 경우 /index.html에서 사용자 이름 표시
- [X] : 사용자가 로그인 상태가 아닐 경우 /index.html에서 [로그인] 버튼을 표시해 준다.
- [X] : 사용자가 로그인 상태일 경우 http://localhost:8080/user/list 에서 사용자 목록을 출력한다.
- [X] : http://localhost:8080/user/list 접근시 로그인하지 않은 상태일 경우 로그인 페이지(login.html)로 이동한다.
- [X] : 로그아웃 구현하기 

## 미션 수행 기록
- 쿠키와 세션을 이용하면 요청을 보낸 클라이언트가 로그인했는지 확인할 수 있다.
- 현재는 /index.html 요청 시 정적인 html 페이지를 보여주고 있다.
- 이제는 /index.html 요청에 대해 페이지를 동적으로(로그인/로그아웃) 랜더링해서 보여줘야 된다.
- 로그아웃 기능은 쿠키 만료 시간을 현재 시간보다 1분 빠르게 설정해서 쿠키가 만료되도록 설정했다.

## 리팩토링 내역
- 패키지 구조 개선
  - java 패키지를 하나의 프레임워크라 생각하고 패키지 구조를 변경하게 되었다.
  - 기존 webserver 패키지 안에 controller, http 패키지가 존재했지만, 밖으로 분리시켰다.
- 세션을 저장하고 관리하는 세션매니저의 map 필드에 value 값으로 Session 객체를 저장하도록 변경
  - 세션 객체는 객체 값과 만료 시간을 필드로 가지고 있다.
- FrontController를 싱글톤 패턴으로 변경
  - 싱글톤 패턴을 통해서 메모리 절약을 할 수 있고, 요청에 대한 응답 속도가 빨라진다.
  - 하지만 상태 공유나 동시성 문제를 잘 처리해야 한다.
- 


# Step5
## 미션 수행 목표
- [X] : 가입한 회원 정보로 로그인할 수 있다.
- [X] : [로그인] 메뉴를 클릭하면 user/login.html로 이동
- [X] : 로그인 성공 시 index.html로 이동
- [X] : 로그인 실패 시 /user/login_failed.html로 이동
- [X] : 로그인 시 클라이언트에게 세션 id가 담긴 쿠키 반환
- [X] : 서버에서는 User 정보가 담긴 세션을 관리한다.
-  

## 미션 수행 기록
- 가입된 회원 정보로 로그인을 할 수 있어야 된다.
- 회원 가입 정보로 로그인 하면 세션에 데이터 저장하고 쿠키 발급
  - 로그인 성공 시 index.html로 이동 (쿠키 발급O)
  - 로그인 실패 시 실패 페이지로 이동 (쿠키 발급X)
- response = Set-Cookie: yummy_cookie=choco
- 요청 -> 핸들러 찾아서 실행 -> 실행한 결과를 토대로 응답 데이터 생성(쿠키 및 세션 생성) -> 응답
- 로그인 성공 -> index.html로 리다이렉트 및 쿠키 반환
- UserController에서 viewpath와 model 객체를 가지는 ModelAndView를 반환하도록 기능 추가
- 응답 헤더의 쿠키에 쿠키가 만들어진 시간으로부터 10분 뒤를 Expires로 설정
  - 이때, LocalDateTime 형식을 GMT 형식으로 바꿔주기 위한 GMTStringConverter 추가
- 현재 다양한 상황에 대한 예외 처리가 많이 부족한 상태인 것 같다.

# Step4
## 미션 수행 목표
- [X] : HTTP POST의 동작 방식 이해
- [X] : redirection 기능 이해
- [X] : 회원가입 구현 (GET -> POST 수정)
- [X] : 회원가입 후 리다이렉션을 통해 페이지 이동 (/index.html 페이지로 이동)

## 미션 수행 기록
- get 요청 방식을 post 요청으로 변경
  - 기존의 쿼리스트링으로 들어오던 폼 데이터는 post로 변경 시 requestBody로 들어온다.
  - requestBody를 파싱할 수 있도록 코드 추가
  - form.html의 method도 post로 변경
- 기존의 Controller 인터페이스를 구현한 방식을 수정
- @RequestMapping 구현
  - 컨트롤러의 메서드 단위에 해당 어노테이션을 붙이고 method와 value를 지정한다.
  - HandlerMapper의 map에 requestMethod와 value 값을 필드로 가지는 ValueAndMethod 객체를 key로 지정한다.
  - 리플렉션을 통해 얻은 Method 객체를 map의 value로 지정한다.
- @Controller 구현
  - 컨트롤러 클래스 단위에 해당 어노테이션을 붙인다.
  - HandlerMapper 클래스의 static 블록에서 해당 어노테이션이 붙은 클래스(컨트롤러)의 메서드들을 HandlerMapper의 map 필드에 저장한다.
- 요청이 들어오면 HandlerAdapter에서 컨트롤러의 메서드를 실행한다.

# Step3
## 미션 수행 목표
- [ ] : HTTP Response 학습 및 위키 정리
- [X] : MIME 타입 이해하고 적용하기 (서버의 static 폴더에 있는 정적 컨텐츠들에 대한 요청 정상 처리)

## 미션 수행 기록
- MIME 타입 관리하는 Enum 클래스 생성
- Path에 대해서도 static 경로와 templates 경로를 관리하는 Enum 클래스 생성
- requestUri 정보의 확장자를 파싱하고 해당 확장자에 알맞는 경로를 설정
- 요청 파일에 알맞는 body와 header를 담아 응답하도록 처리

## 변경 필요 사항
- FrontController 부분에 대한 예외 처리 및 확장성 고려 필요
- UserController 의 doGet 방식도 별로 좋진 않은 것 같다.
- 테스트 코드 추가 해야한다! 

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

