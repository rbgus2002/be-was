# java-was-2023

Java Web Application Server 2023

---

## 구현 내용
### [Step-1] index.html 응답
- [x] 정적 파일 응답
- [x] HTTP Request 내용 Logger 출력
- [x] Thread기반 프로젝트를 Concurrent 패키지를 사용하도록 변경

### [Step-2] GET으로 회원가입
- [x] "회원가입" 메뉴를 클릭하면 회원가입 폼을 표시한다 
- [x] 회원가입 폼으로 회원가입을 할 수 있다
- [x] 가입 버튼을 클릭하면 사용자 입력 값이 서버에 전달된다
- [x] HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다
- [x] SoftAssertion으로 회원가입 컨트롤러 테스트
- [x] 회원가입 성공 시 메인 페이지로 리다이렉트

### [Step-3] 다양한 컨텐츠타입 지원
- [x] html, css, js, ico, png, jpg 타입을 지원한다

### [Step-4] POST로 회원가입
- [x] 로그인을 GET에서 POST로 동작하도록 변경한다
- [x] 가입을 완료하면 /index.html 페이지로 이동한다

---

## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.
