package com.msa.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class UserInternalController {
    private final UserService service;

    @PatchMapping("{id}")
    ResponseEntity<UserDTO> addPoint(@PathVariable Long id, @RequestBody Integer stocks) {
        return ResponseEntity.ok(service.addPoint(id, stocks));
    }
}