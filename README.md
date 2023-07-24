# java-was-2023

Java Web Application Server 2023

## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

---
## 학습 계획
- [x] HTTP 학습!
- [x] concurrent 패키지 공부
- [x] Concurrency 와 Parallelism
- [x] [강의](https://youtu.be/4kb448OJ7Mw) 듣기

- 구현 사항
  - HTTP RequestURI, Request, Response, Header 캡슐화
  - REST API Controller 생성
  - 404 에러, 400 에러 처리
  - request uri, resource path 상수 분리하여 관리
  - 요청 경로와 실제 경로가 같다는 가정하에 정적인 파일 response 할 수 있도
  - Thread -> concurrent 메소드로 이동
  - request line에서의 query parameter 저장
  - MIME enum 클래스 추가
  - 다양한 Content-Type 지원하도록
  - 빌더패턴 적용해 생성자 코드 깔끔하게 정리
  - Main 클래스 생성해서, 실행하는 코드 분리
  - request 통합 테스트
  - 회원가입 구현가
  - 로그인 성공 시 쿠키 생성하여 세션 저장

- 해야 할 일
  - HTTP wiki에 정리
  - HttpResponse Builder 패턴 적용하기
  - IO stream 읽고 쓰는 방식 처리할 방법 고민
  - 테스트 코드 작성..

### 메모
- request body 읽어오도록 수정