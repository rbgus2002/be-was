# java-was-2023

Java Web Application Server 2023

## 프로젝트 정보

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was
를 참고하여 작성되었습니다.
---

# Summary

## Requirements
#### Web Server step-1
- [ ] 클라이언트의 요청에 맞게 'src/main/resources/templates' 디렉토리의 파일을 읽어 응답한다
- [x] 서버로 들어오는 요청을 log.debug를 이용해 출력한다
- 유지보수에 좋은 구조에 대해 고민하고 코드 개선하기
  - [x] http 헤더 구조 객체 생성 (HttpHeader)
- [x] Java Thread 기반에서 Concurrent 패키지 사용하도록 수정
- [ ] Github 위키에 학습 내용 기록

## To Study List
#### Web Server step-1
- 단순 구현이 아닌 동작 원리 파악하기
  - [x] 자바 스레드 모델 
    - [x] 버전별 변경점
    - [ ] 향후 지향점
  - [x] Concurrent 패키지 학습
