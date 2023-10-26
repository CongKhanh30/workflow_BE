package com.workflow.controller;

import com.workflow.dto.ColResponse;
import com.workflow.dto.RenameColRequest;
import com.workflow.model.Board;
import com.workflow.repository.IBoardRepo;
import com.workflow.service.impl.AccountServiceImpl;
import com.workflow.service.impl.ColServiceImpl;
import com.workflow.service.impl.PermissionBoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/col")
@RequiredArgsConstructor
public class ColController {
    private final ColServiceImpl colService;
    private final IBoardRepo boardRepo;
    private final PermissionBoardServiceImpl permissionBoardService;
    private final AccountServiceImpl accountService;

    @GetMapping("/{boardId}")
    public ResponseEntity<List<ColResponse>> getByBoard(@PathVariable int boardId){
        List<ColResponse> cols = colService.getCol(boardId);
        if (cols != null) return ResponseEntity.ok(cols);
        return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/rn")
    public ResponseEntity<String> rename(@RequestBody RenameColRequest renameColRequest){
        if(colService.rename(renameColRequest.getNewName(), renameColRequest.getColId()))
            return ResponseEntity.ok("succeed");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Col not found");
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCol(String name, int boardId){
        Optional<Board> boardOtp = boardRepo.findById(boardId);
        if (!permissionBoardService.isMember(accountService.getCurrentUsername(), boardId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Don't have permission");
        if (boardOtp.isPresent()){
            colService.create(name,boardId);
            return ResponseEntity.ok("succeed");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("fail");
    }

    @DeleteMapping("/delete/{colId}")
    public ResponseEntity<String> deleteCol(@PathVariable int colId){
        if (colService.delete(colId))
            return ResponseEntity.ok("succeed");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Col not found");
    }

}
