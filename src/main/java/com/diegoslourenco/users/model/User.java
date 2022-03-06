package com.diegoslourenco.users.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Profile --- represents a user.
 * @author    Diego da Silva Lourenco
 */

@Entity
@Table(name = "TB_USER")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;
}
