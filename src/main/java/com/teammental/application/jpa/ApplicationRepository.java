package com.teammental.application.jpa;

import com.teammental.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Application Repository Sınıfı.
 */

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
}
