package com.workflow.service.impl;

import com.workflow.dto.BoardResponse;
import com.workflow.model.Board;
import com.workflow.repository.IAccountRepo;
import com.workflow.repository.IBoardRepo;
import com.workflow.service.IBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements IBoardService {

    @Autowired
    IBoardRepo boardRepo;

    @Autowired
    IAccountRepo accountRepo;

    @Autowired
    AccountServiceImpl accountService;

    public List<BoardResponse> findAllByTeam(int id) {
        List<Board> boardList =  boardRepo.findAllByTeam(id);
        List<BoardResponse> boardResponses = new ArrayList<>();
        for (Board board : boardList) {
            boardResponses.add(new BoardResponse(
                    board.getId(),
                    board.getName(),
                    board.getIs_Public()
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
        boardRepo.save(board);
    }

    @Override
    public void edit(Board board) {
        boardRepo.save(board);
    }

    @Override
    public void delete(int id) {
        boardRepo.deleteById(id);
    }

    @Override
    public Board findById(int idBoard) {
        Board board = boardRepo.findById(idBoard).get();
        return board;
    }
}
