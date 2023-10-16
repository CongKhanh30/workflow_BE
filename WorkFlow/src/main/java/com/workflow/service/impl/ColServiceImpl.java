package com.workflow.service.impl;

import com.workflow.dto.ColResponse;
import com.workflow.dto.MiniCardResponse;
import com.workflow.model.Board;
import com.workflow.model.Col;
import com.workflow.repository.IBoardRepo;
import com.workflow.repository.IColRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ColServiceImpl {
    private final IColRepo colRepo;
    private final PermissionTeamServiceImpl permissionTeamService;
    private final IBoardRepo boardRepo;
    private final CardServiceImpl cardService;
    private final AccountServiceImpl accountService;


    public List<ColResponse> getCol(int boardId){
        Optional<Board> boardOpt = boardRepo.findById(boardId);
        if (boardOpt.isPresent()) {
            List<Col> cols = colRepo.findAllByBoardOrderByPosition(boardOpt.get());
            return cols.stream()
                    .map(l -> new ColResponse(l.getId(), l.getPosition(), l.getName(), buildColMini(l)))
                    .sorted(Comparator.comparing(ColResponse::getPosition))
                    .collect(Collectors.toList());
        }
        return null;
    }
    List<MiniCardResponse> buildColMini(Col col){
        return cardService.getByCol(col).stream().map(cardService::buildMiniCard).collect(Collectors.toList());
    }
    public boolean rename(String name, int colId){
        Optional<Col> colOtp = colRepo.findById(colId);
        if (colOtp.isPresent()
                && permissionTeamService.isMember(accountService.getCurrentUsername(), colOtp.get().getBoard().getTeam().getId())){
            Col col = colOtp.get();
            col.setName(name);
            colRepo.save(col);
            return true;
        }
        return false;
    }

}
