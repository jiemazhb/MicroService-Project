package com.nailsWeb.salonService.repository;

import com.nailsWeb.salonService.entity.SalonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalonRepository extends JpaRepository<SalonEntity, Long> {
}
