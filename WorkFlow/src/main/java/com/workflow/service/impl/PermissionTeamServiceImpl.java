package com.workflow.service.impl;

import com.workflow.model.Permission_Team;
import com.workflow.repository.IPermissionTeamRepo;
import com.workflow.service.IPermissionTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionTeamServiceImpl implements IPermissionTeamService {
    private final IPermissionTeamRepo permissionTeamRepo;

    public List<Permission_Team> getByUsername(String username){
        return permissionTeamRepo.findAllByAccount_Username(username);
    }

}
