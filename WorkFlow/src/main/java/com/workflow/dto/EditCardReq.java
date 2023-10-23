package com.workflow.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class EditCardReq {
    private int cardId;
    private String title;
    private String description;
    private Date dueDate;
    private int colId;
}
