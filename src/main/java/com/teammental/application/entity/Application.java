package com.teammental.application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Application tablosu modeli.
 */

@Entity
@Table(name = "MENTAL_APPLICATION")
public class Application {

  @Id
  @Column(name = "ID", columnDefinition = "NUMERIC")
  private int id;

  @Size(max = 100)
  @Column(name = "KEY", columnDefinition = "VARCHAR")
  private String key;

  @Size(max = 150)
  @Column(name = "NAME", columnDefinition = "NVARCHAR")
  private String name;

  @Size(max = 300)
  @Column(name = "DESCRIPTION", columnDefinition = "NVARCHAR")
  private String description;

  public int getId() {
    return id;
  }

  public void setId(int id) {
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
