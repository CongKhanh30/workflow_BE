package com.workflow.service.impl;

import com.workflow.dto.BoardResponse;
import com.workflow.dto.ColResponse;
import com.workflow.model.Board;
import com.workflow.model.Permission;
import com.workflow.model.PermissionBoard;
import com.workflow.repository.IBoardRepo;
import com.workflow.repository.IPermissionBoardRepo;
import com.workflow.service.IBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements IBoardService {

    private final ColServiceImpl colService;
    private final IBoardRepo boardRepo;
    private final AccountServiceImpl accountService;
    private final IPermissionBoardRepo permissionBoardRepo;

    public List<BoardResponse> findAllByTeam(int id) {
        List<Board> boardList =  boardRepo.findAllByTeam(id);
        List<BoardResponse> boardResponses = new ArrayList<>();
        for (Board board : boardList) {
            List<ColResponse> cols = colService.getCol(board.getId());
            boardResponses.add(new BoardResponse(
                    board.getId(),
                    board.getName(),
                    board.getIsPublic(),
                    cols
            ));
        }
        return boardResponses;
    }

    @Override
    public List<Board> findAll() {
        List<Board> boardList = (List<Board>) boardRepo.findAll();
        return boardList;
    }

    @Override
    public void save(Board board) {
        Board boardCreated = boardRepo.save(board);
        PermissionBoard permissionBoard = new PermissionBoard();
        permissionBoard.setBoard(boardCreated);
        permissionBoard.setAccount(accountService.getCurrentAccount());
        permissionBoard.setPermission(new Permission(1,"admin"));
        permissionBoardRepo.save(permissionBoard);
    }

    @Override
    public void edit(Board board) {
        boardRepo.save(board);
    }

    @Override
    public void delete(int id) {
//        permissionBoardRepo.deleteAllByBoard_Id(id);
        List<PermissionBoard> permissionBoards = permissionBoardRepo.findAllByBoard_Id(id);
        permissionBoards.forEach(pb -> permissionBoardRepo.delete(pb));
        boardRepo.deleteById(id);
    }

    @Override
    public Board findByTeamId(int idBoard) {
        return boardRepo.findById(idBoard).orElse(null);
    }
}
