package com.teammental.application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Deneme Sınıfı
 */

@Controller
public class HelloController {

  /**
   * Örnek index sayfasını getiren metod
   * @return index.html
   */

  @RequestMapping({"/", "/index"})
  public String getIndex() {
    return "index";
  }

}

