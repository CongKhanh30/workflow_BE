package com.workflow.dto;

import lombok.*;

@Setter
@Getter
public class AddTeamMemberRequest {
    private int teamId;
    private String username;
    private int permissionId;

}
