package com.workflow.service.impl;

import com.workflow.dto.TeamResponse;
import com.workflow.model.Permission;
import com.workflow.model.Permission_Team;
import com.workflow.model.Teams;
import com.workflow.repository.IPermissionTeamRepo;
import com.workflow.repository.ITeamRepo;
import com.workflow.service.ITeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements ITeamService {
    private final IPermissionTeamRepo permissionTeamRepo;
    private final AccountServiceImpl accountService;

    @Autowired
    ITeamRepo teamRepo;

    public List<TeamResponse> getAll() {
        String username = accountService.getCurrentUsername();
        List<Permission_Team> listPermission = permissionTeamRepo.findAllByAccount_Username(username);
        List<Teams> teams = listPermission.stream().map(Permission_Team::getTeams).collect(Collectors.toList());
        return teams.stream().
                map(t -> new TeamResponse(t.getId(), t.getName(), findAccountByTeam(t))).collect(Collectors.toList());
    }

    private List<String> findAccountByTeam(Teams teams) {
        List<Permission_Team> permissionTeamList = permissionTeamRepo.findAllByTeams(teams);
        return permissionTeamList.stream().map(p -> p.getAccount().getName()).collect(Collectors.toList());
    }

    @Override
    public List<Teams> findAll() {

        return null;
    }

    @Override
    public void save(Teams teams) {

        Permission_Team permission_team = new Permission_Team();

        Teams teamCreate = teamRepo.save(teams);
        permission_team.setTeams(teamCreate);

        permission_team.setAccount(accountService.getCurrentAccount());

        Permission permission = new Permission(1, "Admin");

        permission_team.setPermission(permission);
        permissionTeamRepo.save(permission_team);
    }

    @Override
    public void edit(Teams teams) {

    }

    @Override
    public void delete(int id) {
        Teams teams = teamRepo.findById(id).get();
        List<Permission_Team> permissionTeamList = permissionTeamRepo.findAllByTeams(teams);
        permissionTeamRepo.deleteAll(permissionTeamList);

        teamRepo.deleteById(id);
    }

    @Override
    public Teams findById(int id) {
        Teams teams = teamRepo.findById(id).get();
        return teams;
    }
}
