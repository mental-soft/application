package com.teammental.application.controller;

import com.teammental.application.dto.ApplicationDto;
import com.teammental.application.dto.IdNameDto;
import com.teammental.application.exception.CustomException;
import com.teammental.application.service.ApplicationService;

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
import org.springframework.web.bind.annotation.RestController;

/**
 * Application Controller Sınıfı.
 */

@RestController
public class ApplicationController {

  public static final String MAP_APPLICATION = "/applications";
  public static final String MAP_APPLICATION_IDNAME = "/applications/idname";
  public static final String MAP_APPLICATION_DETAIL = "/applications/{id}";
  private static final String MAP_APPLICATION_JSON = "application/json";

  @Autowired
  private ApplicationService applicationService;

  /**
   * Bütün uygulamaları görüntüler.
   * @return Hatayı veya sonucu json olarak döndürür.
   */
  @GetMapping(value = MAP_APPLICATION, produces = MAP_APPLICATION_JSON)
  public ResponseEntity getApplications() {
    try {
      List<ApplicationDto> list = applicationService.findAll();

      return ResponseEntity.ok().body(list);

    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

  /**
   * Bütün uygulamaları id, name şeklinde görüntüler.
   * @return Hatayı veya sonucu json olarak döndürür.
   */
  @GetMapping(value = MAP_APPLICATION_IDNAME, produces = MAP_APPLICATION_JSON)
  public ResponseEntity getApplicationsIdName() {
    try {
      List<IdNameDto> list = applicationService.findAllIdName();

      return ResponseEntity.ok().body(list);

    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

  /**
   * Yeni bir uygulama ekler.
   * @param applicationDto eklenecek uygulamadır.
   * @return Hatayı veya sonucu json olarak döndürür.
   */
  @PostMapping(value = MAP_APPLICATION, produces = MAP_APPLICATION_JSON)
  public ResponseEntity createApplication(@RequestBody ApplicationDto applicationDto) {
    try {
      Integer id = applicationService.saveOrUpdate(applicationDto);

      return ResponseEntity.created(URI.create(MAP_APPLICATION + "/" + id)).build();

    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

  /**
   * Id parametresine göre uygulamayı görüntüler.
   * @param id görüntülenmek istenen uygulama id bilgisi.
   * @return Hatayı veya sonucu json olarak döndürür.
   */
  @GetMapping(value = MAP_APPLICATION_DETAIL, produces = MAP_APPLICATION_JSON)
  public ResponseEntity getApplicationById(@PathVariable Integer id) {
    try {
      ApplicationDto applicationDto = applicationService.findById(id);

      return ResponseEntity.ok().body(applicationDto);

    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

  /**
   * Bir uygulamayı günceller.
   * @param applicationDto güncellenecek uygulamadır.
   * @return Hatayı veya sonucu json olarak döndürür.
   */
  @PutMapping(value = MAP_APPLICATION_DETAIL, produces = MAP_APPLICATION_JSON)
  public ResponseEntity updteApplication(@RequestBody ApplicationDto applicationDto) {
    try {
      applicationService.saveOrUpdate(applicationDto);

      return ResponseEntity.ok().body(applicationDto);

    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

  /**
   * Bir uygulamayı siler.
   * @param id silinecek uygulamaya ait id bilgisi.
   * @return Hatayı veya sonucu json olarak döndürür.
   */
  @DeleteMapping(value = MAP_APPLICATION_DETAIL, produces = MAP_APPLICATION_JSON)
  public ResponseEntity deleteApplication(@PathVariable Integer id) {
    try {
      applicationService.delete(id);

      return ResponseEntity.noContent().build();

    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

}
