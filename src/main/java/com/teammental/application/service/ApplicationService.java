package com.teammental.application.service;

import com.teammental.application.dto.ApplicationDto;
import com.teammental.application.dto.IdNameDto;
import com.teammental.application.exception.CustomException;

import java.util.List;

/**
 * Application Servis Arayüz Sınıfı.
 */

public interface ApplicationService {

  List<ApplicationDto> findAll() throws CustomException;

  List<IdNameDto> findAllIdName() throws CustomException;

  Integer saveOrUpdate(ApplicationDto applicationDto) throws CustomException;

  ApplicationDto findById(Integer id) throws CustomException;

  Boolean delete(Integer id) throws CustomException;

}
