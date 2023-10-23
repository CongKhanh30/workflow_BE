package com.workflow.service.impl;

import com.workflow.dto.CardDetailResponse;
import com.workflow.dto.MiniAccount;
import com.workflow.dto.MiniCardResponse;
import com.workflow.model.Card;
import com.workflow.model.Col;
import com.workflow.repository.ICardRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl {
    private final ICardRepo cardRepo;

    public List<Card> getByCol(Col col) {

        return cardRepo.findAllByCol(col);
    }
    public List<MiniCardResponse> getByCol(int colId){
        return cardRepo.findAllByColId(colId).stream().map(this::buildMiniCard).collect(Collectors.toList());
    }

    public MiniCardResponse buildMiniCard(Card card){
        return new MiniCardResponse(card.getId(),card.getTitle(),card.getDueDate());
    }
    public CardDetailResponse findById(int id){
        Optional<Card> cardOtp = cardRepo.findById(id);
        return cardOtp.map(this::buildResponse).orElse(null);
    }
    private CardDetailResponse buildResponse(Card card){
        CardDetailResponse cardDetailResponse = new CardDetailResponse();
        cardDetailResponse.setAccounts(card.getAccounts().stream().map(MiniAccount::builder).collect(Collectors.toList()));
        cardDetailResponse.setId(card.getId());
        cardDetailResponse.setTitle(card.getTitle());
        cardDetailResponse.setDescription(card.getDescription());
        cardDetailResponse.setDueDate(card.getDueDate());
        cardDetailResponse.setCol(card.getCol());
        return cardDetailResponse;
    }
}
