# step-5 쿠키를 이용한 로그인

## 기능 구현
- [x] 쿠키, 세션 공부
- [x] 로그인
  - [x] 아이디와 비밀번호가 같은지 확인
  - [x] 로그인 성공 시 응답 헤더에 Set-Cookie 설정(모든 요청에 대해 Cookie 처리가 가능하도록 Path="/"로 설정) 및 /index.html로 이동
  - [x] 로그인 실패 시 /user/login_failed.html로 이동
  - [ ] 단위 테스트
