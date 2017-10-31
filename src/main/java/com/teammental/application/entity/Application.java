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
 * Application tablosu modeli.
 */

@Entity
@Table(name = "application",
    uniqueConstraints = @UniqueConstraint(columnNames = "key"))
public class Application {

  @Id
  @Column(name = "id", columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  @Size(max = 100)
  @Column(name = "key", columnDefinition = "varchar")
  private String key;

  @NotNull
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
