package com.devon.building.api.web;

import com.devon.building.constant.SystemConstant;
import com.devon.building.entity.User;
import com.devon.building.model.request.RegisterRequest;
import com.devon.building.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthAPI {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (request.getUsername() == null || request.getUsername().trim().length() < 3) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username phải có ít nhất 3 ký tự"));
        }

        if (request.getPassword() == null || request.getPassword().length() < 6) {
            return ResponseEntity.badRequest().body(Map.of("message", "Password phải có ít nhất 6 ký tự"));
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Mật khẩu xác nhận không khớp"));
        }

        if (userRepository.existsByUserName(request.getUsername())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username đã tồn tại"));
        }

        User user = new User();
        user.setUserName(request.getUsername().trim());
        user.setFullName(request.getFullName());
        user.setEncrytedPassword(passwordEncoder.encode(request.getPassword()));
        user.setActive(true);
        user.setUserRole(SystemConstant.USER_ROLE);
        user.setPhone(request.getPhone() == null || request.getPhone().isBlank() ? "0000000000" : request.getPhone());

        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Đăng ký thành công"));
    }
}
