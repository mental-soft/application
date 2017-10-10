package com.teammental.application.service;

import com.teammental.application.dto.ApplicationDto;
import com.teammental.application.dto.IdNameDto;
import com.teammental.application.entity.Application;
import com.teammental.application.jpa.ApplicationRepository;
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

  /**
   * Bütün application bilgilerine ulaşır.
   * @return ApplicationDto tipinde liste döner.
   * @throws Exception Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public List<ApplicationDto> getAll() throws Exception {
    List<Application> applicationList = applicationRepository.findAll();

    Optional<List<ApplicationDto>> optional = MeMapper.from(applicationList)
        .toOptional(ApplicationDto.class);

    if (optional.isPresent() && optional.get().size() > 0) {
      return optional.get();
    }

    throw new Exception();
  }

  /**
   * Bütün application bilgilerine id, name şeklinde ulaşır.
   * @return IdNameDto tipinde liste döner.
   * @throws Exception Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public List<IdNameDto> getAllIdName() throws Exception {
    List<Application> applicationList = applicationRepository.findAll();

    Optional<List<IdNameDto>> optional = MeMapper.from(applicationList)
        .toOptional(ApplicationDto.class);

    if (optional.isPresent() && optional.get().size() > 0) {
      return optional.get();
    }

    throw new Exception();
  }

  /**
   * Application tablosune bilgi kayıt eder.
   * @param applicationDto kayıt edilecek bilgidir.
   * @return Kayıt edilen application id integer tipinde döner.
   * @throws Exception Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public Integer save(ApplicationDto applicationDto) throws Exception {
    if (StringUtils.isEmpty(applicationDto.getKey())) {
      throw new Exception();
    }

    if (StringUtils.isEmpty(applicationDto.getName())) {
      throw new Exception();
    }

    if (StringUtils.isEmpty(applicationDto.getDescription())) {
      throw new Exception();
    }

    if (applicationRepository.findByKey(applicationDto.getKey()) != null) {
      throw new Exception();
    }

    Optional<Application> optional = MeMapper.from(applicationDto)
        .toOptional(Application.class);

    if (optional.isPresent()) {
      Application application = applicationRepository.save(optional.get());
      return application.getId();
    }

    throw new Exception();
  }

  /**
   * Id parametresine göre application bilgisine ulaşır.
   * @param applicationId application bilgisine ait id parametresidir.
   * @return ApplicationDto tipinde application bilgisi döner.
   * @throws Exception Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public ApplicationDto getOne(Integer applicationId) throws Exception {
    Application application = applicationRepository.getOne(applicationId);

    if (application == null) {
      throw new Exception();
    }

    Optional<ApplicationDto> optional = MeMapper.from(application)
        .toOptional(ApplicationDto.class);

    if (optional.isPresent()) {
      return optional.get();
    }

    throw new Exception();
  }

  /**
   * Application tablosundan bilgi günceller.
   * @param applicationDto güncellenecek bilgidir.
   * @return Güncellenen application id integer tipinde döner.
   * @throws Exception Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public ApplicationDto update(ApplicationDto applicationDto) throws Exception {
    Optional<Application> optional = MeMapper.from(applicationDto)
        .toOptional(Application.class);

    if (optional.isPresent()) {
      applicationRepository.save(optional.get());
      return applicationDto;
    }

    throw new Exception();
  }

  /**
   * Application tablosundan bilgi siler.
   * @param applicationId application bilgisine ait id parametresidir.
   * @throws Exception Oluşabilecek hata ve mesajları oluşturur.
   */
  @Override
  public void delete(Integer applicationId) throws Exception {
    applicationRepository.delete(applicationId);
  }

}
