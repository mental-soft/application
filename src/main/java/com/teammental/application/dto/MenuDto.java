package com.teammental.application.dto;

/**
 * Menu Dto Sınıfı.
 */

public class MenuDto {

  private Integer id;
  private Integer parentId;
  private Integer applicationId;
  private String relativeUrl;
  private String name;
  private Integer order;
  private String description;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public Integer getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(Integer applicationId) {
    this.applicationId = applicationId;
  }

  public String getRelativeUrl() {
    return relativeUrl;
  }

  public void setRelativeUrl(String relativeUrl) {
    this.relativeUrl = relativeUrl;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
