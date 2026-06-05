package com.devon.building.controller.admin.building;

import com.devon.building.constant.SystemConstant;
import com.devon.building.entity.AssignmentBuilding;
import com.devon.building.entity.User;
import com.devon.building.enums.District;
import com.devon.building.enums.RentType;
import com.devon.building.model.dto.BuildingDTO;
import com.devon.building.model.request.BuildingSearchRequest;
import com.devon.building.service.BuildingService;
import com.devon.building.service.UserService;
import com.devon.building.utils.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/buildings")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BuildingController {

    UserService userService;
    BuildingService buildingService;

    @GetMapping("/list")
    public String getList(@ModelAttribute BuildingSearchRequest buildingSearchRequest, Model model) {
        model.addAttribute("modelSearch", buildingSearchRequest);
        model.addAttribute("staffs", userService.loadStaff());
        model.addAttribute("district", District.getDistrict());
        model.addAttribute("typeCode", RentType.getTypeCode());
        if (SecurityUtils.getAuthorities().contains(SystemConstant.STAFF_ROLE)) {
            User staff = userService.getUserByUsername(SecurityUtils.getCurrentUser().getUsername());
            buildingSearchRequest.setStaffId(staff.getId());
        }
        model.addAttribute("results", buildingService.search(buildingSearchRequest));
        //project 1
        return "admin/building/buildingList";
    }

    @GetMapping("/edit")
    public String createBuilding(Model model) {
        model.addAttribute("district", District.getDistrict());
        model.addAttribute("typeCode", RentType.getTypeCode());
        model.addAttribute("building", new BuildingDTO());
        return "admin/building/buildingEdit";
    }

    @GetMapping("/{id}/update")
    public String updateBuilding(@PathVariable Long id, Model model) {
        //find by id trả về building dto
        if (SecurityUtils.getAuthorities().contains(SystemConstant.STAFF_ROLE)) {
            User staff = userService.getUserByUsername(
                    SecurityUtils.getCurrentUser().getUsername()
            );

            boolean isAssigned = staff.getAssignmentBuildings().stream()
                    .map(AssignmentBuilding::getBuilding)
                    .anyMatch(building -> building.getId().equals(id));

            if (!isAssigned) {
                return "/404";
            }
        }
        model.addAttribute("district", District.getDistrict());
        model.addAttribute("typeCode", RentType.getTypeCode());
        model.addAttribute("building", buildingService.findById(id));
        return "admin/building/buildingEdit";
    }
}
