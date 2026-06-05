package com.devon.building.service.impl;

import com.devon.building.builder.BuildingSearchBuilder;
import com.devon.building.constant.SystemConstant;
import com.devon.building.converter.BuildingMapper;
import com.devon.building.converter.BuildingSearchConverter;
import com.devon.building.entity.AssignmentBuilding;
import com.devon.building.entity.Building;
import com.devon.building.entity.RentArea;
import com.devon.building.entity.User;
import com.devon.building.exception.AppException;
import com.devon.building.exception.ErrorCode;
import com.devon.building.model.dto.ResponseDTO;
import com.devon.building.model.request.AssignmentBuildingRequest;
import com.devon.building.model.request.BuildingCreateRequest;
import com.devon.building.model.request.BuildingSearchRequest;
import com.devon.building.model.request.BuildingUpdateRequest;
import com.devon.building.model.response.BuildingResponse;
import com.devon.building.model.response.BuildingSearchResponse;
import com.devon.building.model.response.StaffResponse;
import com.devon.building.repository.AssignmentBuildingRepository;
import com.devon.building.repository.BuildingRepository;
import com.devon.building.repository.RentAreaRepository;
import com.devon.building.repository.UserRepository;
import com.devon.building.service.BuildingService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BuildingServiceImpl implements BuildingService {

    BuildingSearchConverter buildingSearchConverter;
    UserRepository userRepository;
    BuildingRepository buildingRepository;
    RentAreaRepository rentAreaRepository;
    AssignmentBuildingRepository assignmentBuildingRepository;
    BuildingMapper buildingMapper;


    @Override
    public List<BuildingSearchResponse> search(BuildingSearchRequest request) {
        BuildingSearchBuilder builder = buildingSearchConverter.convertToBuilder(request);
        List<Building> buildings = buildingRepository.search(builder);

        return buildings.stream()
                .map(buildingSearchConverter::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BuildingResponse findById(Long id) {
        Building building = buildingRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BUILDING_NOT_FOUND));
        BuildingResponse response = buildingMapper.toResponse(building);

        if (building.getType() != null && !building.getType().isBlank()) {
            response.setTypeCodes(Arrays.asList(building.getType().split(",")));
        }

        List<String> rentAreas = rentAreaRepository.findByBuildingId(id)
                .stream()
                .map(item -> String.valueOf(item.getValue()))
                .toList();
        response.setRentArea(rentAreas);

        return response;
    }

    private void saveRentAreas(Long buildingId, List<String> rentAreas) {
        if (rentAreas == null || rentAreas.isEmpty()) return; //

        for (String area : rentAreas) {
            if (area == null || area.isBlank()) continue; //["100", "", "  ", null, "200"]

            RentArea rentArea = new RentArea();
            rentArea.setBuildingId(buildingId);
            rentArea.setValue(Long.parseLong(area.trim()));

            rentAreaRepository.save(rentArea);
        }
    }

    private void validateRequest(List<String> typeCodes, List<String> rentAreas) {
        if (typeCodes == null || typeCodes.isEmpty()) {
            throw new IllegalArgumentException("Type codes are required");
        }
        if (rentAreas == null || rentAreas.isEmpty()) {
            throw new IllegalArgumentException("Rent areas are required");
        }
    }

    @Override
    public void create(BuildingCreateRequest request) {
        validateRequest(request.getTypeCodes(), request.getRentArea());

        Building building = buildingMapper.toEntity(request);

        if (request.getTypeCodes() != null && !request.getTypeCodes().isEmpty()) {
            building.setType(String.join(",", request.getTypeCodes()));
        }

        Building savedBuilding = buildingRepository.save(building);
        saveRentAreas(savedBuilding.getId(), request.getRentArea());
    }

    @Override
    public void update(Long id, BuildingUpdateRequest request) {
        validateRequest(request.getTypeCodes(), request.getRentArea());
        Building building = buildingRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BUILDING_NOT_FOUND));
        buildingMapper.updateEntity(building, request);

        if (request.getTypeCodes() != null && !request.getTypeCodes().isEmpty()) {
            building.setType(String.join(",", request.getTypeCodes()));
        } else {
            building.setType(null);
        }
        buildingRepository.save(building);
        rentAreaRepository.deleteByBuildingId(id);
        saveRentAreas(id, request.getRentArea());
    }

    @Override
    public void delete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }

        assignmentBuildingRepository.deleteByBuildingIdIn(ids);
        rentAreaRepository.deleteByBuildingIdIn(ids);
        buildingRepository.deleteAllById(ids);
    }

    @Override
    public ResponseDTO loadStaffs(Long buildingId) {
        List<User> staffs = userRepository.findByUserRoleAndActiveTrue(SystemConstant.STAFF_ROLE);

        Set<Long> assignedStaffIds = assignmentBuildingRepository.findByBuildingId(buildingId)
                .stream()
                .map(assignment -> assignment.getStaff().getId())
                .collect(Collectors.toSet());

        List<StaffResponse> responses = new ArrayList<>();

        for (User user : staffs) {
            StaffResponse item = new StaffResponse();
            item.setId(user.getId());
            item.setUserName(user.getUserName());
            if (assignedStaffIds.contains(user.getId())) {
                item.setChecked("checked");
            }
            responses.add(item);
        }

        return buildStaffResponse(responses);
    }

    private ResponseDTO buildStaffResponse(List<StaffResponse> responses) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Load staffs successfully");
        responseDTO.setData(responses);
        return responseDTO;
    }

    @Override
    public void assignBuilding(AssignmentBuildingRequest request) {
        Long buildingId = request.getBuildingId();
        if (buildingId == null) {
            throw new AppException(ErrorCode.BUILDING_ID_REQUIRED);
        }
        Building building = buildingRepository.findById(buildingId).orElseThrow(() -> new AppException(ErrorCode.BUILDING_NOT_FOUND));
        assignmentBuildingRepository.deleteByBuildingId(building.getId());
        List<Long> staffIds = request.getStaffId();
        if (staffIds == null || staffIds.isEmpty()) {
            return;
        }
        List<User> staffs = userRepository.findAllById(staffIds);

        List<AssignmentBuilding> assignmentBuildings = staffs.stream()
                .distinct()
                .map(staffId -> {
                    AssignmentBuilding assignment = new AssignmentBuilding();
                    assignment.setBuilding(building);
                    assignment.setStaff(staffId);
                    return assignment;
                })
                .toList();
        assignmentBuildingRepository.saveAll(assignmentBuildings);
    }


}
