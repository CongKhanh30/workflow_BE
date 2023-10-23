package com.workflow.controller;

import com.workflow.dto.CardDetailResponse;
import com.workflow.dto.MiniCardResponse;
import com.workflow.service.impl.AccountServiceImpl;
import com.workflow.service.impl.CardServiceImpl;
import com.workflow.service.impl.PermissionBoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/c")
public class CardController {
    private final CardServiceImpl cardService;
    private final PermissionBoardServiceImpl permissionBoardService;
    private final AccountServiceImpl accountService;
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable int id){
        CardDetailResponse cardDetailResponse =cardService.findById(id);
        if (cardDetailResponse != null){
                if (!permissionBoardService.isMember(accountService.getCurrentUsername(), cardDetailResponse.getCol().getBoard().getId()))
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No Permission");
            return ResponseEntity.status(HttpStatus.OK).body(cardDetailResponse);
        }
        return ResponseEntity.status(HttpStatus.IM_USED).body("No card found");
    }

    @GetMapping("getAllCardByColId/{colId}")
    public ResponseEntity getByCol(@PathVariable int colId){
        List<MiniCardResponse> miniCardResponses = cardService.getByCol(colId);
        if (miniCardResponses != null){
            return ResponseEntity.status(HttpStatus.OK).body(miniCardResponses);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No card found");
    }

    // viet ham chi tiet card bang id
    @GetMapping("/detail/{id}")
    public ResponseEntity getDetailById(@PathVariable int id){
        CardDetailResponse cardDetailResponse = cardService.findById(id);
        if (cardDetailResponse != null){
            return ResponseEntity.status(HttpStatus.OK).body(cardDetailResponse);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No card found");
    }
}
