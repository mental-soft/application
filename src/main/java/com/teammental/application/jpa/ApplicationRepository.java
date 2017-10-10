package com.teammental.application.jpa;

import com.teammental.application.entity.Application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Application Repository Sınıfı.
 */

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

  Application findByKey(String key);

}
