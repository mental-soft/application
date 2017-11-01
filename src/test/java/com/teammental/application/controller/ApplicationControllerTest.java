package com.teammental.application.controller;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teammental.application.config.Map;
import com.teammental.application.dto.ApplicationDto;
import com.teammental.application.dto.IdNameDto;
import com.teammental.application.exception.CustomException;
import com.teammental.application.service.ApplicationService;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Application Controller Test Sınıfı.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(value = ApplicationController.class, secure = false)
public class ApplicationControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ApplicationService applicationService;

  private List<ApplicationDto> list;
  private List<IdNameDto> listIdName;
  private ApplicationDto applicationDto;

  /**
   * Test işlemleri için kullanılacak veriler oluşturulur.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Before
  public void init() throws Exception {
    list = new ArrayList<>();
    list.add(getApplicationDto(1, "KEY1", "NAME1", "DESCRIPTION1"));
    list.add(getApplicationDto(2, "KEY2", "NAME2", "DESCRIPTION2"));
    list.add(getApplicationDto(3, "KEY3", "NAME3", "DESCRIPTION3"));

    listIdName = new ArrayList<>();
    listIdName.add(getIdNameDto(1, "NAME1"));
    listIdName.add(getIdNameDto(2, "NAME2"));
    listIdName.add(getIdNameDto(3, "NAME3"));

    applicationDto = getApplicationDto(5, "KEY5", "NAME5", "DESCRIPTION5");
  }

  /**
   * HTTP Status = 200 - OK.
   * Bütün uygulamarın görüntülenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void getApplications_ok() throws Exception {
    when(applicationService.findAll())
        .thenReturn(list);

    mockMvc.perform(get(Map.APPLICATION))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json(objectToJson(list)));

    verify(applicationService, times(1)).findAll();
    verifyNoMoreInteractions(applicationService);
  }

  /**
   * HTTP Status = 404 - NOT FOUND.
   * Bütün uygulamarın görüntülenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void getApplications_notFound() throws Exception {
    when(applicationService.findAll())
        .thenThrow(new CustomException(HttpStatus.NOT_FOUND, CustomException.application_not_found));

    mockMvc.perform(get(Map.APPLICATION))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(CustomException.application_not_found));

    verify(applicationService, times(1)).findAll();
    verifyNoMoreInteractions(applicationService);
  }

  /**
   * HTTP Status = 200 - OK.
   * Bütün uygulamarın id, name şeklinde görüntülenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void getApplicationsIdName_ok() throws Exception {
    when(applicationService.findAllIdName())
        .thenReturn(listIdName);

    mockMvc.perform(get(Map.APPLICATION_IDNAME))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json(objectToJson(listIdName)));

    verify(applicationService, times(1)).findAllIdName();
    verifyNoMoreInteractions(applicationService);
  }

  /**
   * HTTP Status = 404 - NOT FOUND.
   * Bütün uygulamarın id, name şeklinde görüntülenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void getApplicationsIdName_notFound() throws Exception {
    when(applicationService.findAllIdName())
        .thenThrow(new CustomException(HttpStatus.NOT_FOUND, CustomException.application_not_found));

    mockMvc.perform(get(Map.APPLICATION_IDNAME))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(CustomException.application_not_found));

    verify(applicationService, times(1)).findAllIdName();
    verifyNoMoreInteractions(applicationService);
  }

  /**
   * HTTP Status = 201 - CREATED.
   * Yeni bir uygulama eklenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void createApplication_created() throws Exception {
    when(applicationService.saveOrUpdate(anyObject()))
        .thenReturn(applicationDto.getId());

    mockMvc.perform(post(Map.APPLICATION)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(objectToJson(applicationDto)))
        .andExpect(status().isCreated())
        .andExpect(content().string(""))
        .andExpect(header().string("Location", Map.APPLICATION_URI + applicationDto.getId()));

    verify(applicationService, times(1)).saveOrUpdate(anyObject());
    verifyNoMoreInteractions(applicationService);
  }

  /**
   * HTTP Status = 400 - BAD REQUEST.
   * Yeni bir uygulama eklenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void createApplication_badRequest() throws Exception {
    when(applicationService.saveOrUpdate(anyObject()))
        .thenThrow(new CustomException(HttpStatus.BAD_REQUEST, CustomException.application_required));

    mockMvc.perform(post(Map.APPLICATION)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(objectToJson(applicationDto)))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(CustomException.application_required));

    verify(applicationService, times(1)).saveOrUpdate(anyObject());
    verifyNoMoreInteractions(applicationService);
  }

  /**
   * HTTP Status = 409 - CONFLICT.
   * Yeni bir uygulama eklenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void createApplication_conflict() throws Exception {
    when(applicationService.saveOrUpdate(anyObject()))
        .thenThrow(new CustomException(HttpStatus.CONFLICT, CustomException.application_same_key));

    mockMvc.perform(post(Map.APPLICATION)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(objectToJson(applicationDto)))
        .andExpect(status().isConflict())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(CustomException.application_same_key));

    verify(applicationService, times(1)).saveOrUpdate(anyObject());
    verifyNoMoreInteractions(applicationService);
  }

  /**
   * HTTP Status = 500 - INTERNAL SERVER ERROR.
   * Yeni bir uygulama eklenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void createApplication_internalServerError() throws Exception {
    when(applicationService.saveOrUpdate(anyObject()))
        .thenThrow(new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomException.application_save_error));

    mockMvc.perform(post(Map.APPLICATION)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(objectToJson(applicationDto)))
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(CustomException.application_save_error));

    verify(applicationService, times(1)).saveOrUpdate(anyObject());
    verifyNoMoreInteractions(applicationService);
  }

  /**
   * HTTP Status = 200 - OK.
   * Tek uygulama görüntülenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void getApplicationById_ok() throws Exception {
    when(applicationService.findById(applicationDto.getId()))
        .thenReturn(applicationDto);

    mockMvc.perform(get(Map.APPLICATION_DETAIL, applicationDto.getId())
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json(objectToJson(applicationDto)));

    verify(applicationService, times(1)).findById(applicationDto.getId());
    verifyNoMoreInteractions(applicationService);
  }

  /**
   * HTTP Status = 404 - NOT FOUND.
   * Tek uygulama görüntülenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void getApplicationById_notFound() throws Exception {
    when(applicationService.findById(applicationDto.getId()))
        .thenThrow(new CustomException(HttpStatus.NOT_FOUND, CustomException.application_not_found));

    mockMvc.perform(get(Map.APPLICATION_DETAIL, applicationDto.getId())
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(CustomException.application_not_found));

    verify(applicationService, times(1)).findById(applicationDto.getId());
    verifyNoMoreInteractions(applicationService);
  }

  /**
   * HTTP Status = 200 - OK.
   * Bir uygulamanın güncellenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void updateApplication_ok() throws Exception {
    when(applicationService.saveOrUpdate(anyObject()))
        .thenReturn(applicationDto.getId());

    when(applicationService.findById(applicationDto.getId()))
        .thenReturn(applicationDto);

    mockMvc.perform(put(Map.APPLICATION_DETAIL, applicationDto.getId())
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(objectToJson(applicationDto)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json(objectToJson(applicationDto)));

    verify(applicationService, times(1)).saveOrUpdate(anyObject());
    verify(applicationService, times(1)).findById(applicationDto.getId());
    verifyNoMoreInteractions(applicationService);
  }

  /**
   * HTTP Status = 400 - BAD REQUEST.
   * Bir uygulamanın güncellenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void updateApplication_badRequest() throws Exception {
    when(applicationService.saveOrUpdate(anyObject()))
        .thenThrow(new CustomException(HttpStatus.BAD_REQUEST, CustomException.application_required));

    mockMvc.perform(put(Map.APPLICATION_DETAIL, applicationDto.getId())
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(objectToJson(applicationDto)))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(CustomException.application_required));

    verify(applicationService, times(1)).saveOrUpdate(anyObject());
    verifyNoMoreInteractions(applicationService);
  }

  /**
   * HTTP Status = 409 - CONFLICT.
   * Bir uygulamanın güncellenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void updateApplication_conflict() throws Exception {
    when(applicationService.saveOrUpdate(anyObject()))
        .thenThrow(new CustomException(HttpStatus.CONFLICT, CustomException.application_same_key));

    mockMvc.perform(put(Map.APPLICATION_DETAIL, applicationDto.getId())
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(objectToJson(applicationDto)))
        .andExpect(status().isConflict())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(CustomException.application_same_key));

    verify(applicationService, times(1)).saveOrUpdate(anyObject());
    verifyNoMoreInteractions(applicationService);
  }

  /**
   * HTTP Status = 500 - INTERNAL SERVER ERROR.
   * Bir uygulamanın güncellenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void updateApplication_internalServerError() throws Exception {
    when(applicationService.saveOrUpdate(anyObject()))
        .thenThrow(new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomException.application_save_error));

    mockMvc.perform(put(Map.APPLICATION_DETAIL, applicationDto.getId())
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(objectToJson(applicationDto)))
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(CustomException.application_save_error));

    verify(applicationService, times(1)).saveOrUpdate(anyObject());
    verifyNoMoreInteractions(applicationService);
  }

  /**
   * HTTP Status = 204 - NO CONTENT.
   * Bir uygulamanın silinmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void deleteApplication_noContent() throws Exception {
    when(applicationService.delete(applicationDto.getId()))
        .thenReturn(true);

    mockMvc.perform(delete(Map.APPLICATION_DETAIL, applicationDto.getId())
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isNoContent());

    verify(applicationService, times(1)).delete(applicationDto.getId());
    verifyNoMoreInteractions(applicationService);
  }

  /**
   * HTTP Status = 404 - NOT FOUND.
   * Bir uygulamanın silinmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void deleteApplication_notFound() throws Exception {
    when(applicationService.delete(applicationDto.getId()))
        .thenThrow(new CustomException(HttpStatus.NOT_FOUND, CustomException.application_not_found));

    mockMvc.perform(delete(Map.APPLICATION_DETAIL, applicationDto.getId())
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(CustomException.application_not_found));

    verify(applicationService, times(1)).delete(applicationDto.getId());
    verifyNoMoreInteractions(applicationService);
  }

  /**
   * HTTP Status = 400 - BAD REQUEST.
   * Bir uygulamanın silinmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void deleteApplication_badRequest() throws Exception {
    when(applicationService.delete(applicationDto.getId()))
        .thenThrow(new CustomException(HttpStatus.BAD_REQUEST, CustomException.application_has_menu));

    mockMvc.perform(delete(Map.APPLICATION_DETAIL, applicationDto.getId())
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().string(CustomException.application_has_menu));

    verify(applicationService, times(1)).delete(applicationDto.getId());
    verifyNoMoreInteractions(applicationService);
  }

  /**
   * ApplicationDto oluşturur.
   * @param id Application id.
   * @param key Application key.
   * @param name Application name.
   * @param description Application description.
   * @return applicationDto.
   */
  private ApplicationDto getApplicationDto(Integer id, String key, String name, String description) {
    ApplicationDto applicationDto = new ApplicationDto();
    applicationDto.setId(id);
    applicationDto.setKey(key);
    applicationDto.setName(name);
    applicationDto.setDescription(description);
    return applicationDto;
  }

  /**
   * IdNameDto oluşturur.
   * @param id Application id.
   * @param name Application name.
   * @return idnameDto.
   */
  private IdNameDto getIdNameDto(Integer id, String name) {
    IdNameDto idNameDto = new IdNameDto();
    idNameDto.setId(id);
    idNameDto.setName(name);
    return idNameDto;
  }

  /**
   * Nesneleri json'a dönüştürür.
   * @param object dönüştürülecek nesne.
   * @return nesnenin json sonucu.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  private String objectToJson(Object object) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(object);
  }

}
