package com.kolasinski.piotr.filter.role;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @JsonView(RoleViews.Public.class)
    private long id;

    @JsonView(RoleViews.Public.class)
    private RoleType type;
}
