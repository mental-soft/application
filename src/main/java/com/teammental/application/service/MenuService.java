package com.teammental.application.service;

import com.teammental.application.dto.MenuDto;
import com.teammental.application.exception.CustomException;

import java.util.List;

/**
 * Menu Servis Arayüz Sınıfı.
 */

public interface MenuService {

  List<MenuDto> findAll() throws CustomException;

  List<MenuDto> findByApplicationId(Integer applicationId) throws CustomException;

  Integer saveOrUpdate(MenuDto menuDto) throws CustomException;

  MenuDto findById(Integer id) throws CustomException;

  Boolean delete(Integer id) throws CustomException;

}
