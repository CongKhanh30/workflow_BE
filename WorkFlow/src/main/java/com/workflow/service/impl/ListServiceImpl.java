package com.workflow.service.impl;

import com.workflow.repository.IListRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ListServiceImpl {
    private final IListRepo listRepo;
    private final PermissionTeamServiceImpl permissionTeamService;
    private final AccountServiceImpl accountService;

    public boolean isMember(int teamId){
        return (permissionTeamService.adminCheck(accountService.getCurrentUsername(), teamId)
        || permissionTeamService.memberCheck(accountService.getCurrentUsername(), teamId));
    }

    public
}
