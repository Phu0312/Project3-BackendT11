package com.devon.building.service;

import com.devon.building.model.dto.ResponseDTO;
import com.devon.building.model.request.AssignmentBuildingRequest;
import com.devon.building.model.request.BuildingCreateRequest;
import com.devon.building.model.request.BuildingSearchRequest;
import com.devon.building.model.request.BuildingUpdateRequest;
import com.devon.building.model.response.BuildingResponse;
import com.devon.building.model.response.BuildingSearchResponse;

import java.util.List;

public interface BuildingService {
    List<BuildingSearchResponse> search(BuildingSearchRequest request);

    BuildingResponse findById(Long id);

    void create(BuildingCreateRequest request);

    void update(Long id, BuildingUpdateRequest request);

    void delete(List<Long> ids);

    ResponseDTO loadStaffs(Long buildingId);

    void assignBuilding(AssignmentBuildingRequest request);
}
