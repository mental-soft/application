package com.teammental.application.jpa;

import com.teammental.application.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Menu Repository Sınıfı.
 */

public interface MenuRepository extends JpaRepository<Menu, Integer> {
}
