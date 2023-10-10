package com.workflow.controller;

import com.workflow.dto.AddMemberRequest;
import com.workflow.dto.TeamResponse;
import com.workflow.model.Teams;
import com.workflow.repository.IAccountRepo;
import com.workflow.service.impl.AccountServiceImpl;
import com.workflow.service.impl.PermissionTeamServiceImpl;
import com.workflow.service.impl.TeamServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamServiceImpl teamService;
    private final PermissionTeamServiceImpl permissionTeamService;
    private final IAccountRepo accountRepo;
    private final AccountServiceImpl accountService;

    @GetMapping
    public List<TeamResponse> getAllTeam(){
        return teamService.getAll();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTeam(@RequestBody Teams teams){
        teamService.save(teams);
        return ResponseEntity.ok("Team created");
    }
    @PostMapping("/add")
    public ResponseEntity<String> addMember(@RequestBody AddMemberRequest addMemberRequest){
        if (permissionTeamService.adminCheck(accountService.getCurrentUsername(), addMemberRequest.getTeamId()))
        {if(accountRepo.findByUsername(addMemberRequest.getUsername()) == null ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username not found");
        }
        permissionTeamService.addMember(addMemberRequest);
        return ResponseEntity.ok("succeed");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Don't have permission");
    }
}
