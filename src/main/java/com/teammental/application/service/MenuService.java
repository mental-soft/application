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

  Integer save(MenuDto menuDto) throws CustomException;

  MenuDto getOne(Integer menuId) throws CustomException;

  MenuDto update(MenuDto menuDto) throws CustomException;

  void delete(Integer menuId) throws CustomException;

}
