package com.devon.building.repository;

import com.devon.building.entity.AssignmentBuilding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentBuildingRepository extends JpaRepository<AssignmentBuilding, Long> {
    List<AssignmentBuilding> findByBuildingId(Long buildingId);

    void deleteByBuildingId(Long buildingId);

    void deleteByBuildingIdIn(List<Long> buildingIds);
}