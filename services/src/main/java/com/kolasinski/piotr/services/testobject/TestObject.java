package com.kolasinski.piotr.services.testobject;

import com.fasterxml.jackson.annotation.JsonView;
import com.kolasinski.piotr.services.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_test_object")
public class TestObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_object_id")
    @JsonView(TestObjectViews.Public.class)
    private long id;

    @Column(name = "test_object_name")
    @JsonView(TestObjectViews.Public.class)
    private String name;

    @Column(name = "test_object_type")
    @JsonView(TestObjectViews.Public.class)
    private TestObjectType type;

    @Column(name = "test_object_date")
    @JsonView(TestObjectViews.Public.class)
    private Instant date;

    @OneToOne
    @JoinColumn(name = "test_object_user_id")
    @JsonView(TestObjectViews.Public.class)
    private User user;
}
