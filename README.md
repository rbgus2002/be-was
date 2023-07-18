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

### 기능 구현 커밋
- [X] HTTP Request Message 출력
- [X] /index.html 요청 시 응답
- [X] Thread -> Concurrent 패키지 사용
- [X] GET으로 회원가입하기

## Wiki page
https://github.com/plusjob70/be-was/wiki
