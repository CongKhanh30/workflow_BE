package com.workflow.dto;

import lombok.Getter;

@Getter
public class KickTeamMemberReq {
    private String username;
    private int teamId;
}
