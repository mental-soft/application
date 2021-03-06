package com.teammental.application;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Qa ve Prod ortamı için test sınıfı.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles(value = {"qa", "prod"})
public class ApplicationQaAndProdTest {

  @Value(value = "${liquibase.drop-first}")
  private String isDropFirst;

  /**
   * Liquibase qa ortamında drop yapmaması için test ediliyor.
   * @throws Exception exception
   */

  @Test
  public void checkDropFirst() throws Exception {
    if (!isDropFirst.equals("${liquibase.drop-first}")) {
      assertFalse(Boolean.getBoolean(isDropFirst));
    }
  }

}
