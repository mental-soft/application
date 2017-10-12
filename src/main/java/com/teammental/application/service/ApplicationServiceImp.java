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
   * Bütün application bilgilerine ulaşır.
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

    throw new CustomException(0, CustomException.application_not_found);
  }

  /**
   * Bütün application bilgilerine id, name şeklinde ulaşır.
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

    throw new CustomException(0, CustomException.application_not_found);
  }

  /**
   * Application tablosune bilgi kayıt eder.
   * @param applicationDto kayıt edilecek bilgidir.
   * @return Kayıt edilen application id integer tipinde döner.
   * @throws CustomException Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public Integer save(ApplicationDto applicationDto) throws CustomException {
    if (StringUtils.isEmpty(applicationDto.getKey())) {
      throw new CustomException(1, CustomException.application_key_required);
    }

    if (StringUtils.isEmpty(applicationDto.getName())) {
      throw new CustomException(2, CustomException.application_name_required);
    }

    if (StringUtils.isEmpty(applicationDto.getDescription())) {
      throw new CustomException(3, CustomException.application_description_required);
    }

    if (applicationRepository.findByKey(applicationDto.getKey()) != null) {
      throw new CustomException(4, CustomException.application_same_key);
    }

    Optional<Application> optional = MeMapper.from(applicationDto)
        .toOptional(Application.class);

    if (optional.isPresent()) {
      return applicationRepository.save(optional.get()).getId();
    }

    throw new CustomException(0, CustomException.application_save_error);
  }

  /**
   * Id parametresine göre application bilgisine ulaşır.
   * @param applicationId application bilgisine ait id parametresidir.
   * @return ApplicationDto tipinde application bilgisi döner.
   * @throws CustomException Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public ApplicationDto getOne(Integer applicationId) throws CustomException {
    Optional<ApplicationDto> optional = MeMapper.from(applicationRepository.getOne(applicationId))
        .toOptional(ApplicationDto.class);

    if (optional.isPresent()) {
      return optional.get();
    }

    throw new CustomException(0, CustomException.application_not_found);
  }

  /**
   * Application tablosundan bilgi günceller.
   * @param applicationDto güncellenecek bilgidir.
   * @return Güncellenen application id integer tipinde döner.
   * @throws CustomException Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public ApplicationDto update(ApplicationDto applicationDto) throws CustomException {
    if (StringUtils.isEmpty(applicationDto.getKey())) {
      throw new CustomException(1, CustomException.application_key_required);
    }

    if (StringUtils.isEmpty(applicationDto.getName())) {
      throw new CustomException(2, CustomException.application_name_required);
    }

    if (StringUtils.isEmpty(applicationDto.getDescription())) {
      throw new CustomException(3, CustomException.application_description_required);
    }

    if (applicationRepository.findByKey(applicationDto.getKey()) != null) {
      throw new CustomException(4, CustomException.application_same_key);
    }

    Optional<Application> optional = MeMapper.from(applicationDto)
        .toOptional(Application.class);

    if (optional.isPresent()) {
      applicationRepository.save(optional.get());
      return applicationDto;
    }

    throw new CustomException(0, CustomException.application_save_error);
  }

  /**
   * Application tablosundan bilgi siler.
   * @param applicationId application bilgisine ait id parametresidir.
   * @throws CustomException Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public void delete(Integer applicationId) throws CustomException {
    Application application = applicationRepository.getOne(applicationId);

    if (application == null) {
      throw new CustomException(0, CustomException.application_not_found);
    }

    if (menuRepository.findByApplicationId(applicationId).size() > 0) {
      throw new CustomException(1, CustomException.application_has_menu);
    }

    applicationRepository.delete(application);
  }

}
