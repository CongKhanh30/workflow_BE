package com.workflow.controller;

import com.workflow.dto.BoardResponse;
import com.workflow.model.Board;
import com.workflow.model.Teams;
import com.workflow.service.impl.BoardServiceImpl;
import com.workflow.service.impl.TeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/board")

public class BoardController {

    @Autowired
    private BoardServiceImpl boardService;

    @Autowired
    private TeamServiceImpl teamService;

    @GetMapping("/getAllByIdTeam/{idTeam}")
    public ResponseEntity<List<BoardResponse>> getAllBoard(@PathVariable int idTeam) {

        List<BoardResponse> boardList = boardService.findAllByTeam(idTeam);
        if (boardList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Board>> getAllBoard() {
        List<Board> boardList = boardService.findAll();
        if (boardList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

    @GetMapping("/findBoardById/{idBoard}")
    public ResponseEntity<Board> getBoardById(@PathVariable int idBoard) {
        Board board = boardService.findById(idBoard);
        if (board == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @PostMapping("/createBoard/{idTeam}")
    public ResponseEntity<String> createBoard(@RequestBody Board board,@PathVariable int idTeam) {

        Teams teams = teamService.findById(idTeam);
        if (teams == null) {
            return new ResponseEntity<>("Team not found", HttpStatus.NOT_FOUND);
        }
        board.setTeam(teams);
        boardService.save(board);
        return new ResponseEntity<>("Create Board Success", HttpStatus.OK);
    }
}
