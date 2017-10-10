package com.teammental.application.service;

import com.teammental.application.dto.ApplicationDto;
import com.teammental.application.dto.IdNameDto;

import java.util.List;

/**
 * Application Servis Arayüz Sınıfı.
 */

public interface ApplicationService {

  List<ApplicationDto> getAll() throws Exception;

  List<IdNameDto> getAllIdName() throws Exception;

  Integer save(ApplicationDto applicationDto) throws Exception;

  ApplicationDto getOne(Integer applicationId) throws Exception;

  ApplicationDto update(ApplicationDto applicationDto) throws Exception;

  void delete(Integer applicationId) throws Exception;

}
