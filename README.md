# java-was-2023

Java Web Application Server 2023

## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

### 프로젝트 원리
  - 사용한 기술에 대한 정리는 [WAS-Wiki](https://github.com/ijehyunpark/be-was/wiki/Web-Application-Server-Wiki)에 있습니다.
  - WAS는 다음과 같이 동작합니다.
    1. 클라이언트로부터 요청이 수신되었을 경우 새로운 스레드를 할당하여 해당 요청을 RequestHandler에게 전달합니다. 
    2. RequestHandler는 HttpReqeust와 HttpResponse를 생성한 후 HttpMethod에 따라 적합한 메소드를 호출합니다.
    3. RequestUrl에 따라 이를 처리할 수 있는 Controller가 존재할 경우 처리를 해당 Controller에게 위임하고 없는 경우 단순 페이지 로딩을 시도합니다.
    4. 상황에 따른 Response Message를 생성하고 이를 클라이언트에게 전달합니다.

## 프로젝트 구조

### webserver package
WAS 구동 시 필요한 기능들이 구현되어 있습니다.

  - WebServer

    사용자의 요청을 받아 서버 소켓을 생성하고 연결합니다. 사용자의 요청이 올 때 마다 새로운 스레드를 스레드 풀에서 가져와 병렬적으로 처리합니다.
    - Header
      - Reqeust & Response 헤더를 전담하는 객체입니다.
    - WebPageReader
      - 다양한 페이지를 사용자에게 전달하기 위해 서버의 파일을 읽습니다. 해당 객체에 특정 경로를 추가함으로써 해당 경로내의 파일을 탐색하여 사용자에게 페이지를 전달합니다.
      - 기본적으로 resources/static, resource/template 내의 파일을 지원합니다.
    - RequestHandler
      - 사용자의 요청이 들어왔을 때 요청을 해석하고(HttpRequest) 이에 맞춰 적합한 처리를 진행 한 후 HttpResponse를 작성하고 사용자에게 전달합니다.
  - support

    서버의 다양한 기능을 지원하기 위한 클래스가 정의되어 있습니다.
    - InstanceManager
       - 서버에서 처리하는 메소드를 가진 인스턴스를 검색하고 보관합니다.
    - ControllerResolver
      - 서버에서 사용하는 모든 Controller와 내부 메소드를 찾고 정의된 url이 호출될 경우 이를 처리합니다.