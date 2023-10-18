package com.workflow.dto;

import lombok.Getter;

@Getter
public class RenameColRequest {
    private String newName;
    private int colId;
}
