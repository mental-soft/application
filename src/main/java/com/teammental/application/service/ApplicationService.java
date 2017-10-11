package com.teammental.application.service;

import com.teammental.application.dto.ApplicationDto;
import com.teammental.application.dto.IdNameDto;
import com.teammental.application.exception.CustomException;

import java.util.List;

/**
 * Application Servis Arayüz Sınıfı.
 */

public interface ApplicationService {

  List<ApplicationDto> getAll() throws CustomException;

  List<IdNameDto> getAllIdName() throws CustomException;

  Integer save(ApplicationDto applicationDto) throws CustomException;

  ApplicationDto getOne(Integer applicationId) throws CustomException;

  ApplicationDto update(ApplicationDto applicationDto) throws CustomException;

  void delete(Integer applicationId) throws CustomException;

}
