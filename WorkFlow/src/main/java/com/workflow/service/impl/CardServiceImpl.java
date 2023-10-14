package com.workflow.service.impl;

import com.workflow.dto.MiniCardResponse;
import com.workflow.model.Card;
import com.workflow.model.Col;
import com.workflow.repository.ICardRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardServiceImpl {
    private final ICardRepo cardRepo;

    public List<Card> getByCol(Col col) {
        return cardRepo.findAllByCol(col);
    }
    public MiniCardResponse buildMiniCard(Card card){
        return new MiniCardResponse(card.getId(),card.getTitle(),card.getDueDate());
    }
}
