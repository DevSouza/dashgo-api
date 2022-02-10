package br.com.dashgo.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dashgo.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UsersController {

	private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('users.list')")
    public ResponseEntity<?> listUsers(@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('users.list')")
    public ResponseEntity<?> listUsers(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userService.findByUserIdOrThrowBadRequestException(userId));
    }

}
