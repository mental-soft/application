package com.teammental.application.controller;

import com.teammental.application.config.Map;
import com.teammental.application.dto.MenuDto;
import com.teammental.application.exception.CustomException;
import com.teammental.application.service.MenuService;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Menu Controller Sınıfı.
 */

@RestController
public class MenuController {
  @Autowired
  private MenuService menuService;

  /**
   * Bütün ya da uygulamaya göre işlemleri görüntüler.
   * @param applicationId işlemleri uygulamasına göre kısıtlar.
   * @return Hatayı veya sonucu json olarak döndürür.
   */
  @GetMapping(value = Map.MENU, produces = Map.JSON)
  public ResponseEntity getMenus(@RequestParam(required = false) Integer applicationId) {
    try {
      if (applicationId == null) {
        return ResponseEntity.ok().body(menuService.findAll());
      }

      return ResponseEntity.ok().body(menuService.findByApplicationId(applicationId));
    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

  /**
   * Bir işlem ekler.
   * @param menuDto eklenecek işlemdir.
   * @return Hatayı veya sonucu json olarak döndürür.
   */
  @PostMapping(value = Map.MENU, produces = Map.JSON)
  public ResponseEntity createMenu(@RequestBody MenuDto menuDto) {
    try {
      return ResponseEntity.created(URI.create(Map.MENU_URI
          + menuService.saveOrUpdate(menuDto))).build();
    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

  /**
   * Id parametresine göre işlemi görüntüler.
   * @param id görüntülenmek istenen işlem id bilgisi.
   * @return Hatayı veya sonucu json olarak döndürür.
   */
  @GetMapping(value = Map.MENU_DETAIL, produces = Map.JSON)
  public ResponseEntity getMenuById(@PathVariable Integer id) {
    try {
      return ResponseEntity.ok().body(menuService.findById(id));
    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

  /**
   * Bir işlemi günceller.
   * @param menuDto güncellenecek işlemdir.
   * @return Hatayı veya sonucu json olarak döndürür.
   */
  @PutMapping(value = Map.MENU_DETAIL, produces = Map.JSON)
  public ResponseEntity updateMenu(@RequestBody MenuDto menuDto) {
    try {
      return ResponseEntity.ok().body(menuService
          .findById(menuService.saveOrUpdate(menuDto)));
    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

  /**
   * Bir işlemi siler.
   * @param id silinecek işleme ait id bilgisi.
   * @return Hatayı veya sonucu json olarak döndürür.
   */
  @DeleteMapping(value = Map.MENU_DETAIL, produces = Map.JSON)
  public ResponseEntity deleteMenu(@PathVariable Integer id) {
    try {
      menuService.delete(id);
      return ResponseEntity.noContent().build();
    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

}
