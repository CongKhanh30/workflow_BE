package com.workflow.controller;

import com.workflow.dto.TeamResponse;
import com.workflow.model.Teams;
import com.workflow.service.impl.TeamServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public ResponseEntity<String> createTeam(@RequestBody Teams teams){
        teamService.save(teams);


        return ResponseEntity.ok("Team created");
    }
}
