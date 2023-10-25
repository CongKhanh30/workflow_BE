package com.workflow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamMemberResponse {
    private int id;
    private String name;
    private String username;
    private String permission;
}
