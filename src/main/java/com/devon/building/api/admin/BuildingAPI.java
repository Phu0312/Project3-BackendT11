package com.devon.building.api.admin;

import com.devon.building.constant.SystemConstant;
import com.devon.building.entity.AssignmentBuilding;
import com.devon.building.entity.User;
import com.devon.building.model.dto.ResponseDTO;
import com.devon.building.model.request.AssignmentBuildingRequest;
import com.devon.building.model.request.BuildingCreateRequest;
import com.devon.building.model.request.BuildingUpdateRequest;
import com.devon.building.service.BuildingService;
import com.devon.building.service.UserService;
import com.devon.building.utils.SecurityUtils;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buildings")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BuildingAPI {

    BuildingService buildingService;
    UserService userService;

    @PostMapping
    public ResponseEntity<Object> addBuilding(@RequestBody @Valid BuildingCreateRequest buildingCreateRequest, BindingResult bindingResult) {
        ResponseDTO responseDTO = new ResponseDTO();
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            responseDTO.setMessage("Failed to add building");
            responseDTO.setDetail(errorMessages);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }
        buildingService.create(buildingCreateRequest);
        responseDTO.setMessage("Successfully add building");
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBuilding(@PathVariable Long id,
                                                 @RequestBody @Valid BuildingUpdateRequest request,
                                                 BindingResult bindingResult) {
        ResponseDTO responseDTO = new ResponseDTO();
        if (SecurityUtils.getAuthorities().contains(SystemConstant.STAFF_ROLE)) {
            User staff = userService.getUserByUsername(
                    SecurityUtils.getCurrentUser().getUsername());

            boolean isAssigned = staff.getAssignmentBuildings().stream()
                    .map(AssignmentBuilding::getBuilding)
                    .anyMatch(building -> building.getId().equals(id));

            if (!isAssigned) {
                List<String> errorMessages = List.of("Page Not Found");
                responseDTO.setMessage("Failed to update building");
                responseDTO.setDetail(errorMessages);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
            }
        }
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            responseDTO.setMessage("Failed to update building");
            responseDTO.setDetail(errorMessages);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }
        buildingService.update(id, request);
        responseDTO.setMessage("Update building successfully");
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping("/{ids}")
    public ResponseEntity<Object> deleteBuilding(@PathVariable List<Long> ids) {
        ResponseDTO responseDTO = new ResponseDTO();
        if (ids.isEmpty()) {
            responseDTO.setMessage("Failed to delete building");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        } else {
            buildingService.delete(ids);
            responseDTO.setMessage("Successfully delete building");
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        }
    }

    @GetMapping("/{buildingId}/staff")
    public ResponseEntity<Object> loadStaffs(@PathVariable Long buildingId) {
        return ResponseEntity.ok(buildingService.loadStaffs(buildingId));
    }

    @PostMapping("/assign")
    public ResponseEntity<Object> assignBuilding(@RequestBody AssignmentBuildingRequest assignmentBuildingRequest) {
        ResponseDTO responseDTO = new ResponseDTO();
        buildingService.assignBuilding(assignmentBuildingRequest);
        responseDTO.setMessage("Successfully assign building");
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }


}
