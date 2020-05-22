package com.service.rest.users;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    private String phone;

}
