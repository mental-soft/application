package com.teammental.application.service;

import com.teammental.application.dto.MenuDto;
import com.teammental.application.entity.Menu;
import com.teammental.application.exception.CustomException;
import com.teammental.application.jpa.MenuRepository;
import com.teammental.memapper.MeMapper;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
   * Bütün işlem bilgilerine ulaşır.
   * @return MenuDto tipinde liste döner.
   * @throws CustomException Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public List<MenuDto> findAll() throws CustomException {
    Optional<List<MenuDto>> optional = MeMapper.from(menuRepository.findAll())
        .toOptional(MenuDto.class);

    if (optional.isPresent() && optional.get().size() > 0) {
      return optional.get();
    }

    throw new CustomException(HttpStatus.NOT_FOUND, CustomException.menu_not_found);
  }

  /**
   * applicationId parametresine göre işlem bilgisine ulaşır.
   * @param applicationId uygulamaya ait id bilgisi.
   * @return MenuDto tipinde işlem bilgisi döner.
   * @throws CustomException Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public List<MenuDto> findByApplicationId(Integer applicationId) throws CustomException {
    Optional<List<MenuDto>> optional = MeMapper
        .from(menuRepository.findByApplicationId(applicationId))
        .toOptional(MenuDto.class);

    if (optional.isPresent() && optional.get().size() > 0) {
      return optional.get();
    }

    throw new CustomException(HttpStatus.NOT_FOUND, CustomException.menu_not_found);
  }

  /**
   * İşlem tablosune bilgi kayıt eder.
   * @param menuDto kayıt edilecek işlemdir.
   * @return Kayıt edilecek işlem id bilgisi.
   * @throws CustomException Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public Integer saveOrUpdate(MenuDto menuDto) throws CustomException {
    if (StringUtils.isEmpty(menuDto.getApplicationId())) {
      throw new CustomException(HttpStatus.BAD_REQUEST, CustomException.menu_app_required);
    }

    if (StringUtils.isEmpty(menuDto.getRelativeUrl())) {
      throw new CustomException(HttpStatus.BAD_REQUEST, CustomException.menu_url_required);
    }

    if (StringUtils.isEmpty(menuDto.getName())) {
      throw new CustomException(HttpStatus.BAD_REQUEST, CustomException.menu_name_required);
    }

    if (StringUtils.isEmpty(menuDto.getOrder())) {
      throw new CustomException(HttpStatus.BAD_REQUEST, CustomException.menu_order_required);
    }

    if (menuRepository.findByRelativeUrl(menuDto.getRelativeUrl()) != null) {
      throw new CustomException(HttpStatus.CONFLICT, CustomException.menu_same_url);
    }

    Optional<Menu> optional = MeMapper.from(menuDto)
        .toOptional(Menu.class);

    if (optional.isPresent()) {
      Menu menu = menuRepository.save(optional.get());
      return menu.getId();
    }

    throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomException.menu_save_error);
  }

  /**
   * Id parametresine göre işlem bilgisine ulaşır.
   * @param id istenilen işlemin id bilgisi.
   * @return MenuDto tipinde işlem bilgisi döner.
   * @throws CustomException Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public MenuDto findById(Integer id) throws CustomException {
    Optional<MenuDto> optional = MeMapper.from(menuRepository.findOne(id))
        .toOptional(MenuDto.class);

    if (optional.isPresent()) {
      return optional.get();
    }

    throw new CustomException(HttpStatus.NOT_FOUND, CustomException.menu_not_found);
  }

  /**
   * İşlem tablosundan bilgi siler.
   * @param id silinecek işleme ait id bilgisi.
   * @return Boolean tipinde işlem silinme durumu döner.
   * @throws CustomException Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public Boolean delete(Integer id) throws CustomException {
    Menu menu = menuRepository.findOne(id);

    if (menu == null) {
      throw new CustomException(HttpStatus.NOT_FOUND, CustomException.menu_not_found);
    }

    menuRepository.delete(menu);
    return true;
  }

}
