package com.devon.building.repository;

import com.devon.building.entity.RentArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentAreaRepository extends JpaRepository<RentArea, Long> {
    List<RentArea> findByBuildingId(Long buildingId);

    void deleteByBuildingId(Long buildingId);

    void deleteByBuildingIdIn(List<Long> buildingIds);
}
