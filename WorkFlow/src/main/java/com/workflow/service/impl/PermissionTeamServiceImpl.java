package com.workflow.service.impl;

import com.workflow.dto.AddMemberRequest;
import com.workflow.model.PermissionTeam;
import com.workflow.repository.IAccountRepo;
import com.workflow.repository.IPermissionRepo;
import com.workflow.repository.IPermissionTeamRepo;
import com.workflow.repository.ITeamRepo;
import com.workflow.service.IPermissionTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionTeamServiceImpl implements IPermissionTeamService {
    private final IPermissionTeamRepo permissionTeamRepo;
    private final ITeamRepo teamRepo;
    private final IAccountRepo accountRepo;
    private final IPermissionRepo permissionRepo;

    public List<PermissionTeam> getByUsername(String username){
        return permissionTeamRepo.findAllByAccount_Username(username);
    }

    public void addMember(AddMemberRequest addMemberRequest){
        permissionTeamRepo.save(builderAdd(addMemberRequest));
    }
    private PermissionTeam builderAdd(AddMemberRequest addMemberRequest){
        PermissionTeam permissionTeam = new PermissionTeam();
        permissionTeam.setTeams(teamRepo.findById(addMemberRequest.getTeamId()).get());
        permissionTeam.setAccount(accountRepo.findByUsername(addMemberRequest.getUsername()));
        permissionTeam.setPermission(permissionRepo.findById(addMemberRequest.getPermissionId()).get());
        return permissionTeam;
    }
    public boolean adminCheck(String username, int teamId){
        int ROLE_ADMIN_ID = 1;
        return (permissionTeamRepo.findByAccount_UsernameAndTeamsId(username,teamId) != null
                && (permissionTeamRepo.findByAccount_UsernameAndTeamsId(username,teamId).getPermission().getId() == ROLE_ADMIN_ID));

    }
    public boolean memberCheck(String username, int teamId){
        int ROLE_MEMBER_ID = 2;
        return permissionTeamRepo.findByAccount_UsernameAndTeamsId(username, teamId) != null
                && (permissionTeamRepo.findByAccount_UsernameAndTeamsId(username, teamId).getPermission().getId() == ROLE_MEMBER_ID);
    }
    public boolean isMember(String username,int teamId){
        return (adminCheck(username, teamId)
                ||memberCheck(username, teamId));
    }
}
