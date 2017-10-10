package com.teammental.application.jpa;

import com.teammental.application.entity.Menu;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Menu Repository Sınıfı.
 */

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

  List<Menu> findByApplicationId(Integer applicationId);

  Menu findByRelativeUrl(String relativeUrl);

}
