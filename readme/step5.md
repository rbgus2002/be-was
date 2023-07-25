# 기능 목록 및 학습 정리

## 기능 목록
- [X] 로그인에 성공하면, 홈화면으로 리다이렉트한다.

## 학습 정리
- 쿠키, 세션의 차이점
- 영속 쿠키, 세션 쿠키

## 고민
- 세션을 Controller의 Response Entity에서 주입했으나, Controller가 세션의 로직을 갖는 것이 맞는지 의문이 들었다. 따라서, 이를 CookieUtil을 통해 관리하게 하고 ResponseEntity에서 setCookie를 Builder 패턴으로 구현했다.
