package com.kolasinski.piotr.services.role;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    @JsonView(RoleViews.Public.class)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type")
    @JsonView(RoleViews.Public.class)
    private RoleType type;
}
