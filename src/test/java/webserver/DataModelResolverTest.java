package webserver;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.DataModelResolver;
import support.DataModelWrapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("DataModel Resolver 테스트")
class DataModelResolverTest {

    @Test
    @DisplayName("경로가 입력됬을 경우 해당 경로에 맞는 Class를 반환해야 한다.")
    void resolve() throws ClassNotFoundException {
        //given
        String path = "/user/create";

        //when
        DataModelWrapper resolve = DataModelResolver.resolve(path);

        //then
        assertNotNull(resolve);
        assertTrue(resolve.equalsClass(User.class));
    }

}
