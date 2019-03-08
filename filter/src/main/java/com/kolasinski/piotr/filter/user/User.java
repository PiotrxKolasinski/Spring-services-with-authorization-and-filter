package com.kolasinski.piotr.filter.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.kolasinski.piotr.filter.user.role.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @JsonView(UserViews.Public.class)
    private long id;

    @JsonView(UserViews.Public.class)
    private String email;

    @JsonView(UserViews.Internal.class)
    private String password;

    @JsonView(UserViews.Internal.class)
    private boolean active;

    @JsonView(UserViews.Internal.class)
    private Instant createDate;

    @JsonView(UserViews.Public.class)
    private List<UserRole> userRoles;
}