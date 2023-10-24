package com.workflow.dto;

import com.workflow.model.Col;
import lombok.Getter;

import java.util.Date;

@Getter
public class EditCardReq {
    private int id;
    private String title;
    private String description;
    private Date dueDate;
    private int colId;
}
