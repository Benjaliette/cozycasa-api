package me.cozycosa.api.homes.controllers;

import lombok.RequiredArgsConstructor;
import me.cozycosa.api.homes.DTO.HomeDto;
import me.cozycosa.api.homes.services.HomeService;
import me.cozycosa.api.users.entities.UserEntity;
import me.cozycosa.api.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/homes")
public class HomeController {
    @Autowired
    private HomeService homeService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<HomeDto>> getAllHomes() {
        return new ResponseEntity<>(homeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HomeDto> getHomeById(@PathVariable Long id) {
        return new ResponseEntity<>(homeService.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<HomeDto> createHome(@RequestBody HomeDto home, Principal principal) {
        UserEntity currentUser = userService.getUserBy(principal.getName());
        return new ResponseEntity<>(homeService.create(home, currentUser), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HomeDto> updateHome(@RequestBody HomeDto home, @PathVariable Long id) {
        return new ResponseEntity<>(homeService.update(id, home), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHome(@PathVariable Long id) {
        return new ResponseEntity<>(homeService.delete(id), HttpStatus.OK);
    }
}
