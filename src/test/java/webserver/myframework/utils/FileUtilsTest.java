package webserver.myframework.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;


@DisplayName("FileUtils 테스트")
class FileUtilsTest {
    @Nested
    @DisplayName("getExtension method")
    class GetExtension {
        @Nested
        @DisplayName("확장자가 존재할 경우")
        class IsExtensionExist {
            @Test
            @DisplayName("파일의 확장자를 반환한다")
            void returnExtensionOfFile() {
                //given
                File htmlFile = new File(".test.html");

                //when
                String htmlExtension = FileUtils.getExtension(htmlFile);

                //then
                assertThat(htmlExtension).isEqualTo("html");
            }
        }

        @Nested
        @DisplayName("확장자가 존재하지 않은경우")
        class IsExtensionNotExist {
            @Test
            @DisplayName("빈 문자열을 반환한다")
            void returnEmptyString() {
                //given
                File notExtensionFile = new File("test");

                //when
                String emptyExtension = FileUtils.getExtension(notExtensionFile);

                //then
                assertThat(emptyExtension.isEmpty()).isTrue();
            }
        }
    }

    @Nested
    @DisplayName("getPackageDirectory method")
    class GetPackageDirectory {
        @Nested
        @DisplayName("패키지가 존재할 경우")
        class IsDirectoryExist {
            @Test
            @DisplayName("해당 패키지 디렉토리를 반환한다")
            void returnFilesInDirectory() throws FileNotFoundException {
                //given
                //when
                File dbPackage = FileUtils.getPackageDirectory(Objects.requireNonNull(
                        Thread.currentThread().getContextClassLoader().getResource("db")));

                //then
                assertThat(dbPackage.exists()).isTrue();
                assertThat(dbPackage.isDirectory()).isTrue();
                assertThat(dbPackage.getName()).isEqualTo("db");
            }
        }

        @Nested
        @DisplayName("디렉토리가 존재하지 않는 경우")
        class IsDirectoryNotExist {
            @Test
            @DisplayName("FileNotFoundException 예외를 던진다")
            void throwFileNotFoundException() {
                //given
                //when
                //then
                assertThatThrownBy(() -> FileUtils.getPackageDirectory(
                        new File("notExist").toURI().toURL()
                )).isInstanceOf(FileNotFoundException.class);
            }
        }
    }
}
