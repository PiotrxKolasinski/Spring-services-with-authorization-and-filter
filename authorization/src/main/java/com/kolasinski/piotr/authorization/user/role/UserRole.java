package com.kolasinski.piotr.authorization.user.role;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.kolasinski.piotr.authorization.role.Role;
import com.kolasinski.piotr.authorization.user.User;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    @JsonView(UserRoleViews.Public.class)
    private long id;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_role_role_id")
    @JsonView(UserRoleViews.Public.class)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "user_role_user_id")
    @JsonBackReference
    @JsonView(UserRoleViews.Public.class)
    private User user;
}