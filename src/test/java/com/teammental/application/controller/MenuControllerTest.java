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
import com.teammental.application.dto.MenuDto;
import com.teammental.application.exception.CustomException;
import com.teammental.application.service.MenuService;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Menu Controller Test Sınıfı.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(value = MenuController.class, secure = false)
public class MenuControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MenuService menuService;

  private List<MenuDto> list;
  private List<MenuDto> listByApplicationId;
  private MenuDto menuDto;

  /**
   * Test işlemleri için kullanılacak veriler oluşturulur.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Before
  public void init() throws Exception {
    list = new ArrayList<>();
    list.add(getMenuDto(1,1,1,"RELATIVEURL1", "NAME1", 1, "DESCRIPTION1"));
    list.add(getMenuDto(2,2,2,"RELATIVEURL2", "NAME2", 2, "DESCRIPTION2"));
    list.add(getMenuDto(3,3,3,"RELATIVEURL3", "NAME3", 3, "DESCRIPTION3"));

    menuDto = getMenuDto(5,5,5,"RELATIVEURL5", "NAME5", 5, "DESCRIPTION5");

    listByApplicationId = new ArrayList<>();
    listByApplicationId.add(menuDto);
  }

  /**
   * HTTP Status = 200 - OK.
   * Bütün işlemlerin görüntülenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void getMenus_ok() throws Exception {
    when(menuService.findAll())
        .thenReturn(list);

    mockMvc.perform(get(Map.MENU))
        .andExpect(status().isOk())
        .andExpect(content().contentType(Map.JSON_UTF8))
        .andExpect(content().json(objectToJson(list)));

    verify(menuService, times(1)).findAll();
    verifyNoMoreInteractions(menuService);
  }

  /**
   * HTTP Status = 404 - NOT FOUND.
   * Bütün işlemlerin görüntülenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void getMenus_notFound() throws Exception {
    when(menuService.findAll())
        .thenThrow(new CustomException(HttpStatus.NOT_FOUND, CustomException.menu_not_found));

    mockMvc.perform(get(Map.MENU))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(Map.JSON_UTF8))
        .andExpect(content().string(CustomException.menu_not_found));

    verify(menuService, times(1)).findAll();
    verifyNoMoreInteractions(menuService);
  }

  /**
   * HTTP Status = 200 - OK.
   * Uygulama id bilgisine göre bütün işlemlerin görüntülenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void getMenusByApplicationId_ok() throws Exception {
    when(menuService.findByApplicationId(menuDto.getApplicationId()))
        .thenReturn(listByApplicationId);

    mockMvc.perform(get(Map.MENU_APP, menuDto.getApplicationId())
        .contentType(Map.JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(Map.JSON_UTF8))
        .andExpect(content().json(objectToJson(listByApplicationId)));

    verify(menuService, times(1)).findByApplicationId(menuDto.getApplicationId());
    verifyNoMoreInteractions(menuService);
  }

  /**
   * HTTP Status = 404 - NOT FOUND.
   * Uygulama id bilgisine göre bütün işlemlerin görüntülenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void getMenusByApplicationId_notFound() throws Exception {
    when(menuService.findByApplicationId(menuDto.getApplicationId()))
        .thenThrow(new CustomException(HttpStatus.NOT_FOUND, CustomException.menu_not_found));

    mockMvc.perform(get(Map.MENU_APP, menuDto.getApplicationId())
        .contentType(Map.JSON_UTF8))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(Map.JSON_UTF8))
        .andExpect(content().string(CustomException.menu_not_found));

    verify(menuService, times(1)).findByApplicationId(menuDto.getId());
    verifyNoMoreInteractions(menuService);
  }

  /**
   * HTTP Status = 201 - CREATED.
   * Yeni bir işlem eklenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void createMenu_created() throws Exception {
    when(menuService.saveOrUpdate(anyObject()))
        .thenReturn(menuDto.getId());

    mockMvc.perform(post(Map.MENU)
        .contentType(Map.JSON_UTF8)
        .content(objectToJson(menuDto)))
        .andExpect(status().isCreated())
        .andExpect(content().string(""))
        .andExpect(header().string("Location", Map.MENU_URI + menuDto.getId()));

    verify(menuService, times(1)).saveOrUpdate(anyObject());
    verifyNoMoreInteractions(menuService);
  }

  /**
   * HTTP Status = 400 - BAD REQUEST.
   * Yeni bir işlem eklenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void createMenu_badRequest() throws Exception {
    when(menuService.saveOrUpdate(anyObject()))
        .thenThrow(new CustomException(HttpStatus.BAD_REQUEST, CustomException.menu_required));

    mockMvc.perform(post(Map.MENU)
        .contentType(Map.JSON_UTF8)
        .content(objectToJson(menuDto)))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(Map.JSON_UTF8))
        .andExpect(content().string(CustomException.menu_required));

    verify(menuService, times(1)).saveOrUpdate(anyObject());
    verifyNoMoreInteractions(menuService);
  }

  /**
   * HTTP Status = 409 - CONFLICT.
   * Yeni bir işlem eklenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void createMenu_conflict() throws Exception {
    when(menuService.saveOrUpdate(anyObject()))
        .thenThrow(new CustomException(HttpStatus.CONFLICT, CustomException.menu_same_url));

    mockMvc.perform(post(Map.MENU)
        .contentType(Map.JSON_UTF8)
        .content(objectToJson(menuDto)))
        .andExpect(status().isConflict())
        .andExpect(content().contentType(Map.JSON_UTF8))
        .andExpect(content().string(CustomException.menu_same_url));

    verify(menuService, times(1)).saveOrUpdate(anyObject());
    verifyNoMoreInteractions(menuService);
  }

  /**
   * HTTP Status = 500 - INTERNAL SERVER ERROR.
   * Yeni bir işlem eklenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void createMenu_internalServerError() throws Exception {
    when(menuService.saveOrUpdate(anyObject()))
        .thenThrow(new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomException.menu_save_error));

    mockMvc.perform(post(Map.MENU)
        .contentType(Map.JSON_UTF8)
        .content(objectToJson(menuDto)))
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentType(Map.JSON_UTF8))
        .andExpect(content().string(CustomException.menu_save_error));

    verify(menuService, times(1)).saveOrUpdate(anyObject());
    verifyNoMoreInteractions(menuService);
  }

  /**
   * HTTP Status = 200 - OK.
   * Tek işlem görüntülenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void getMenuById_ok() throws Exception {
    when(menuService.findById(menuDto.getId()))
        .thenReturn(menuDto);

    mockMvc.perform(get(Map.MENU_DETAIL, menuDto.getId())
        .contentType(Map.JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(Map.JSON_UTF8))
        .andExpect(content().json(objectToJson(menuDto)));

    verify(menuService, times(1)).findById(menuDto.getId());
    verifyNoMoreInteractions(menuService);
  }

  /**
   * HTTP Status = 404 - NOT FOUND.
   * Tek işlem görüntülenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void getMenuById_notFound() throws Exception {
    when(menuService.findById(menuDto.getId()))
        .thenThrow(new CustomException(HttpStatus.NOT_FOUND, CustomException.menu_not_found));

    mockMvc.perform(get(Map.MENU_DETAIL, menuDto.getId())
        .contentType(Map.JSON_UTF8))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(Map.JSON_UTF8))
        .andExpect(content().string(CustomException.menu_not_found));

    verify(menuService, times(1)).findById(menuDto.getId());
    verifyNoMoreInteractions(menuService);
  }

  /**
   * HTTP Status = 200 - OK.
   * Bir işlemin güncellenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void updateMenu_ok() throws Exception {
    when(menuService.saveOrUpdate(anyObject()))
        .thenReturn(menuDto.getId());

    when(menuService.findById(menuDto.getId()))
        .thenReturn(menuDto);

    mockMvc.perform(put(Map.MENU_DETAIL, menuDto.getId())
        .contentType(Map.JSON_UTF8)
        .content(objectToJson(menuDto)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(Map.JSON_UTF8))
        .andExpect(content().json(objectToJson(menuDto)));

    verify(menuService, times(1)).saveOrUpdate(anyObject());
    verify(menuService, times(1)).findById(menuDto.getId());
    verifyNoMoreInteractions(menuService);
  }

  /**
   * HTTP Status = 400 - BAD REQUEST.
   * Bir işlemin güncellenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void updateMenu_badRequest() throws Exception {
    when(menuService.saveOrUpdate(anyObject()))
        .thenThrow(new CustomException(HttpStatus.BAD_REQUEST, CustomException.menu_required));

    mockMvc.perform(put(Map.MENU_DETAIL, menuDto.getId())
        .contentType(Map.JSON_UTF8)
        .content(objectToJson(menuDto)))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(Map.JSON_UTF8))
        .andExpect(content().string(CustomException.menu_required));

    verify(menuService, times(1)).saveOrUpdate(anyObject());
    verifyNoMoreInteractions(menuService);
  }

  /**
   * HTTP Status = 409 - CONFLICT.
   * Bir işlemin güncellenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void updateMenu_conflict() throws Exception {
    when(menuService.saveOrUpdate(anyObject()))
        .thenThrow(new CustomException(HttpStatus.CONFLICT, CustomException.menu_same_url));

    mockMvc.perform(put(Map.MENU_DETAIL, menuDto.getId())
        .contentType(Map.JSON_UTF8)
        .content(objectToJson(menuDto)))
        .andExpect(status().isConflict())
        .andExpect(content().contentType(Map.JSON_UTF8))
        .andExpect(content().string(CustomException.menu_same_url));

    verify(menuService, times(1)).saveOrUpdate(anyObject());
    verifyNoMoreInteractions(menuService);
  }

  /**
   * HTTP Status = 500 - INTERNAL SERVER ERROR.
   * Bir işlemin güncellenmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void updateMenu_internalServerError() throws Exception {
    when(menuService.saveOrUpdate(anyObject()))
        .thenThrow(new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomException.menu_save_error));

    mockMvc.perform(put(Map.MENU_DETAIL, menuDto.getId())
        .contentType(Map.JSON_UTF8)
        .content(objectToJson(menuDto)))
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentType(Map.JSON_UTF8))
        .andExpect(content().string(CustomException.menu_save_error));

    verify(menuService, times(1)).saveOrUpdate(anyObject());
    verifyNoMoreInteractions(menuService);
  }

  /**
   * HTTP Status = 204 - NO CONTENT.
   * Bir işlemin silinmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void deleteMenu_noContent() throws Exception {
    when(menuService.delete(menuDto.getId()))
        .thenReturn(true);

    mockMvc.perform(delete(Map.MENU_DETAIL, menuDto.getId())
        .contentType(Map.JSON_UTF8))
        .andExpect(status().isNoContent());

    verify(menuService, times(1)).delete(menuDto.getId());
    verifyNoMoreInteractions(menuService);
  }

  /**
   * HTTP Status = 404 - NOT FOUND.
   * Bir işlemin silinmesi test ediliyor.
   * @throws Exception Oluşabilecek hata ve mesajları döndürür.
   */
  @Test
  public void deleteMenu_notFound() throws Exception {
    when(menuService.delete(menuDto.getId()))
        .thenThrow(new CustomException(HttpStatus.NOT_FOUND, CustomException.menu_not_found));

    mockMvc.perform(delete(Map.MENU_DETAIL, menuDto.getId())
        .contentType(Map.JSON_UTF8))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(Map.JSON_UTF8))
        .andExpect(content().string(CustomException.menu_not_found));

    verify(menuService, times(1)).delete(menuDto.getId());
    verifyNoMoreInteractions(menuService);
  }

  /**
   * MenuDto oluşturur.
   * @param id Menu id.
   * @param parentId Menu parentId.
   * @param applicationId Menu applicationId.
   * @param relativeUrl Menu relativeUrl.
   * @param name Menu name.
   * @param order Menu order.
   * @param description Menu description.
   * @return menuDto.
   */
  private MenuDto getMenuDto(Integer id, Integer parentId, Integer applicationId, String relativeUrl, String name, Integer order, String description) {
    MenuDto menuDto = new MenuDto();
    menuDto.setId(id);
    menuDto.setParentId(parentId);
    menuDto.setApplicationId(applicationId);
    menuDto.setRelativeUrl(relativeUrl);
    menuDto.setName(name);
    menuDto.setOrder(order);
    menuDto.setDescription(description);
    return menuDto;
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


