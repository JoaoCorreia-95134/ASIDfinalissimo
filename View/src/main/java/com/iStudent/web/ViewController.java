package com.iStudent.web;





import com.iStudent.model.entity.UsersWithLargeTown;
import com.iStudent.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/View")
public class ViewController {

    private final ViewService viewService;

    @Autowired

    public ViewController(ViewService viewService) {
        this.viewService = viewService;

    }

    @GetMapping
    public ResponseEntity<List<UsersWithLargeTown>> getView() {
        List<UsersWithLargeTown> users = viewService.getView();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UsersWithLargeTown> addClub(@Valid @RequestBody UsersWithLargeTown user,
                                                      UriComponentsBuilder uriComponentsBuilder) {

        UsersWithLargeTown userCreated = viewService.addUser(user);

        ResponseEntity<UsersWithLargeTown> responseEntity = ResponseEntity.ok(userCreated);

        return responseEntity;
    }


}