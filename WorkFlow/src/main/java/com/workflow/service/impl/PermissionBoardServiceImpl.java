package com.workflow.service.impl;

import com.workflow.dto.AddBoardMemberRequest;
import com.workflow.model.Account;
import com.workflow.model.Board;
import com.workflow.model.Permission;
import com.workflow.model.PermissionBoard;
import com.workflow.repository.IAccountRepo;
import com.workflow.repository.IBoardRepo;
import com.workflow.repository.IPermissionBoardRepo;
import com.workflow.repository.IPermissionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionBoardServiceImpl {
    private final IPermissionBoardRepo permissionBoardRepo;
    private final IAccountRepo accountRepo;
    private final IBoardRepo boardRepo;
    private final IPermissionRepo permissionRepo;


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

    public boolean addMember(AddBoardMemberRequest addBoardMemberRequest){
        PermissionBoard permissionBoard = builderAdd(addBoardMemberRequest);
        if (permissionBoard != null){
            permissionBoardRepo.save(permissionBoard);
            return true;
        }
        return false;
    }
    private PermissionBoard builderAdd(AddBoardMemberRequest addBoardMemberRequest){
        Account account = accountRepo.findByUsername(addBoardMemberRequest.getUsername());
        Optional<Board> boardOtp = boardRepo.findById(addBoardMemberRequest.getBoardId());
        Optional<Permission> permissionOtp = permissionRepo.findById(addBoardMemberRequest.getPermissionId());
        if(account!=null && boardOtp != null && permissionOtp != null){
            return new PermissionBoard(boardOtp.get(), permissionOtp.get(),account);
        }
        return null;
    }
}
