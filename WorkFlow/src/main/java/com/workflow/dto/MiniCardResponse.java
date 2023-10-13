package com.workflow.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class MiniCardResponse {
    private int id;
    private String title;
    private Date dueDate;
}
