package com.workflow.service.impl;

import com.workflow.dto.*;
import com.workflow.model.Account;
import com.workflow.model.Card;
import com.workflow.model.Col;
import com.workflow.repository.ICardRepo;
import com.workflow.repository.IColRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl {
    private final ICardRepo cardRepo;
    private final IColRepo colRepo;
    private final AccountServiceImpl accountService;

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
    public Card getById(int id){ return cardRepo.findById(id).orElse(null);}

    public void createCard(CreateCardReq createCardReq) {
        Card card = new Card();
        card.setTitle(createCardReq.getTitle());
        card.setDescription(createCardReq.getDescription());
        card.setDueDate(createCardReq.getDueDate());
        card.setCol(colRepo.findById(createCardReq.getColId()).orElse(null));
        cardRepo.save(card);
    }
    @Transactional
    public void deleteCard(int cardId){
        Card card = cardRepo.findById(cardId).orElse(null);
        if (card != null){
            card.getAccounts().clear();
            cardRepo.delete(card);
        }
    }
    public void addAccount(int cardId, String username){
        Optional<Card> cardOtp = cardRepo.findById(cardId);
        if (cardOtp.isPresent()){
            List<Account> accounts = cardOtp.get().getAccounts();
            accounts.add(accountService.findByUsername(username));
            cardOtp.get().setAccounts(accounts);
            cardRepo.save(cardOtp.get());
        }
    }

    public void editCard(EditCardReq editCardReq) {
        Optional<Card> cardOtp = cardRepo.findById(editCardReq.getId());
        Optional<Col> colOtp = colRepo.findById(editCardReq.getColId());
        if (cardOtp.isPresent() && colOtp.isPresent()){
            Card card = cardOtp.get();
            card.setTitle(editCardReq.getTitle());
            card.setDescription(editCardReq.getDescription());
            card.setDueDate(editCardReq.getDueDate());
            card.setCol(colOtp.get());
            cardRepo.save(card);
        }
    }
}
