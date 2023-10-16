package com.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class MiniCardResponse {
    private int id;
    private String title;
    private Date dueDate;
}
