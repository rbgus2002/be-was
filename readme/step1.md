# 기능 목록 및 학습 정리

## 기능 목록
- [X] `index.html` 파일 반환
- [X] java thread 모델에 대한 학습
- [X] Git Wiki에 내용 정리
- [X] 요청에 대한 헤더 출력
- [X] Concurrent 패키지 적용

## 학습 정리
- InputStreamReader는 바이트 기반의 InputStream을 문자 기반으로 변환하는 역할을 한다. 
- in이라는 InputStream을 InputStreamReader로 감싸서 문자열을 읽을 수 있는 상태로 만든다.
- BufferedReader는 InputStreamReader로부터 읽은 문자열을 버퍼링하여 효율적으로 처리하는 역할을 한다.
- 버퍼링은 입출력 성능을 향상시키기 위한 기법으로, 한 번에 여러 문자를 읽고 처리한다.
- bufferedReader.readLine(): BufferedReader로부터 한 줄씩 문자열을 읽어온다.

## 고민
- 쓰레드 풀의 크기 고민이다. 어느정도가 적당할까?
- 데몬 쓰레드가 꺼지면, 다른 쓰레드도 꺼질까?
- RequestHandler와 Webserver는 어떻게 테스트하지?
- 브라우저로 실행 시, request가 두 개인 이유는?
- log에서 info와 debug의 차이