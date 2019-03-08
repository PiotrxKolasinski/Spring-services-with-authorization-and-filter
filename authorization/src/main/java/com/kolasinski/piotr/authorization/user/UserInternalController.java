package com.kolasinski.piotr.authorization.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.kolasinski.piotr.authorization.role.RoleViews;
import com.kolasinski.piotr.authorization.user.role.UserRoleViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("internal/users")
public class UserInternalController {
    private final UserService userService;

    interface UserInternalControllerViews extends UserRoleViews.Public, UserViews.Public, RoleViews.Public {}

    @Autowired
    public UserInternalController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @JsonView(UserInternalControllerViews.class)
    public ResponseEntity findOneByEmail(@RequestParam String email) {
        return new ResponseEntity<>(userService.findOneByEmail(email), HttpStatus.OK);
    }
}