package com.teammental.application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Menu tablosu modeli.
 */

@Entity
@Table(name = "menu",
    uniqueConstraints = @UniqueConstraint(columnNames = "relative_url"))
public class Menu {

  @Id
  @Column(name = "id", columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "parent_id", columnDefinition = "integer")
  private Integer parentId;

  @NotNull
  @Column(name = "application_id", columnDefinition = "integer")
  private Integer applicationId;

  @NotNull
  @Size(max = 250)
  @Column(name = "relative_url", columnDefinition = "varchar")
  private String relativeUrl;

  @NotNull
  @Size(max = 150)
  @Column(name = "name", columnDefinition = "nvarchar")
  private String name;

  @NotNull
  @Column(name = "order", columnDefinition = "integer")
  private Integer order;

  @Size(max = 300)
  @Column(name = "description", columnDefinition = "nvarchar")
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
