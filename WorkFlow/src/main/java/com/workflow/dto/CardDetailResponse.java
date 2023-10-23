package com.workflow.dto;

import com.workflow.model.Col;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDetailResponse {
    private int id;
    private String title;
    private String description;
    private Date dueDate;
    private Col col;
    private List<MiniAccount> accounts;
}
