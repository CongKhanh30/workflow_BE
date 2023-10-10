package com.workflow.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddMemberRequest {
    private int teamId;
    private String username;
    private int permissionId;

}
