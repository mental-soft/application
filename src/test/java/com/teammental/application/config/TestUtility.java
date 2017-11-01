package com.teammental.application.config;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test işlemleri için gerekli yardımcı sınıf.
 */
public class TestUtility {

  /**
   * Nesneleri json'a dönüştürür.
   *
   * @param object dönüştürülecek nesne.
   * @return nesnenin json sonucu.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  public static String objectToJson(Object object) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(object);
  }

}
