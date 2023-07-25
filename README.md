# java-was-2023

Java Web Application Server 2023

## step-5 쿠키를 이용한 로그인

- [x]  회원 가입 후 로그인
- [x]  로그인 성공 시 HTTP header의 Set-Cookie 값 `SID = 세션 ID; Path=/`로 응답
- [x]  로그인 실패 시 /user/login_failed.html로 redirection
- [x]  Controller, ControllerMappingHandler에 싱글톤 패턴 적용

### 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.
