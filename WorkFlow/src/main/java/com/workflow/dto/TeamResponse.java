package com.workflow.dto;

import com.workflow.model.Account;
import com.workflow.model.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponse {
    private int id;
    private String name;
    private List<String> members;
    private Permission permission;
}
