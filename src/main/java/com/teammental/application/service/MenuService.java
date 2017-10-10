package com.teammental.application.service;

import com.teammental.application.dto.MenuDto;

import java.util.List;

/**
 * Menu Servis Arayüz Sınıfı.
 */

public interface MenuService {

  List<MenuDto> getAll() throws Exception;

  List<MenuDto> getByApplicationId(Integer applicationId) throws Exception;

  Integer save(MenuDto menuDto) throws Exception;

  MenuDto getOne(Integer menuId) throws Exception;

  MenuDto update(MenuDto menuDto) throws Exception;

  void delete(Integer menuId) throws Exception;

}
