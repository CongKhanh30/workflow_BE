package com.workflow.controller;

import com.workflow.dto.RenameListRequest;
import com.workflow.model.List;
import com.workflow.service.impl.ListServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping("/list")
@RequiredArgsConstructor
public class ListController {
    private final ListServiceImpl listService;

    @GetMapping("/{boardId}")
    public ResponseEntity getByBoard(@PathVariable int boardId){
        Set<List> lists = listService.getList(boardId);
        if (lists != null) return ResponseEntity.ok(lists);
        return new  ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/rn")
    public ResponseEntity rename(@RequestBody RenameListRequest renameListRequest){
        if(listService.rename(renameListRequest.getNewName(), renameListRequest.getListId()))
            return ResponseEntity.ok("succeed");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("List not found");
    }
}
