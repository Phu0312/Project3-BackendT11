package com.devon.building.converter;

import com.devon.building.builder.BuildingSearchBuilder;
import com.devon.building.entity.Building;
import com.devon.building.entity.RentArea;
import com.devon.building.enums.District;
import com.devon.building.model.request.BuildingSearchRequest;
import com.devon.building.model.response.BuildingSearchResponse;
import com.devon.building.repository.RentAreaRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BuildingSearchConverter {

    RentAreaRepository rentAreaRepository;

    public BuildingSearchBuilder convertToBuilder(BuildingSearchRequest request) {
        return new BuildingSearchBuilder.Builder()
                .setName(request.getName())
                .setFloorArea(request.getFloorArea())
                .setDistrict(request.getDistrict())
                .setWard(request.getWard())
                .setStreet(request.getStreet())
                .setNumberOfBasement(
                        request.getNumberOfBasement() != null && !request.getNumberOfBasement().isBlank()
                                ? Long.valueOf(request.getNumberOfBasement())
                                : null
                )
                .setDirection(request.getDirection())
                .setLevel(request.getLevel())
                .setAreaFrom(request.getAreaFrom())
                .setAreaTo(request.getAreaTo())
                .setRentPriceFrom(request.getRentPriceFrom())
                .setRentPriceTo(request.getRentPriceTo())
                .setManagerName(request.getManagerName())
                .setManagerPhone(request.getManagerPhoneNumber())
                .setStaffId(request.getStaffId())
                .setTypeCodes(request.getTypeCodes())
                .build();
    }

    public BuildingSearchResponse toResponse(Building building) {
        BuildingSearchResponse response = new BuildingSearchResponse();
        response.setId(building.getId());
        response.setName(building.getName());
        response.setAddress(buildAddress(building));
        response.setNumberOfBasement(
                building.getNumberOfBasement() != null ? String.valueOf(building.getNumberOfBasement()) : null
        );
        response.setManagerName(building.getManagerName());
        response.setManagerPhoneNumber(building.getManagerPhoneNumber());
        response.setFloorArea(building.getFloorArea());
        response.setRentPrice(building.getRentPrice());
        response.setServiceFee(building.getServiceFee());
        response.setBrokerageFee(building.getBrokerageFee());

        List<RentArea> rentAreas = rentAreaRepository.findByBuildingId(building.getId());
        response.setRentArea(
                rentAreas.stream()
                        .map(item -> String.valueOf(item.getValue()))
                        .collect(Collectors.joining(","))
        );

        long totalRentArea = rentAreas.stream()
                .mapToLong(item -> item.getValue() != null ? item.getValue() : 0L)
                .sum();

        long floorArea = building.getFloorArea() != null ? building.getFloorArea() : 0L;
        long emptyArea = floorArea - totalRentArea;
        response.setEmptyArea(Math.max(emptyArea, 0L));

        return response;
    }

    private String buildAddress(Building building) {
        StringBuilder address = new StringBuilder();

        if (building.getStreet() != null && !building.getStreet().isBlank()) {
            address.append(building.getStreet());
        }

        if (building.getWard() != null && !building.getWard().isBlank()) {
            if (!address.isEmpty()) {
                address.append(", ");
            }
            address.append(building.getWard());
        }

        if (building.getDistrict() != null && !building.getDistrict().isBlank()) {
            if (!address.isEmpty()) {
                address.append(", ");
            }
            try {
                address.append(District.valueOf(building.getDistrict()).getDistrictName());
            } catch (IllegalArgumentException e) {
                address.append(building.getDistrict());
            }
        }

        return address.toString();
    }
}