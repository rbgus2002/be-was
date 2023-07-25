# java-was-2023

Java Web Application Server 2023

## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

## 요구사항

### step1 - index.html 응답
- [x] 정적인 html 파일 응답 
  - http://localhost:8080/index.html 접속시 index.html 파일을 읽어 응답
  
- [x] HTTP Request 내용 출력
  - 서버로 들어오는 HTTP Request 내용을 읽고 log.debug로 출력

- [x] 기존 Thread 기반 프로젝트를 Concurrent 패키지를 사용하도록 수정

### step2 - GET으로 회원가입
- [x] '회원가입' 클릭시 회원가입 폼 표시
- [x] 회원가입 폼에서 '가입' 클릭시 전달되는 사용자 입력값을 파싱해 model.User 클래스에 저장
  - HTML과 URL을 비교 후 파싱할 것
  - 유지보수가 편하도록!
- [x] Junit을 활용한 단위테스트

### step3 - 다양한 컨텐츠 타입 지원
- [x] html, css, js, ico, png, jpg 확장자를 지원해야 한다
- [x] static 폴더 내의 정적 콘텐츠에 대한 요청이 정상적으로 처리되어야 한다

### step4 - POST로 회원 가입
- [ ] 회원가입 POST로 수정!
  - [ ] user/form.html 파일의 form 태그 method를 post로 수정
  - [ ] body에서 인자 읽어오기
- [ ] 가입 완료시 index.html 페이지로 리다이렉트
  - [ ] HTTP redirect 기능 구현하기
  - [ ] statusCode 302로 설정

---
## 학습할 내용

### step1 - index.html 응답
- [x] [Web Server, WAS](https://github.com/SeoSiun/be-was/wiki/Web-Server-&-WAS)
- [x] 자바 스레드 모델에 대해 학습 (버전별 변경점, 향후 지향점)
  - [x] [JVM](https://github.com/SeoSiun/be-was/wiki/JVM-(Java-Virtual-Machine))
  - [x] [Thread & Java Thread model](https://github.com/SeoSiun/be-was/wiki/%EC%93%B0%EB%A0%88%EB%93%9C-(Thread))
- [x] [자바 Concurrent 패키지](https://github.com/SeoSiun/be-was/wiki/Concurrent-%ED%8C%A8%ED%82%A4%EC%A7%80(Java.util.concurrent))
- [x] [HTTP](https://github.com/SeoSiun/be-was/wiki/HTTP)
- [x] [입출력 Stream](https://github.com/SeoSiun/be-was/wiki/%EC%9E%85%EC%B6%9C%EB%A0%A5-Stream)
- [x] [Socket](https://github.com/SeoSiun/be-was/wiki/Socket)
