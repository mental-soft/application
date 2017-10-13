package com.teammental.application.service;

import com.teammental.application.dto.MenuDto;
import com.teammental.application.exception.CustomException;

import java.util.List;

/**
 * Menu Servis Arayüz Sınıfı.
 */

public interface MenuService {

  List<MenuDto> getAll() throws CustomException;

  List<MenuDto> getByApplicationId(Integer applicationId) throws CustomException;

  Integer saveOrUpdate(MenuDto menuDto) throws CustomException;

  MenuDto getOne(Integer id) throws CustomException;

  void delete(Integer id) throws CustomException;

}
