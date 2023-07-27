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
        defaultInstanceManager = new DefaultInstanceManager();
    }

    @Test
    @DisplayName("중복 인스턴스 생성")
    void addInstance() throws NoSuchFieldException, IllegalAccessException {
        //given
        defaultInstanceManager.addInstance("UserController", new UserController());

        //when
        defaultInstanceManager.addInstance("UserController", new UserController());

        //then
        Field instancesField = DefaultInstanceManager.class.getDeclaredField("singletonInstances");
        instancesField.setAccessible(true);
        Map<?, ?> instances = (Map<?, ?>) instancesField.get(defaultInstanceManager);
        assertThat(instances.size()).isEqualTo(1);
    }

}