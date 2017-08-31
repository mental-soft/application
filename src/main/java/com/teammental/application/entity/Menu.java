package com.teammental.application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Menu tablosu modeli.
 */

@Entity
@Table(name = "MENTAL_MENU")
public class Menu {

  @Id
  @Column(name = "ID", columnDefinition = "NUMERIC")
  private int id;

  @Column(name = "PARENT_ID", columnDefinition = "NUMERIC")
  private int parentId;

  @Column(name = "APPLICATION_ID", columnDefinition = "NUMERIC")
  private int applicationId;

  @Size(max = 250)
  @Column(name = "RELATIVE_URL", columnDefinition = "VARCHAR")
  private String relativeUrl;

  @Size(max = 150)
  @Column(name = "NAME", columnDefinition = "NVARCHAR")
  private String name;

  @Column(name = "ORDER", columnDefinition = "NUMERIC")
  private int order;

  @Size(max = 300)
  @Column(name = "DESCRIPTION", columnDefinition = "NVARCHAR")
  private String description;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getParentId() {
    return parentId;
  }

  public void setParentId(int parentId) {
    this.parentId = parentId;
  }

  public int getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(int applicationId) {
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

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
