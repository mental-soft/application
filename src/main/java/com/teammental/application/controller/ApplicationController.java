package com.teammental.application.controller;

import com.teammental.application.config.Map;
import com.teammental.application.dto.ApplicationDto;
import com.teammental.application.exception.CustomException;
import com.teammental.application.service.ApplicationService;

import java.net.URI;

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
  @Autowired
  private ApplicationService applicationService;

  /**
   * Bütün uygulamaları görüntüler.
   * @return Hatayı veya sonucu json olarak döndürür.
   */
  @GetMapping(value = Map.APPLICATION, produces = Map.JSON)
  public ResponseEntity getApplications() {
    try {
      return ResponseEntity.ok().body(applicationService.findAll());
    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

  /**
   * Bütün uygulamaları id, name şeklinde görüntüler.
   * @return Hatayı veya sonucu json olarak döndürür.
   */
  @GetMapping(value = Map.APPLICATION_IDNAME, produces = Map.JSON)
  public ResponseEntity getApplicationsIdName() {
    try {
      return ResponseEntity.ok().body(applicationService.findAllIdName());
    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

  /**
   * Yeni bir uygulama ekler.
   * @param applicationDto eklenecek uygulamadır.
   * @return Hatayı veya sonucu json olarak döndürür.
   */
  @PostMapping(value = Map.APPLICATION, produces = Map.JSON)
  public ResponseEntity createApplication(@RequestBody ApplicationDto applicationDto) {
    try {
      return ResponseEntity.created(URI.create(Map.APPLICATION_URI
          + applicationService.saveOrUpdate(applicationDto))).build();
    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

  /**
   * Id parametresine göre uygulamayı görüntüler.
   * @param id görüntülenmek istenen uygulama id bilgisi.
   * @return Hatayı veya sonucu json olarak döndürür.
   */
  @GetMapping(value = Map.APPLICATION_DETAIL, produces = Map.JSON)
  public ResponseEntity getApplicationById(@PathVariable Integer id) {
    try {
      return ResponseEntity.ok().body(applicationService.findById(id));
    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

  /**
   * Bir uygulamayı günceller.
   * @param applicationDto güncellenecek uygulamadır.
   * @return Hatayı veya sonucu json olarak döndürür.
   */
  @PutMapping(value = Map.APPLICATION_DETAIL, produces = Map.JSON)
  public ResponseEntity updteApplication(@RequestBody ApplicationDto applicationDto) {
    try {
      return ResponseEntity.ok().body(applicationService
          .findById(applicationService.saveOrUpdate(applicationDto)));
    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

  /**
   * Bir uygulamayı siler.
   * @param id silinecek uygulamaya ait id bilgisi.
   * @return Hatayı veya sonucu json olarak döndürür.
   */
  @DeleteMapping(value = Map.APPLICATION_DETAIL, produces = Map.JSON)
  public ResponseEntity deleteApplication(@PathVariable Integer id) {
    try {
      applicationService.delete(id);
      return ResponseEntity.noContent().build();
    } catch (CustomException e) {
      return ResponseEntity.status(e.getCode()).body(e.getLabel());
    }
  }

}
