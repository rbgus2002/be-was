package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class WebServerTest {


    private static final Logger logger = LoggerFactory.getLogger(WebServerTest.class);

    @Test
    @DisplayName("여러 스레드가 동시 접근할 때 데이터가 유지되는지?")
    void multi_thread() {
        logger.info("동시성 테스트 시작");

        // given
        int numberOfThreads = 2;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);


        // when

        // then
    }
}