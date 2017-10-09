package com.teammental.application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Application tablosu modeli.
 */

@Entity
@Table(name = "mental_application")
public class Application {

  @Id
  @Column(name = "id", columnDefinition = "serial", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Size(max = 100)
  @Column(name = "key", columnDefinition = "varchar")
  private String key;

  @Size(max = 150)
  @Column(name = "name", columnDefinition = "nvarchar")
  private String name;

  @Size(max = 300)
  @Column(name = "description", columnDefinition = "nvarchar")
  private String description;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
