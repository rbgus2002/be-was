# java-was-2023

Java Web Application Server 2023

## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

# Step-1
## 구현 사항
- [x] Concurrent 패키지의 Executors 클래스를 사용하여 멀티 스레드 방식을 적용
- [x] 소켓 입출력에 버퍼링을 적용
- [x] resources 디렉터리의 모든 파일에 대한 요청을 처리
- [x] 존재하지 않는 리소스 요청 시, 404 페이지를 404 status code로 응답
- [x] resources 디렉터리의 모든 파일(html, css, js, png, eot, svg, ttf, woff, woff2, ico)에 대한 컨텐츠 타입을 지원
- [x] Request-URI가 '/'인 경우, 인덱스 페이지를 응답

## 학습 내용
- HTTP
  - [HTTP/HTTPS](https://github.com/csct3434/be-was/wiki/HTTP%EC%99%80-HTTPS)
  - [HTTP 요청 및 응답](https://github.com/csct3434/be-was/wiki/HTTP-%EC%9A%94%EC%B2%AD-%EB%B0%8F-%EC%9D%91%EB%8B%B5)
  - [SSL/TLS](https://github.com/csct3434/be-was/wiki/SSL-TLS)
  - [세션](https://github.com/csct3434/be-was/wiki/%EC%84%B8%EC%85%98)
- MVC
  - [웹 애플리케이션의 이해](https://github.com/csct3434/be-was/wiki/MVC-%E2%80%90-1.-%EC%9B%B9-%EC%95%A0%ED%94%8C%EB%A6%AC%EC%BC%80%EC%9D%B4%EC%85%98%EC%9D%98-%EC%9D%B4%ED%95%B4)
  - [서블릿](https://github.com/csct3434/be-was/wiki/MVC-%E2%80%90-2.-%EC%84%9C%EB%B8%94%EB%A6%BF)
  - [서블릿, JSP, MVC패턴](https://github.com/csct3434/be-was/wiki/MVC-%E2%80%90-3.-%EC%84%9C%EB%B8%94%EB%A6%BF,JSP,-MVC%ED%8C%A8%ED%84%B4)
  - [MVC 프레임워크 만들기](https://github.com/csct3434/be-was/wiki/MVC-%E2%80%90-4.-MVC-%ED%94%84%EB%A0%88%EC%9E%84%EC%9B%8C%ED%81%AC-%EB%A7%8C%EB%93%A4%EA%B8%B0)
  - [구조 이해](https://github.com/csct3434/be-was/wiki/MVC-%E2%80%90-5.-%EA%B5%AC%EC%A1%B0-%EC%9D%B4%ED%95%B4)
  - [메시지 컨버터](https://github.com/csct3434/be-was/wiki/MVC-%E2%80%90-6.-%EB%A9%94%EC%8B%9C%EC%A7%80-%EC%BB%A8%EB%B2%84%ED%84%B0)
- ETC
  - [Logging](https://github.com/csct3434/be-was/wiki/%EB%A1%9C%EA%B9%85)
  - [Socket](https://github.com/csct3434/be-was/wiki/%EC%86%8C%EC%BC%93)
  - [Java Thread](https://github.com/csct3434/be-was/wiki/%EC%9E%90%EB%B0%94-%EC%93%B0%EB%A0%88%EB%93%9C)
  - [Concurrent package](https://github.com/csct3434/be-was/wiki/concurrent-package)