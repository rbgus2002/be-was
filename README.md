# java-was-2023

Java Web Application Server 2023

## 프로젝트 정보

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was
를 참고하여 작성되었습니다.

## 기능 요구 사항

### step - 1

- [X] 특정 uri 로 접속했을 때, 해당 파일을 request 한다.
- [X] http request 내용을 읽고 logger 를 통해 확인한다.
- [X] 자바의 concurrent 패키지를 사용하도록 변경한다.

### step - 2

- [ ] 응답 메시지를 관리하는 클래스를 구현한다.
- [ ] GET 메소드를 통해 파라미터를 받는다.
    - [ ] Path를 파싱해 파라미터와 분리한다.
- [ ] 주어진 파라미터를 User 클래스에 담는다.

## 학습 내용

- 코드 분석 및 동작
  과정 - https://github.com/ddingmin/be-was/wiki/%EC%BD%94%EB%93%9C-%EB%B6%84%EC%84%9D-%EB%B0%8F-%EB%8F%99%EC%9E%91-%EA%B3%BC%EC%A0%95
- HTTP
  메시지 - https://github.com/ddingmin/be-was/wiki/HTTP-Request,-Response-%EB%A9%94%EC%8B%9C%EC%A7%80-%EA%B5%AC%EC%A1%B0
- 자바의 스레드 - https://github.com/ddingmin/be-was/wiki/%EC%9E%90%EB%B0%94%EC%9D%98-%EC%8A%A4%EB%A0%88%EB%93%9C
- 자바의 concurrent 패키지 - 