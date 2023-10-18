package com.workflow.controller;

import com.workflow.dto.ColResponse;
import com.workflow.dto.RenameColRequest;
import com.workflow.service.impl.ColServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/col")
@RequiredArgsConstructor
public class ColController {
    private final ColServiceImpl colService;

    @GetMapping("/{boardId}")
    public ResponseEntity getByBoard(@PathVariable int boardId){
        List<ColResponse> cols = colService.getCol(boardId);
        if (cols != null) return ResponseEntity.ok(cols);
        return new  ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/rn")
    public ResponseEntity rename(@RequestBody RenameColRequest renameColRequest){
        if(colService.rename(renameColRequest.getNewName(), renameColRequest.getColId()))
            return ResponseEntity.ok("succeed");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Col not found");
    }
}
