package com.teammental.application.service;

import com.teammental.application.dto.ApplicationDto;
import com.teammental.application.dto.IdNameDto;
import com.teammental.application.entity.Application;
import com.teammental.application.exception.CustomException;
import com.teammental.application.jpa.ApplicationRepository;
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
 * Application Servis Sınıfı.
 */

@Service
@Transactional
public class ApplicationServiceImp implements ApplicationService {

  @Autowired
  private ApplicationRepository applicationRepository;

  @Autowired
  private MenuRepository menuRepository;

  /**
   * Bütün uygulama bilgilerine ulaşır.
   * @return ApplicationDto tipinde liste döner.
   * @throws CustomException Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public List<ApplicationDto> getAll() throws CustomException {
    Optional<List<ApplicationDto>> optional = MeMapper.from(applicationRepository.findAll())
        .toOptional(ApplicationDto.class);

    if (optional.isPresent() && optional.get().size() > 0) {
      return optional.get();
    }

    throw new CustomException(HttpStatus.NOT_FOUND, CustomException.application_not_found);
  }

  /**
   * Bütün uygulama bilgilerine id, name şeklinde ulaşır.
   * @return IdNameDto tipinde liste döner.
   * @throws CustomException Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public List<IdNameDto> getAllIdName() throws CustomException {
    Optional<List<IdNameDto>> optional = MeMapper.from(applicationRepository.findAll())
        .toOptional(ApplicationDto.class);

    if (optional.isPresent() && optional.get().size() > 0) {
      return optional.get();
    }

    throw new CustomException(HttpStatus.NOT_FOUND, CustomException.application_not_found);
  }

  /**
   * Uygulama tablosuna bilgi kayıt eder.
   * @param applicationDto kayıt edilecek uygulamadır.
   * @return Kayıt edilecek uygulama id bilgisi.
   * @throws CustomException Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public Integer saveOrUpdate(ApplicationDto applicationDto) throws CustomException {
    if (StringUtils.isEmpty(applicationDto.getKey())) {
      throw new CustomException(HttpStatus.BAD_REQUEST, CustomException.application_key_required);
    }

    if (StringUtils.isEmpty(applicationDto.getName())) {
      throw new CustomException(HttpStatus.BAD_REQUEST, CustomException.application_name_required);
    }

    if (StringUtils.isEmpty(applicationDto.getDescription())) {
      throw new CustomException(HttpStatus.BAD_REQUEST,
          CustomException.application_description_required);
    }

    if (applicationRepository.findByKey(applicationDto.getKey()) != null) {
      throw new CustomException(HttpStatus.CONFLICT, CustomException.application_same_key);
    }

    Optional<Application> optional = MeMapper.from(applicationDto)
        .toOptional(Application.class);

    if (optional.isPresent()) {
      return applicationRepository.save(optional.get()).getId();
    }

    throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,
        CustomException.application_save_error);
  }

  /**
   * Id parametresine göre uygulama bilgisine ulaşır.
   * @param id istenilen uygulamanın id bilgisi.
   * @return ApplicationDto tipinde uygulama bilgisi döner.
   * @throws CustomException Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public ApplicationDto getOne(Integer id) throws CustomException {
    Optional<ApplicationDto> optional = MeMapper.from(applicationRepository.getOne(id))
        .toOptional(ApplicationDto.class);

    if (optional.isPresent()) {
      return optional.get();
    }

    throw new CustomException(HttpStatus.NOT_FOUND, CustomException.application_not_found);
  }

  /**
   * Uygulama tablosundan bilgi siler.
   * @param id silinecek uygulamaya ait id bilgisi.
   * @throws CustomException Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public void delete(Integer id) throws CustomException {
    Application application = applicationRepository.getOne(id);

    if (application == null) {
      throw new CustomException(HttpStatus.NOT_FOUND, CustomException.application_not_found);
    }

    if (menuRepository.findByApplicationId(id).size() > 0) {
      throw new CustomException(HttpStatus.BAD_REQUEST, CustomException.application_has_menu);
    }

    applicationRepository.delete(application);
  }

}
