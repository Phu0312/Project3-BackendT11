package com.devon.building.repository.custom;

import com.devon.building.builder.BuildingSearchBuilder;
import com.devon.building.entity.Building;

import java.util.List;

public interface BuildingRepositoryCustom {
    List<Building> search(BuildingSearchBuilder builder);
}
