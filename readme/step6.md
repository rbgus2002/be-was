# 기능 목록 및 학습 정리

## 기능 목록

- [X] Session Class로 리팩토링
- [X] 사용자가 로그인 상태 유지
- [X] view html 생성
- [X] 사용자 목록 출력
- [X] 테스트 커버리지 80% 이상 달성

## 학습 정리

- Throwable과 throws Exception의 차이점은?
Throwable은 Java에서 모든 예외와 오류의 최상위 클래스이다. throws Exception은 메서드 선언부에 사용되는 예약어로, 해당 메서드가 해당 예외를 호출한 측으로 던지는 것을 나타낸다.


## 고민

- View를 어떤식으로 보여주는게 맞을끼? 이걸 String Builder로 하드 코딩하는게 맞나...?
- Session 클래스 생성: 어떤 필드로 정하지?
- 인코딩의 문제