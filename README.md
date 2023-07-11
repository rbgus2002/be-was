# java-was-2023

Java Web Application Server 2023

## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

## 프로젝트 구조

### webserver package
WAS 구동 시 필요한 기능들이 구현되어 있습니다.

  - WebServer
    
    사용자의 요청을 받아 서버 소켓을 생성하고 연결합니다. 사용자의 요청이 올 때 마다 새로운 스레드를 스레드 풀에서 가져와 병렬적으로 처리합니다.
    - RequestHeader
      - 요청헤더를 전담하는 객체입니다.
    - WebPageReader
      - 다양한 페이지를 사용자에게 전달하기 위해 서버의 파일을 읽습니다. 해당 객체에 특정 경로를 추가함으로써 해당 경로내의 파일을 탐색하여 사용자에게 페이지를 전달합니다.
      - 기본적으로 resources/static, resource/template 내의 파일을 지원합니다.
    - RequestHandler
      - 사용자의 요청이 들어왔을 때 요청을 해석하여 이에 맞춰 ResponseHeader를 작성하고 해당 페이지를 불러와 사용자에게 전달합니다.
