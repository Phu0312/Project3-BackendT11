package com.devon.building.converter;

import com.devon.building.entity.Building;
import com.devon.building.model.request.BuildingCreateRequest;
import com.devon.building.model.request.BuildingUpdateRequest;
import com.devon.building.model.response.BuildingResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BuildingMapper {
    Building toEntity(BuildingCreateRequest request);

    BuildingResponse toResponse(Building entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Building entity, BuildingUpdateRequest request);
}
