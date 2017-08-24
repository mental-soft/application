package com.teammental.application;

import static org.junit.Assert.assertFalse;

import liquibase.integration.spring.SpringLiquibase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Qa ortamı için test sınıfı
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("qa")
public class ApplicationQaTest {

  @Autowired
  SpringLiquibase liquibase;

  /**
   * Liquibase qa ortamında drop yapmaması için test ediliyor.
   * @throws Exception exception
   */

  @Test
  public void checkDropFirst() throws Exception {
    assertFalse(liquibase.isDropFirst());
  }
}
