package com.devon.building.api.admin;

import com.devon.building.model.dto.PasswordDTO;
import com.devon.building.model.dto.ResponseDTO;
import com.devon.building.model.dto.UserDTO;
import com.devon.building.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAPI {

    UserService userService;

    @PostMapping
    public ResponseEntity<ResponseDTO> createUser(@Valid @ModelAttribute UserDTO user, BindingResult bindingResult) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());

                responseDTO.setMessage("Validation failed");
                responseDTO.setDetail(errorMessages);
                return ResponseEntity.badRequest().body(responseDTO);
            }
            userService.save(user);
            responseDTO.setMessage("Create Successfully");
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());

                responseDTO.setMessage("Validation failed");
                responseDTO.setDetail(errorMessages);
                return ResponseEntity.badRequest().body(responseDTO);
            }
            userService.update(userDTO);
            responseDTO.setMessage("Update Successfully");
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO> deleteUsers(@RequestBody List<Long> idList) {
        ResponseDTO responseDTO = new ResponseDTO();
        if (!idList.isEmpty()) {
            userService.delete(idList);
        }
        responseDTO.setMessage("Delete Successfully");
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<ResponseDTO> updateUser(@PathVariable Long id, @RequestBody PasswordDTO passwordDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        return ResponseEntity.ok().body(responseDTO);
    }
}
