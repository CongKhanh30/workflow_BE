package com.workflow.service.impl;

import com.workflow.dto.AddMemberRequest;
import com.workflow.model.Permission_Team;
import com.workflow.repository.IAccountRepo;
import com.workflow.repository.IPermissionRepo;
import com.workflow.repository.IPermissionTeamRepo;
import com.workflow.repository.ITeamRepo;
import com.workflow.service.IPermissionTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionTeamServiceImpl implements IPermissionTeamService {
    @Autowired
    private final IPermissionTeamRepo permissionTeamRepo;
    private final ITeamRepo teamRepo;
    private final IAccountRepo accountRepo;
    private final IPermissionRepo permissionRepo;
    private final AccountServiceImpl accountService;
    private final int ROLE_ADMIN_ID = 1;
    private final int ROLE_MEMBER_ID = 2;

    public List<Permission_Team> getByUsername(String username){
        return permissionTeamRepo.findAllByAccount_Username(username);
    }
    public void addMember(AddMemberRequest addMemberRequest){
        permissionTeamRepo.save(builderAdd(addMemberRequest));
    }
    private Permission_Team builderAdd(AddMemberRequest addMemberRequest){
        Permission_Team permissionTeam = new Permission_Team();
        permissionTeam.setTeams(teamRepo.findById(addMemberRequest.getTeamId()).get());
        permissionTeam.setAccount(accountRepo.findByUsername(addMemberRequest.getUsername()));
        permissionTeam.setPermission(permissionRepo.findById(addMemberRequest.getPermissionId()).get());
        return permissionTeam;
    }
    public boolean adminCheck(String username, int teamId){
        return (permissionTeamRepo.findByAccount_UsernameAndTeamsId(username,teamId) != null
                && (permissionTeamRepo.findByAccount_UsernameAndTeamsId(username,teamId).getPermission().getId() == ROLE_ADMIN_ID));

    }
    public boolean memberCheck(String username, int teamId){
        return permissionTeamRepo.findByAccount_UsernameAndTeamsId(username, teamId) != null
                && (permissionTeamRepo.findByAccount_UsernameAndTeamsId(username, teamId).getPermission().getId() == ROLE_MEMBER_ID);
    }
    public boolean isMember(int teamId){
        return (adminCheck(accountService.getCurrentUsername(), teamId)
                ||memberCheck(accountService.getCurrentUsername(), teamId));
    }
}
