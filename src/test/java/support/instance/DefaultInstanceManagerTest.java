package support.instance;


import controller.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultInstanceManagerTest {

    DefaultInstanceManager defaultInstanceManager;

    @BeforeEach
    void setUp() {
        defaultInstanceManager = DefaultInstanceManager.getInstanceManager();
        defaultInstanceManager.clear();
    }

    @Test
    @DisplayName("중복 인스턴스 생성")
    void addInstance() throws NoSuchFieldException, IllegalAccessException {
        //given
        defaultInstanceManager.addInstance(UserController.class);

        //when
        defaultInstanceManager.addInstance(UserController.class);

        //then
        Field instancesField = DefaultInstanceManager.class.getDeclaredField("instances");
        instancesField.setAccessible(true);
        Map instances = (Map) instancesField.get(defaultInstanceManager);
        assertThat(instances.size()).isEqualTo(1);
    }

}