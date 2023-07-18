# java-was-2023

Java Web Application Server 2023

## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.


## Mission 1. 웹 서버 1단계 - index.html 응답

### 기능요구사항
- 정적인 html 파일 응답: http://localhost:8080/index.html로 접속 시 해당 파일 응답.
- HTTP Request 내용 출력: HTTP Request의 내용을 로거를 이용해 출력.

### 프로그래밍 요구사항
- 프로젝트 분석
- 구조 변경
  - [자바 스레드 모델](https://github.com/jjy0709/be-was/wiki/%5BJava%5D-Thread-Class)에 대해 학습한다. 
  - [자바 Concurrent 패키지](https://github.com/jjy0709/be-was/wiki/%5BJava%5D-Concurrent-Package)에 대해 학습한다.
  - 기존의 프로젝트를 Concurrent 패키지를 사용하도록 변경한다.
- OOP와 클린 코딩
  - 기능요구사항을 만족하는 코드 작성
  - 유지보수에 좋은 구조에 대해 고민

### 구현에 대한 설명
1. HttpRequest가 들어왔을 때 RequestHandler로 내용이 들어온다.
2. 이를 처리하고 Request에 따라 다른 작업을 실행하기 위해서는 Request를 공유해야 한다.
    - Request 공유를 위해 Java의 Request class를 사용하려 하였다.
    - method 사용이 불편 -> version, uri 등의 type/null check 문제와 header의 메소드 등
    - Java.HttpRequest class를 참고하여 새롭게 HttpRequest class 및 builder를 생성하였다.
3. 새롭게 생성한 HttpRequest 클래스를 이용하여 코드를 구현하였다.
4. Concurrent Package의 ExecutorService와 Executors 등을 이용해 코드를 수정하였다.
