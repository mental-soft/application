package com.teammental.application.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception'ların yönetildiği sınıftır.
 */

public class CustomException extends Exception {

  public static final String application_not_found = "Herhangi bir uygulama bulunamadı.";
  public static final String application_required = "Bu alan zorunludur.";
  public static final String application_key_required = "Anahtar alanı zorunludur.";
  public static final String application_name_required = "İsim alanı zorunludur.";
  public static final String application_same_key = "Bu anahtar kelime ile bir uygulama zaten var.";
  public static final String application_save_error = "Kaydetme işlemi sırasında bir hata oluştu.";
  public static final String application_has_menu = "Bu uygulama silinemez. "
      + "Uygulamaya bağlı işlemler vardır.";

  public static final String menu_not_found = "Herhangi bir işlem bulunamadı.";
  public static final String menu_required = "Bu alan zorunludur.";
  public static final String menu_app_required = "Uygulama alanı zorunludur.";
  public static final String menu_url_required = "URL alanı zorunludur.";
  public static final String menu_name_required = "İsim alanı zorunludur.";
  public static final String menu_order_required = "Sıra alanı zorunludur.";
  public static final String menu_same_url = "Bu link ile bir işlem zaten var.";
  public static final String menu_save_error = "Kaydetme işlemi sırasında bir hata oluştu.";

  private HttpStatus code;
  private String label;

  public CustomException(HttpStatus code, String label) {
    this.code = code;
    this.label = label;
  }

  public HttpStatus getCode() {
    return code;
  }

  public void setCode(HttpStatus code) {
    this.code = code;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

}
