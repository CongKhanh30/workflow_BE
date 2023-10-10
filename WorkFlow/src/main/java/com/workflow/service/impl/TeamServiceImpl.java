package com.workflow.service.impl;

import com.workflow.dto.TeamResponse;
import com.workflow.model.Permission_Team;
import com.workflow.model.Teams;
import com.workflow.repository.IPermissionTeamRepo;
import com.workflow.service.ITeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements ITeamService {
    private final IPermissionTeamRepo permissionTeamRepo;
    private final AccountServiceImpl accountService;

    public List<TeamResponse> getAll(){
        String username = accountService.getCurrentUsername();
        List<Permission_Team> listPermission = permissionTeamRepo.findAllByAccount_Username(username);
        List<Teams> teams = listPermission.stream().map(Permission_Team::getTeams).collect(Collectors.toList());
        return teams.stream().
                map(t->new TeamResponse(t.getId(), t.getName(), findAccountByTeam(t)))
                .collect(Collectors.toList());
    }
    private List<String> findAccountByTeam(Teams teams){
        List<Permission_Team> permissionTeamList = permissionTeamRepo.findAllByTeams(teams);
        return permissionTeamList.stream()
                .map(p->p.getAccount().getName())
                .collect(Collectors.toList());
    }

}
