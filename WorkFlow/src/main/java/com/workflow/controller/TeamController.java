package com.workflow.controller;

import com.workflow.dto.TeamResponse;
import com.workflow.service.impl.TeamServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamServiceImpl teamService;

    @GetMapping
    public List<TeamResponse> getAllTeam(){
        return teamService.getAll();
    }

}
