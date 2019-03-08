package com.kolasinski.piotr.filter.user.role;

import com.fasterxml.jackson.annotation.JsonView;
import com.kolasinski.piotr.filter.role.Role;
import com.kolasinski.piotr.filter.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole {

    @JsonView(UserRoleViews.Public.class)
    private long id;

    @JsonView(UserRoleViews.Public.class)
    private Role role;

    @JsonView(UserRoleViews.Public.class)
    private User user;
}