package com.example.login_test;

import org.hibernate.boot.TempTableDdlTransactionHandling;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

// ** 테스트 클래스 라는것을 명시.
@Sql("classpath:db/dataset.sql")
@SpringBootTest
class LoginTestApplicationTests {

    // TDD
    // BDD
    // DDD

    @Test
    void contextLoads() {
        // given(준비): 어떠한 데이터가 준비되었을 때

        // when(실행): 어떠한 함수를 실행하면

        // then(검증): 어떠한 결과가 나와야 한다.
    }
}
