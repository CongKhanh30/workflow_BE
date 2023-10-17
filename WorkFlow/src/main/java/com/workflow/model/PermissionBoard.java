package com.workflow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Board board;

    @ManyToOne
    private Permission permission;

    @ManyToOne
    private Account account;

    public PermissionBoard(Board board, Permission permission, Account account){
        this.board = board;
        this.permission = permission;
        this.account = account;
    }
}
