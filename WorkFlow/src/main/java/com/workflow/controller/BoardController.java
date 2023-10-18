package com.workflow.controller;

import com.workflow.dto.BoardResponse;
import com.workflow.model.Board;
import com.workflow.model.Teams;
import com.workflow.service.impl.BoardServiceImpl;
import com.workflow.service.impl.TeamServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {


    private final BoardServiceImpl boardService;

    private final TeamServiceImpl teamService;

    @GetMapping("/getAllByIdTeam/{idTeam}")
    public ResponseEntity<List<BoardResponse>> getAllBoard(@PathVariable int idTeam) {

        List<BoardResponse> boards = boardService.findAllByTeam(idTeam);
        if (boards.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Board>> getAllBoard() {
        List<Board> boards = boardService.findAll();
        if (boards.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @PostMapping("/createBoard/{idTeam}")
    public ResponseEntity<String> createBoard(@RequestBody Board board,@PathVariable int idTeam) {

        Teams teams = teamService.findByTeamId(idTeam);
        if (teams == null) {
            return new ResponseEntity<>("Team not found", HttpStatus.NOT_FOUND);
        }
        board.setTeam(teams);
        boardService.save(board);
        return new ResponseEntity<>("Create Board Success", HttpStatus.OK);
    }

    @GetMapping("/removeBoard/{id}")
    public ResponseEntity<String> removeBoard(@PathVariable int id) {
        Board boardRemove = boardService.findByTeamId(id);
        if (boardRemove == null) {
            return new ResponseEntity<>("Board not found", HttpStatus.NOT_FOUND);
        }
        boardService.delete(id);
        return new ResponseEntity<>("Delete Board Success", HttpStatus.OK);
    }

    @PostMapping("/editNameBoard/{id}")
    public ResponseEntity<String> editNameBoard(@RequestBody Board board,@PathVariable int id) {
        Board boardEdit = boardService.findByTeamId(id);
        if (boardEdit == null) {
            return new ResponseEntity<>("Board not found", HttpStatus.NOT_FOUND);
        }
        boardEdit.setName(board.getName());
        boardService.edit(boardEdit);

        return new ResponseEntity<>("Edit Board Success", HttpStatus.OK);
    }

    @GetMapping("/getBoardById/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable int id) {
        Board board = boardService.findByTeamId(id);
        if (board == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(board, HttpStatus.OK);
    }
}
