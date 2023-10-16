package com.workflow.dto;

import lombok.*;

@Setter
@Getter
public class AddMemberRequest {
    private int teamId;
    private String username;
    private int permissionId;

}
