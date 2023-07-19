package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.request.Uri;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static utils.StringUtils.appendNewLine;

class HandlerMappingTest {
    private final String GET = "GET";
    private final String PATH = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
    private final Uri URI = Uri.from(PATH);
    private String requestStr;
    private InputStream in;

    @BeforeEach
    void setRequestStr() {
        requestStr = GET + " " + URI + " " + appendNewLine("HTTP/1.1") +
                appendNewLine("Host: localhost:8080") +
                appendNewLine("Connection: keep-alive") +
                appendNewLine("Cache-Control: max-age=0") +
                "";
        in = new ByteArrayInputStream(requestStr.getBytes());
    }

    @Test
    @DisplayName("GetMapping 어노테이션이 붙은 메소드를 실행한다")
    void doDispatch() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // given, when
        HttpRequest request = HttpRequest.from(in);

        // then
        HandlerMapping.getMethodMapped(request);
    }

    @Test
    @DisplayName("Controller의 메소드를 미리 Map에 할당해둔다")
    void confirmStaticMap() throws IOException {
        // given
        HttpRequest request = HttpRequest.from(in);

        // when
        Method method = HandlerMapping.getMethodMapped(request);

        // then
        assertEquals("createUser", method.getName());
    }


    class People {
        public People(int money) {
            this.money = money;
        }

        private int money;
    }

    @Test
    @DisplayName("test")
    void test() throws IllegalAccessException {


        Class<People> clazz = People.class;
        Field[] fields = clazz.getDeclaredFields();

        People people = new People(1000);

        fields[0].setAccessible(true);
        System.out.println(fields[0]);
    }
}




