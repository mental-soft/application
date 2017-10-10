package com.teammental.application.service;

import com.teammental.application.dto.MenuDto;
import com.teammental.application.entity.Menu;
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
   * @throws Exception Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public List<MenuDto> getAll() throws Exception {
    List<Menu> menuList = menuRepository.findAll();

    Optional<List<MenuDto>> optional = MeMapper.from(menuList)
        .toOptional(MenuDto.class);

    if (optional.isPresent() && optional.get().size() > 0) {
      return optional.get();
    }

    throw new Exception();
  }

  /**
   * applicationId parametresine göre menu bilgisine ulaşır.
   * @param applicationId application bilgisine ait id parametresidir.
   * @return MenuDto tipinde menu bilgisi döner.
   * @throws Exception Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public List<MenuDto> getByApplicationId(Integer applicationId) throws Exception {
    List<Menu> menuList = menuRepository.findByApplicationId(applicationId);

    Optional<List<MenuDto>> optional = MeMapper.from(menuList)
        .toOptional(MenuDto.class);

    if (optional.isPresent() && optional.get().size() > 0) {
      return optional.get();
    }

    throw new Exception();
  }

  /**
   * Menu tablosune bilgi kayıt eder.
   * @param menuDto kayıt edilecek bilgidir.
   * @return Kayıt edilen menu id integer tipinde döner.
   * @throws Exception Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public Integer save(MenuDto menuDto) throws Exception {
    if (StringUtils.isEmpty(menuDto.getApplicationId())) {
      throw new Exception();
    }

    if (StringUtils.isEmpty(menuDto.getRelativeUrl())) {
      throw new Exception();
    }

    if (StringUtils.isEmpty(menuDto.getName())) {
      throw new Exception();
    }

    if (StringUtils.isEmpty(menuDto.getOrder())) {
      throw new Exception();
    }

    if (StringUtils.isEmpty(menuDto.getDescription())) {
      throw new Exception();
    }

    if (menuRepository.findByRelativeUrl(menuDto.getRelativeUrl()) != null) {
      throw new Exception();
    }

    Optional<Menu> optional = MeMapper.from(menuDto)
        .toOptional(Menu.class);

    if (optional.isPresent()) {
      Menu menu = menuRepository.save(optional.get());
      return menu.getId();
    }

    throw new Exception();
  }

  /**
   * Id parametresine göre menu bilgisine ulaşır.
   * @param menuId menu bilgisine ait id parametresidir.
   * @return MenuDto tipinde application bilgisi döner.
   * @throws Exception Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public MenuDto getOne(Integer menuId) throws Exception {
    Menu menu = menuRepository.getOne(menuId);

    if (menu == null) {
      throw new Exception();
    }

    Optional<MenuDto> optional = MeMapper.from(menu)
        .toOptional(MenuDto.class);

    if (optional.isPresent()) {
      return optional.get();
    }

    throw new Exception();
  }

  /**
   * Menu tablosundan bilgi günceller.
   * @param menuDto güncellenecek bilgidir.
   * @return Güncellenen menu id integer tipinde döner.
   * @throws Exception Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public MenuDto update(MenuDto menuDto) throws Exception {
    Optional<Menu> optional = MeMapper.from(menuDto)
        .toOptional(Menu.class);

    if (optional.isPresent()) {
      menuRepository.save(optional.get());
      return menuDto;
    }

    throw new Exception();
  }

  /**
   * Application tablosundan bilgi siler.
   * @param menuId menu bilgisine ait id parametresidir.
   * @throws Exception Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public void delete(Integer menuId) throws Exception {
    menuRepository.delete(menuId);
  }

}
