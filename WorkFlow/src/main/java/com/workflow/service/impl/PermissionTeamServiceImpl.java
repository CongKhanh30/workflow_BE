package com.workflow.service.impl;

import com.workflow.dto.AddTeamMemberRequest;
import com.workflow.dto.KickTeamMemberReq;
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

    public void addMember(AddTeamMemberRequest addTeamMemberRequest){
        permissionTeamRepo.save(builderAdd(addTeamMemberRequest));
    }
    private PermissionTeam builderAdd(AddTeamMemberRequest addTeamMemberRequest){
        PermissionTeam permissionTeam = new PermissionTeam();
        permissionTeam.setTeams(teamRepo.findById(addTeamMemberRequest.getTeamId()).get());
        permissionTeam.setAccount(accountRepo.findByUsername(addTeamMemberRequest.getUsername()));
        permissionTeam.setPermission(permissionRepo.findById(addTeamMemberRequest.getPermissionId()).get());
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

    public void kickMember(KickTeamMemberReq kickTeamMemberReq) {
        permissionTeamRepo.delete(permissionTeamRepo.findByAccount_UsernameAndTeamsId(
                kickTeamMemberReq.getUsername(), kickTeamMemberReq.getTeamId()));
    }
}
