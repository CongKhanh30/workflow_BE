package com.workflow.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "account_role")
public class AccountRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Account account;
    @ManyToOne
    private Role role;
}
