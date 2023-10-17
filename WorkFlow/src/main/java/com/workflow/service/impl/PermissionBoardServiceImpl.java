package com.workflow.service.impl;

import com.workflow.model.Account;
import com.workflow.model.PermissionBoard;
import com.workflow.repository.IPermissionBoardRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionBoardServiceImpl {
    private final IPermissionBoardRepo permissionBoardRepo;

    public boolean adminCheck(String username, int boardId){
        final int adminPermissionId = 1;
        PermissionBoard permissionBoard = permissionBoardRepo.findByAccount_UsernameAndBoard_Id(username,boardId);
        return (permissionBoard.getPermission().getId() == adminPermissionId);
    }
    public boolean memberCheck(String username, int boardId){
        final int memberPermissionId = 2;
        PermissionBoard permissionBoard = permissionBoardRepo.findByAccount_UsernameAndBoard_Id(username,boardId);
        return (permissionBoard.getPermission().getId() == memberPermissionId);
    }

    public boolean isMember(String username, int boardId){
        return (memberCheck(username, boardId)||adminCheck(username, boardId));
    }
}