/*


## 답안은 여기에다 작성

## 기본

### 1. autoboxing이란 무엇인가요?
자바에서는 기본 primitive 타입에 대한 Wrapper class가 존재합니다. primitive 타입을 별도의 객체로 감쌌다고 해서 Wrapper class라 불리는데, primitive 타입에서 wrapper class로 감싸는 과정을 boxing, 반대의 과정을 unboxing 이라고 합니다. <br>
즉 boxing, unboxing을 자바 자체적으로 자동으로 지원하는데 이를 autoboxing이라고 합니다.

### 2. JVM의 메모리 구조에 대해 설명해 주세요.
stack 영역, heap 영역, method 영역 등이 존재합니다. <br>
stack 영역 => 메소드 호출에 대한 순서를 저장(콜스택), 지역 변수 저장 <br>
heap 영역 => new 키워드를 통해서 할당하는 객체들은 heap 영역에 들어갑니다. 참고로 자바는 배열을 생성해 객체를 초기화 하기만 해도 모두 heap 영역에 들어가는 등, 메모리를 효율적으로 사용한다고는 할 수 없습니다. heap 영역에 할당된 객체들은 자기 자신을 가리키는 포인터가 없을 때 GC에 의해 수거됩니다. <br>
method 영역 => 위에 설명한 부분 외에 여타 다른 것들은 대부분 method 영역에 들어가는 것으로 알고 있습니다.

### 3. 자바 스레드의 동작 방식을 설명해 주세요.
프로세스 내에서 여러 개의 스레드가 존재할 수 있습니다. 스레드란 실(thread)이라는 의미가 있는데 최소 작업 단위입니다.
커널의 스레드와 JVM이 생성하는 스레드가 1:1로 매칭이 되어 동작합니다.

### 4. protected와, default 접근한정자에 대해 설명해 주세요.
같은 패키지 안에서만 접근할 수 있다는 공통점을 가지고 있습니다.
차이점은 protected의 경우는 해당 클래스를 상속받으면 외부 패키지에서도 사용할 수 있게 되고 default는 그렇지 못합니다.
따라서 접근을 한정하는 범위가 작은순으로 나열하면, private => default => protected => public 이라고 할 수 있습니다.

## 람다, 스트림
### 5. 자바 8에서 추가된 '람다식(Lambda Expressions)'는 무엇이며 왜 사용하나요?
메소드가 하나만 존재하는 객체를 사용할 때, 별도로 선언을 하는 것이 아니라 바로 -> 키워드를 사용하여 메소드로써 동작하게 코드 작성이 가능합니다.

### 6. 함수형 인터페이스는 무엇인가요
자바스크립트 등 다른 언어에서는 함수를 일급 함수로 취급하는 경우가 있는데 자바는 그렇지 못합니다. 따라서 자바에서는 사전에 여러 함수형 인터페이스가 정의되어있습니다. 각 함수형 인터페이스에는 고유한 이름이 존재합니다.
함수형 인터페이스는 정확히 하나의 추상메소드가 존재합니다.

### 7. java.util.function에 있는 대표적인 함수형 인터페이스 4가지만 나열하고 간단히 설명해 주세요. <br>
  7-1. Consumer => 인자 한개에 return 값이 존재하지 않습니다. <br>
  7-2. Supplier => 인자가 존재하고 return 값도 존재합니다. (특별한 요구사항이 없습니다.) <br>
  7-3. Function => 인자 한개에 return 값이 존재합니다. <br>
  7-4. Predicate => boolean을 인자로 받아 타당한지 여부를 return 합니다. <br>


### 8. 스트림이란 무엇이며 어떤 장점과 단점이 있나요?
스트림은 loop을 돌 때 for 키워드를 사용하지 않고자 만들어졌습니다. stream 객체를 생성하고, 가공하고, 반환하는 3가지 구조로 나뉩니다. <br>
스트림을 사용하면서 가독성이 좋아지고, 또 병렬 처리가 가능하도록 코드 작성이 가능합니다. 하지만 일반적으로 병렬 처리를 하지 않는 경우에 일반적으로 for 보다 성능이 더 좋지 않습니다. 따라서 항상 스트림이 좋은 것은 아니므로 경우에 맞게 사용해야 합니다.



## 리플렉션, 어노테이션

### 9. 리플렉션이란 무엇인가요? 장단점은?
리플렉션은 클래스, 메소드, 필드 생성자의 정보를 런타임에 확인할 수 있는 기능입니다. java.lang.reflect 패키지 안에 있습니다. lang 패키지 안에 있기 때문에 사용하기 위해 별다른 패키지를 import 하지 않아도 됩니다. <br>
장점은 리플렉션의 정의와 같이 런타임에 실행 정보를 확인할 수 있습니다. 자바만의 특징으로 C언어 등에서는 확인이 불가능합니다.
단점으로는 리플렉션을 남용하면 부하가 생겨 성능의 지장을 줄 수 있습니다.


### 10. People 클래스에 private int money가 있고 getter()가 없습니다. 리플렉션을 통해 값을 읽을 수 있나요? 있다면 코드를 작성해 주세요.
값을 읽을 수 있습니다. 먼저 People class의 field 값을 setAccssible(true)를 통해 접근할 수 있게 만든 뒤에 원하는 값을 가져옵니다.

''' <br>
Class<People> clazz = People.class;
Field[] fields = clazz.getDeclaredFields();

People people = new People(1000);

fields[0].setAccessible(true); <br>
System.out.println(fields[0]); <br>
'''

### 11. 어노테이션이란 무엇입니까?
클래스, 메소드, 필드 등에 붙일 수 있으며 어노테이션이 붙은 부분에 정해진 동작을 수행합니다. 어노테이션을 통해 중복 코드를 줄일 수 있으므로 AOP 실현을 돕습니다.


### 12.자바의 기본 어노테이션 세가지에 대해 설명해 주세요.
  12-1. @Override : 해당 메소드가 상위 클래스의 메소드를 상속하는지 여부를 알려줍니다. <br>
  12-2. @Nullable : 해당 필드가 null 값을 갖을 수 있게 해줍니다. <br>
  12-3. @Deprecated : 더이상 시용이 권장되지 않는 코드임을 알려줍니다. <br>

### 13. 'Annotation'과 Reflection의 관계에 대해 설명해주세요.
각 어노테이션마다 어느 기간까지 살아있을 수 있는지 스스로 결정합니다. 컴파일타임, 런타임 동안에도 살아있을 수 있는 어노테이션인 경우에 리플렉이션이 사용됩니다.

 */
