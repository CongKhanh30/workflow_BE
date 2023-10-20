package com.workflow.dto;

import com.workflow.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MiniAccount {
    private int id;
    private String username;
    private String name;
    public static MiniAccount builder(Account account){
        return new MiniAccount(account.getId(), account.getUsername(), account.getName());
    }
}
