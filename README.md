# java-was-2023

Java Web Application Server 2023


## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.


## step 1
### 기능 요구사항
1. 정적인 html 파일 응답
    - http://localhost:8080/index.html 로 접속했을 때 `src/main/resources/templates` 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
2. HTTP Request 내용 출력
    - 서버로 들어오는 HTTP Request의 내용을 읽고 로거(log.debug)를 이용해 출력한다.

## step 2
### 기능 요구사항
1. GET으로 회원가입 기능 구현

## step 3
### 기능 요구사항
1. stylesheet와 파비콘 등 다양한 컨텐츠 타입을 지원하도록 개선

## step 4
### 기능 요구사항
1. POST로 회원가입 기능 구현
2. 가입 완료 후 index.html로 리다이렉션

## step 5
### 기능 요구사항
1. 가입된 회원 정보로 로그인 기능 구현
2. 로그인 성공 시 index.html로 이동
3. 로그인 실패 시 login_failed.html 페이지로 이동

## step 6
### 기능 요구사항
1. 동적인 HTML 구현

## 기능 구현 커밋
- [X] HTTP Request Message 출력
- [X] /index.html 요청 시 응답
- [X] Thread -> Concurrent 패키지 사용
- [X] GET으로 회원가입하기
- [X] 다양한 컨텐츠 타입 지원
- [X] POST로 회원가입 하기
- [X] 로그인 기능
  - [X] 세션 구현
  - [X] 쿠키 구현
- [X] 로그인 성공 및 실패 시 페이지 이동
- [X] 동적 페이지 로딩 구현
- [X] @Template 어노테이션 추가
- [X] 동적 렌더링을 위한 클래스 구현

## Wiki page
https://github.com/plusjob70/be-was/wiki
