package com.teammental.application.service;

import com.teammental.application.dto.MenuDto;
import com.teammental.application.entity.Menu;
import com.teammental.application.exception.CustomException;
import com.teammental.application.jpa.MenuRepository;
import com.teammental.memapper.MeMapper;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Menu Servis Sınıfı.
 */

@Service
@Transactional
public class MenuServiceImp implements MenuService {

  @Autowired
  private MenuRepository menuRepository;

  /**
   * Bütün menu bilgilerine ulaşır.
   * @return MenuDto tipinde liste döner.
   * @throws CustomException Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public List<MenuDto> getAll() throws CustomException {
    Optional<List<MenuDto>> optional = MeMapper.from(menuRepository.findAll())
        .toOptional(MenuDto.class);

    if (optional.isPresent() && optional.get().size() > 0) {
      return optional.get();
    }

    throw new CustomException(0, CustomException.menu_not_found);
  }

  /**
   * applicationId parametresine göre menu bilgisine ulaşır.
   * @param applicationId application bilgisine ait id parametresidir.
   * @return MenuDto tipinde menu bilgisi döner.
   * @throws CustomException Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public List<MenuDto> getByApplicationId(Integer applicationId) throws CustomException {
    Optional<List<MenuDto>> optional = MeMapper
        .from(menuRepository.findByApplicationId(applicationId))
        .toOptional(MenuDto.class);

    if (optional.isPresent() && optional.get().size() > 0) {
      return optional.get();
    }

    throw new CustomException(0, CustomException.menu_not_found);
  }

  /**
   * Menu tablosune bilgi kayıt eder.
   * @param menuDto kayıt edilecek bilgidir.
   * @return Kayıt edilen menu id integer tipinde döner.
   * @throws CustomException Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public Integer save(MenuDto menuDto) throws CustomException {
    if (StringUtils.isEmpty(menuDto.getApplicationId())) {
      throw new CustomException(1, CustomException.menu_app_required);
    }

    if (StringUtils.isEmpty(menuDto.getRelativeUrl())) {
      throw new CustomException(2, CustomException.menu_url_required);
    }

    if (StringUtils.isEmpty(menuDto.getName())) {
      throw new CustomException(3, CustomException.menu_name_required);
    }

    if (StringUtils.isEmpty(menuDto.getOrder())) {
      throw new CustomException(4, CustomException.menu_order_required);
    }

    if (StringUtils.isEmpty(menuDto.getDescription())) {
      throw new CustomException(5, CustomException.menu_description_required);
    }

    if (menuRepository.findByRelativeUrl(menuDto.getRelativeUrl()) != null) {
      throw new CustomException(6, CustomException.menu_same_url);
    }

    Optional<Menu> optional = MeMapper.from(menuDto)
        .toOptional(Menu.class);

    if (optional.isPresent()) {
      Menu menu = menuRepository.save(optional.get());
      return menu.getId();
    }

    throw new CustomException(0, CustomException.menu_save_error);
  }

  /**
   * Id parametresine göre menu bilgisine ulaşır.
   * @param menuId menu bilgisine ait id parametresidir.
   * @return MenuDto tipinde application bilgisi döner.
   * @throws CustomException Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public MenuDto getOne(Integer menuId) throws CustomException {
    Optional<MenuDto> optional = MeMapper.from(menuRepository.getOne(menuId))
        .toOptional(MenuDto.class);

    if (optional.isPresent()) {
      return optional.get();
    }

    throw new CustomException(0, CustomException.menu_not_found);
  }

  /**
   * Menu tablosundan bilgi günceller.
   * @param menuDto güncellenecek bilgidir.
   * @return Güncellenen menu id integer tipinde döner.
   * @throws CustomException Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public MenuDto update(MenuDto menuDto) throws CustomException {
    Optional<Menu> optional = MeMapper.from(menuDto)
        .toOptional(Menu.class);

    if (optional.isPresent()) {
      menuRepository.save(optional.get());
      return menuDto;
    }

    throw new CustomException(0, CustomException.menu_save_error);
  }

  /**
   * Application tablosundan bilgi siler.
   * @param menuId menu bilgisine ait id parametresidir.
   * @throws CustomException Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public void delete(Integer menuId) throws CustomException {
    Menu menu = menuRepository.getOne(menuId);

    if (menu == null) {
      throw new CustomException(0, CustomException.menu_not_found);
    }

    menuRepository.delete(menu);
  }

}
