package com.workflow.dto;

import lombok.Getter;

@Getter
public class RenameListRequest {
    private String newName;
    private int listId;
}
