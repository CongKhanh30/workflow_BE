package com.workflow.dto;

import lombok.Data;

import java.util.Set;

@Data
public class TeamDetailResponse {
    private String name;
    private int id;
    private Set<TeamMemberResponse> members;
}
