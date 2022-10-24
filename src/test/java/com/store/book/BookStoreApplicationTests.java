package com.store.book;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * @author Mohammad.Asim.Khan
 *
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class BookStoreApplicationTests {

  @Test
  void contextLoads() {
  }

}
