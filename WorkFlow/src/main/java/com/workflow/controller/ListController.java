package com.workflow.controller;

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
}
