# 기능 목록 및 학습 정리

## 기능 목록
- [X] MIME 타입 관리하는 Enum 클래스 생성
- [X] Path에 대해서도 static 경로와 templates 경로를 관리하는 Enum 클래스 생성
- [ ] 정적 파일 요청 시, 바로 리턴

## 학습 정리
- 스프링은 정적 파일 요청시 디스페처 서블릿까지 가지 않고 톰캣에서 처리한다..
- GetMapping, PostMapping보다 RequestMapping을 사용하면 더욱 관리하기 용이해진다.

## 고민
- queryparameter를 일급 클래스로 관리하는게 더 좋을까?
- 프로그램을 실행하면서 HttpRequest의 URL 부분을 디코딩하지 않았음을 깨달았다
- 왜 크롬에서는 파비콘이 뜨지 않을까? 사파리에서는 잘 떴는데, 이유를 모르겠다.