package com.workflow.dto;

import com.workflow.model.Col;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponse {
    private int id;
    private String name;
    private Boolean isPrivate;
    private List<ColResponse> cols;

}
