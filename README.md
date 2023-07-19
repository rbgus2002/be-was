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
- [X] Response, Request 리팩터링
- [X] 회원가입 기능 구현하기
- [X] 지금까지의 `public` 메서드들에 대한 테스트 코드 작성
### Step-3
- [X] HttpWasResponse 클래스가 하드코딩으로 하지 않고 여러개의 값을 조합해서 Response를 하도록 리팩토링
- [X] Mime 타입 지원하도록 관리하는 핸들러나 로직 구현
- [X] 상수로 포장할 수 있는 값들 변경
- [X] 단위 테스트 작성