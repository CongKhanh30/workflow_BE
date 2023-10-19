package com.workflow.service.impl;

import com.workflow.dto.TeamDetailResponse;
import com.workflow.dto.TeamMemberResponse;
import com.workflow.dto.TeamResponse;
import com.workflow.model.Permission;
import com.workflow.model.PermissionTeam;
import com.workflow.model.Teams;
import com.workflow.repository.IPermissionTeamRepo;
import com.workflow.repository.ITeamRepo;
import com.workflow.service.ITeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements ITeamService {
    private final IPermissionTeamRepo permissionTeamRepo;
    private final AccountServiceImpl accountService;

    private final ITeamRepo teamRepo;
    private final PermissionTeamServiceImpl permissionTeamService;

    public List<TeamResponse> getAll(){
        String username = accountService.getCurrentUsername();
        List<PermissionTeam> listPermission = permissionTeamRepo.findAllByAccount_Username(username);
        List<Teams> teams = listPermission.stream().map(PermissionTeam::getTeams).collect(Collectors.toList());
        return teams.stream().
                map(t->new TeamResponse(t.getId(), t.getName(), findAccountByTeam(t),getCurrentPermission(t)))
                .collect(Collectors.toList());
    }
    private List<String> findAccountByTeam(Teams teams){
        List<PermissionTeam> permissionTeamList = permissionTeamRepo.findAllByTeams(teams);
        return permissionTeamList.stream().map(p->p.getAccount().getName()).collect(Collectors.toList());
    }
    private Permission getCurrentPermission(Teams teams){
        PermissionTeam permissionTeam = permissionTeamRepo.findByAccount_UsernameAndTeamsId(
                accountService.getCurrentUsername(), teams.getId());
        return permissionTeam.getPermission();
    }

    @Override
    public List<Teams> findAll() {

        return teamRepo.findAll();
    }

    @Override
    public void save(Teams teams) {
        PermissionTeam permissionteam = new PermissionTeam();
        Teams teamCreate = teamRepo.save(teams);
        permissionteam.setTeams(teamCreate);
        permissionteam.setAccount(accountService.getCurrentAccount());
        Permission permission = new Permission(1, "Admin");
        permissionteam.setPermission(permission);
        permissionTeamRepo.save(permissionteam);
    }

    @Override
    public void edit(Teams teams) {

    }

    @Override
    public void delete(int id) {
        Teams teams = teamRepo.findById(id).get();
        List<PermissionTeam> permissionTeamList = permissionTeamRepo.findAllByTeams(teams);
        permissionTeamRepo.deleteAll(permissionTeamList);
        teamRepo.deleteById(id);
    }

    @Override
    public Teams findByTeamId(int id) {
        return teamRepo.findById(id).orElse(null);
    }

    public boolean changeName(String name, int teamId){
        Optional<Teams> teamOtp = teamRepo.findById(teamId);
            if (teamOtp.isPresent() && permissionTeamService.adminCheck(accountService.getCurrentUsername(), teamId)) {
                teamOtp.get().setName(name);
                teamRepo.save(teamOtp.get());
                return true;
            }
        return false;
    }

    public TeamDetailResponse findById(int id){
        Optional<Teams> teamsOtp = teamRepo.findById(id);
        if (teamsOtp.isPresent()
                && permissionTeamRepo.findByAccount_UsernameAndTeamsId(accountService.getCurrentUsername(), id)!= null){
            Teams teams = teamsOtp.get();
            TeamDetailResponse teamDetailResponse = new TeamDetailResponse();
            teamDetailResponse.setName(teams.getName());
            List<PermissionTeam> permissionTeamList = permissionTeamRepo.findAllByTeams(teams);
            teamDetailResponse.setMembers(permissionTeamList.stream().map(this::buildMember).collect(Collectors.toSet()));
            return teamDetailResponse;
        }
        return null;
    }
    private TeamMemberResponse buildMember(PermissionTeam pt){
        TeamMemberResponse teamMemberResponse = new TeamMemberResponse();
        teamMemberResponse.setId(pt.getAccount().getId());
        teamMemberResponse.setName(pt.getAccount().getName());
        teamMemberResponse.setPermission(pt.getPermission().getName());
        return teamMemberResponse;
    }
}
