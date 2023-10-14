package com.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ColResponse {
    private int id;
    private int position;
    private String name;
    private List<MiniCardResponse> cards;
}
