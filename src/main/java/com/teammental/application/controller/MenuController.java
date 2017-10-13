package com.teammental.application.controller;

import com.teammental.application.dto.MenuDto;
import com.teammental.application.exception.CustomException;
import com.teammental.application.service.MenuService;

import java.net.URI;
import java.util.List;

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

  private static final String MAP_MENU = "/operations";
  private static final String MAP_MENU_JSON = "application/json";
  private static final String MAP_MENU_APP = "/operations?applicationId={applcationId}";
  private static final String MAP_MENU_DETAIL = "/operations/{id}";

  @Autowired
  private MenuService menuService;

  /**
   * Bütün işlemleri görüntüler.
   * @return Hatayı veya sonucu json olarak döndürür
   */
  @GetMapping(value = MAP_MENU, produces = MAP_MENU_JSON)
  public ResponseEntity getMenus() {
    try {
      List<MenuDto> list = menuService.getAll();

      return ResponseEntity.ok().body(list);

    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

  /**
   * Bir uygulamaya ait bütün işlemleri görüntüler.
   * @param applicationId işlemlere ait uygulama id bilgisi.
   * @return Hatayı veya sonucu json olarak döndürür
   */
  @GetMapping(value = MAP_MENU_APP, produces = MAP_MENU_JSON)
  public ResponseEntity getMenusByApplicationId(@RequestParam Integer applicationId) {
    try {
      List<MenuDto> list = menuService.getByApplicationId(applicationId);

      return ResponseEntity.ok().body(list);

    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

  /**
   * Bir işlem ekler.
   * @param menuDto eklenecek işlemdir.
   * @return Hatayı veya sonucu json olarak döndürür
   */
  @PostMapping(value = MAP_MENU, produces = MAP_MENU_JSON)
  public ResponseEntity createMenu(@RequestBody MenuDto menuDto) {
    try {
      Integer id = menuService.saveOrUpdate(menuDto);

      return ResponseEntity.created(URI.create(MAP_MENU + "/" + id)).build();

    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

  /**
   * Id parametresine göre işlemi görüntüler.
   * @param id görüntülenmek istenen işlem id bilgisi.
   * @return Hatayı veya sonucu json olarak döndürür
   */
  @GetMapping(value = MAP_MENU_DETAIL, produces = MAP_MENU_JSON)
  public ResponseEntity getMenuById(@PathVariable Integer id) {
    try {
      MenuDto menuDto = menuService.getOne(id);

      return ResponseEntity.ok().body(menuDto);

    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

  /**
   * Bir işlemi günceller.
   * @param menuDto güncellenecek işlemdir.
   * @return Hatayı veya sonucu json olarak döndürür
   */
  @PutMapping(value = MAP_MENU_DETAIL, produces = MAP_MENU_JSON)
  public ResponseEntity updateMenu(@RequestBody MenuDto menuDto) {
    try {
      menuService.saveOrUpdate(menuDto);

      return ResponseEntity.ok().body(menuDto);

    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

  /**
   * Bir işlemi siler.
   * @param id silinecek işleme ait id bilgisi.
   * @return Hatayı veya sonucu json olarak döndürür
   */
  @DeleteMapping(value = MAP_MENU_DETAIL, produces = MAP_MENU_JSON)
  public ResponseEntity deleteMenu(@PathVariable Integer id) {
    try {
      menuService.delete(id);

      return ResponseEntity.noContent().build();

    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

}