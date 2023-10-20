package com.workflow.controller;

import com.workflow.dto.CardDetailResponse;
import com.workflow.model.Account;
import com.workflow.model.Card;
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
    @PutMapping("/add")
    public ResponseEntity addMember (String username, int cardId){
        Card card = cardService.getById(cardId);
        if (card == null) return  ResponseEntity.status(HttpStatus.ACCEPTED).body("No card found");
        if (!permissionBoardService.isMember(accountService.getCurrentUsername(),card.getCol().getBoard().getId()))
            return ResponseEntity.status(403).body("You're not in this board");
        List<Account> accounts = card.getAccounts();
        accounts.add(accountService.findByUsername(username));
        return ResponseEntity.status(200).body("Succeed");
    }
}
