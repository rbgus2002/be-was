# java-was-2023

Java Web Application Server 2023

## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

## Thread에 대한 공부
- [참고 링크(Github Wiki)](https://github.com/kwYoohae/be-was/wiki/Thread) 

## 구현 목록
### Step-1 
- [X] Thread를 고수준 API로 변환
- [X] HTTP Request, Response 파싱
- [X] HTTP Response 응답하기 작성
### Step-2
- [ ] Response, Request 리팩터링
  - [ ] 정적 파일을 내려주는 API와 그냥 API를 파싱하는 기능 구현
    - [ ] 둘을 따로 관리하도록 변경
  - [ ] 폼형태로가 아닌 어느정도의 파라미터를 내가 정할 수 있도록 변경
- [ ] 회원가입 기능 구현하기
- [ ] 지금까지의 `public` 메서드들에 대한 테스트 코드 작성