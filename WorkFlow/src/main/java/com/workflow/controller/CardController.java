package com.workflow.controller;

import com.workflow.dto.CardDetailResponse;
import com.workflow.model.Card;
import com.workflow.service.impl.CardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/c")
public class CardController {
    private final CardServiceImpl cardService;
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable int id){
        CardDetailResponse cardDetailResponse =cardService.findById(id);
        if (cardDetailResponse != null)
            return ResponseEntity.status(HttpStatus.OK).body(cardDetailResponse);
        return ResponseEntity.status(HttpStatus.IM_USED).body("No card found");
    }
}
