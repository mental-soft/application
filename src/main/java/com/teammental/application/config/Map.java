package com.teammental.application.config;

/**
 * Mapping string'lerinin bulunduğu sınıf.
 */

public class Map {

  public static final String JSON = "application/json";
  public static final String JSON_UTF8 = JSON + ";charset=UTF-8";

  public static final String APPLICATION = "/applications";
  public static final String APPLICATION_IDNAME = "/applications/idname";
  public static final String APPLICATION_DETAIL = "/applications/{id}";
  public static final String APPLICATION_URI = "/applications/";

  public static final String MENU = "/operations";
  public static final String MENU_APP = "/operations?applicationId={applicationId}";
  public static final String MENU_DETAIL = "/operations/{id}";
  public static final String MENU_URI = "/operations/";

}
