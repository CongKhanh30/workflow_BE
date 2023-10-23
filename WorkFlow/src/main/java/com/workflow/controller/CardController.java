package com.workflow.controller;

import com.workflow.dto.CardDetailResponse;
import com.workflow.dto.CreateCardReq;
import com.workflow.dto.EditCardReq;
import com.workflow.model.Card;
import com.workflow.model.Col;
import com.workflow.repository.ICardRepo;
import com.workflow.repository.IColRepo;
import com.workflow.service.impl.AccountServiceImpl;
import com.workflow.service.impl.CardServiceImpl;
import com.workflow.service.impl.PermissionBoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/c")
public class CardController {
    private final CardServiceImpl cardService;
    private final PermissionBoardServiceImpl permissionBoardService;
    private final AccountServiceImpl accountService;
    private final IColRepo colRepo;
    private final ICardRepo cardRepo;
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        CardDetailResponse cardDetailResponse =cardService.findById(id);
        if (cardDetailResponse != null){
                if (!permissionBoardService.isMember(accountService.getCurrentUsername(), cardDetailResponse.getCol().getBoard().getId()))
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No Permission");
            return ResponseEntity.status(HttpStatus.OK).body(cardDetailResponse);
        }
        return ResponseEntity.status(HttpStatus.IM_USED).body("No card found");
    }
    @PutMapping("/add")
    public ResponseEntity<?> addMember (String username, int cardId){
        Card card = cardService.getById(cardId);
        if (card == null) return  ResponseEntity.status(HttpStatus.ACCEPTED).body("No card found");
        if (!permissionBoardService.isMember(accountService.getCurrentUsername(),card.getCol().getBoard().getId()))
            return ResponseEntity.status(403).body("You're not in this board");
        cardService.addAccount(cardId,username);
        return ResponseEntity.status(200).body("Succeed");
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCard(@RequestBody CreateCardReq createCardReq){
        Optional<Col> colOtp = colRepo.findById(createCardReq.getColId());
        if (colOtp.isPresent()) {
            if(permissionBoardService.isMember(accountService.getCurrentUsername(), colOtp.get().getBoard().getId())){
                cardService.createCard(createCardReq);
                return ResponseEntity.status(HttpStatus.CREATED).body("succeed");
            }
            return ResponseEntity.status(403).body("Don't have permission");
        }
        return ResponseEntity.status(HttpStatus.GONE).body("Col not found");
    }
    @DeleteMapping("/remove/{cardId}")
    public ResponseEntity<?> remove(@PathVariable int cardId){
        Card card = cardService.getById(cardId);
        if (card == null) return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("No card found");
        if (!permissionBoardService.isMember(accountService.getCurrentUsername(),card.getCol().getBoard().getId()))
            return ResponseEntity.status(403).body("No permission");
        cardService.deleteCard(cardId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("succeed");
    }
    @PutMapping("/edit")
    public ResponseEntity<?> editCard(@RequestBody EditCardReq editCardReq){
        if(!colRepo.existsById(editCardReq.getColId()))
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Col not found");
        if(!cardRepo.existsById(editCardReq.getCardId()))
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Card not found");
        if(!permissionBoardService.isMember(
                accountService.getCurrentUsername(),
                cardService.getById(editCardReq.getCardId()).getCol().getBoard().getId()))
            return ResponseEntity.status(403).body("No permission");
        cardService.editCard(editCardReq);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("succeed");
    }

}
