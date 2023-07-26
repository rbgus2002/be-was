package db;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static db.SessionStorage.*;

class SessionStorageTest {

    private SoftAssertions softAssertions;

    @BeforeEach
    void setup() {
        clearSessionIds();
        softAssertions = new SoftAssertions();
    }

    @AfterEach
    void after() {
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("세션 정보를 스토리지에 추가할 수 있어야 한다")
    void addSessionIdTest() {
        // Given
        String sessionId_1 = "4bc504ae-a64f-4fba-a3df-4466c012915a";
        String userId_1 = "kimahhh";

        // When
        addSessionId(sessionId_1, userId_1);

        // Then
        softAssertions.assertThat(findAllSessionIds().size())
                .as("세션 정보가 추가되지 않았습니다.\n현재 userDB size: %d", findAllSessionIds().size())
                .isEqualTo(1);

        // Given
        String sessionId_2 = "bde99a4a-0885-4c85-b4a3-7402ffff8892";
        String userId_2 = "gildong";

        // When
        addSessionId(sessionId_2, userId_2);

        // Then
        softAssertions.assertThat(findAllSessionIds().size())
                .as("세션 정보가 추가되지 않았습니다.\n현재 userDB size: %d", findAllSessionIds().size())
                .isEqualTo(2);
    }

    @Test
    @DisplayName("세션 정보를 스토리지에서 삭제할 수 있어야 한다")
    void deleteSessionIDTest() {
        // Given
        String sessionId_1 = "4bc504ae-a64f-4fba-a3df-4466c012915a";
        String userId_1 = "kimahhh";
        addSessionId(sessionId_1, userId_1);
        String sessionId_2 = "bde99a4a-0885-4c85-b4a3-7402ffff8892";
        String userId_2 = "gildong";
        addSessionId(sessionId_2, userId_2);

        // When
        deleteSessionId(sessionId_1);

        // Then
        softAssertions.assertThat(findAllSessionIds().size())
                .as("세션 정보가 삭제되지 않았습니다.\n현재 userDB size: %d", findAllSessionIds().size())
                .isEqualTo(1);

        // When
        deleteSessionId(sessionId_2);

        // Then
        softAssertions.assertThat(findAllSessionIds().size())
                .as("세션 정보가 삭제되지 않았습니다.\n현재 userDB size: %d", findAllSessionIds().size())
                .isEqualTo(0);
    }

    @Test
    @DisplayName("모든 세션 정보를 조회할 수 있어야 한다")
    void findAllTest() {
        // Given
        String sessionId_1 = "4bc504ae-a64f-4fba-a3df-4466c012915a";
        String userId_1 = "kimahhh";
        addSessionId(sessionId_1, userId_1);
        String sessionId_2 = "bde99a4a-0885-4c85-b4a3-7402ffff8892";
        String userId_2 = "gildong";
        addSessionId(sessionId_2, userId_2);
        String sessionId_3 = "f6ed9c26-f7eb-4e48-8c9c-4d5161f44d7e";
        String userId_3 = "juice";
        addSessionId(sessionId_3, userId_3);

        // When
        Collection<String> allSessionIds = findAllSessionIds();

        // Then
        softAssertions.assertThat(allSessionIds.contains(sessionId_1))
                .as("sessionId_1이 포함되어 있지 않습니다.\n현재 값: %s", allSessionIds.contains(sessionId_1))
                .isEqualTo(true);
        softAssertions.assertThat(allSessionIds.contains(sessionId_2))
                .as("sessionId_2가 포함되어 있지 않습니다.\n현재 값: %s", allSessionIds.contains(sessionId_2))
                .isEqualTo(true);
        softAssertions.assertThat(allSessionIds.contains(sessionId_3))
                .as("sessionId_3이 포함되어 있지 않습니다.\n현재 값: %s", allSessionIds.contains(sessionId_3))
                .isEqualTo(true);
    }

    @Test
    @DisplayName("세션 아이디로 유저 아이디를 조회할 수 있어야 한다")
    void findUserByIdTest() {
        // Given
        String sessionId_1 = "4bc504ae-a64f-4fba-a3df-4466c012915a";
        String userId_1 = "kimahhh";
        addSessionId(sessionId_1, userId_1);
        String sessionId_2 = "bde99a4a-0885-4c85-b4a3-7402ffff8892";
        String userId_2 = "gildong";
        addSessionId(sessionId_2, userId_2);

        // When
        String findUserId1 = findUserIdBySessionId(sessionId_1);
        String findUserId2 = findUserIdBySessionId(sessionId_2);

        // Then
        softAssertions.assertThat(findUserId1)
                .as("세션 아이디로 유저 아이디를 조회할 수 없습니다.\n현재 값: %s", findUserId1)
                .isEqualTo(userId_1);
        softAssertions.assertThat(findUserId2)
                .as("세션 아이디로 유저 아이디를 조회할 수 없습니다.\n현재 값: %s", findUserId2)
                .isEqualTo(userId_2);
    }

    @Test
    @DisplayName("세션 정보를 초기화할 수 있어야 한다")
    void clearTest() {
        // Given
        String sessionId_1 = "4bc504ae-a64f-4fba-a3df-4466c012915a";
        String userId_1 = "kimahhh";
        addSessionId(sessionId_1, userId_1);
        String sessionId_2 = "bde99a4a-0885-4c85-b4a3-7402ffff8892";
        String userId_2 = "gildong";
        addSessionId(sessionId_2, userId_2);
        String sessionId_3 = "f6ed9c26-f7eb-4e48-8c9c-4d5161f44d7e";
        String userId_3 = "juice";
        addSessionId(sessionId_3, userId_3);

        // When
        clearSessionIds();

        // Then
        softAssertions.assertThat(findAllSessionIds().size())
                .as("Session Storage가 초기화되지 않았습니다.\n현재 userDB size: %s", findAllSessionIds().size())
                .isEqualTo(0);
    }

}