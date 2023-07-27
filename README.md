# java-was-2023

Java Web Application Server 2023

## 프로젝트 정보

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was
를 참고하여 작성되었습니다.


---


## Requirements
#### Web Server step-1
- [x] 클라이언트의 요청에 맞게 'src/main/resources/templates' 디렉토리의 파일을 읽어 응답한다
- [x] 서버로 들어오는 요청을 log.debug를 이용해 출력한다
- 유지보수에 좋은 구조에 대해 고민하고 코드 개선하기
  - [x] http 헤더 구조 객체 생성 (HttpHeader)
- [x] Java Thread 기반에서 Concurrent 패키지 사용하도록 수정
- [x] Github 위키에 학습 내용 기록
#### Web Server step-2
- [x] GET을 통한 회원가입 구현
  - [x] Dispatcher Servlet 구현
#### Web Server step-3
- 다양한 Content-Type 지원하도록 구현
  - [x] html
  - [x] css
  - [x] js
  - [x] ico
  - [x] png
  - [x] jpg 
#### Web Server step-4
- [x] POST를 통한 회원가입 구현
  - [x] HttpRequest 객체에 body 추가&적용 
- [x] redirection 기능 구현
  - [x] 회원가입 완료하면 "/index.html'로 redirection
  - [x] http status code 302 사용
#### Web Server step-5
- 로그인
  - [x] 로그인 메뉴 클릭 시 /user/login.html 이동
  - 성공
    - [x] redirect
    - [x] 응답 header의 Set-Cookie 값 설정
    - [x] user 정보에 접근할 수 있는 세션 ID 적용
  - [x] 로그인 실패 시 /user/login_failed.html 이동
#### Web Server step-6
- 동적인 HTML 구현
  - 로그인 상태O
    - [x] 사용자 이름 표시
    - [x] 사용자 목록 표시 (/user/list)
  - 로그인 상태X
    - [x] 로그인 버튼 표시
    - [x] 사용자 목록 페이지 접근 시도 시 로그인 페이지로 이동

## To Study List
#### Web Server step-1
- 단순 구현이 아닌 동작 원리 파악하기
  - [x] 자바 스레드 모델 
    - [x] 버전별 변경점
    - [ ] 향후 지향점
  - [x] Concurrent 패키지 학습
#### Web Server step-2
- [x] GET 프로토콜 이해하기
#### Web Server step-3
- [x] MIME 타가입 이해하기
#### Web Server step-4
- [x] 자주 사용하는 status code 정리
#### Web Server step-5
- [x] 쿠키, 세션 간단하게 학습
#### Web Server step-6
-
