# java-was-2023

Java Web Application Server 2023

## 프로젝트 정보

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was
를 참고하여 작성되었습니다.

## 기능 목록

- http://localhost:8080/index.html 로 접속했을 때 src/main/resources/templates 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.

- 서버로 들어오는 HTTP Request의 내용을 읽고 로거(log.debug)를 이용해 출력한다.

- “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동, 회원가입 폼을 표시한다.

- “회원가입” 메뉴 폼을 통해서 회원가입을 할 수 있다.
    - 회원가입을 POST로 구현
    - 가입을 완료하면 /index.html 페이지로 이동한다.

- 다양한 컨텐츠 타입을 지원하도록 개선

- 가입한 회원 정보로 로그인을 할 수 있다.
    - 로그인이 성공하면 index.html로 이동한다.
    - 로그인이 실패하면 /user/login_failed.html로 이동한다.
    - 로그인이 성공할 경우 HTTP 헤더의 쿠키 값을 SID=세션 ID 로 응답한다.

- [로그인] 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 로그인할 수 있다.

- 사용자가 로그인 상태일 경우 /index.html에서 사용자 이름을 표시해 준다.

- 사용자가 로그인 상태가 아닐 경우 /index.html에서 [로그인] 버튼을 표시해 준다.

- 사용자가 로그인 상태일 경우 http://localhost:8080/user/list 에서 사용자 목록을 출력한다.

- http://localhost:8080/user/list  페이지 접근시 로그인하지 않은 상태일 경우 로그인 페이지(login.html)로 이동한다.

