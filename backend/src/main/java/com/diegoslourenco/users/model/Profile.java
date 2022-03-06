package com.diegoslourenco.users.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Profile --- represents a profile for an user.
 * @author    Diego da Silva Lourenco
 */

@Entity
@Table(name = "TB_PROFILE")
@Getter
@Setter
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;
}
