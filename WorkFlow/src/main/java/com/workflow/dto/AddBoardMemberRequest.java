package com.workflow.dto;

import lombok.Getter;

@Getter
public class AddBoardMemberRequest {
    private String username;
    private int boardId;
    private int permissionId;
}
