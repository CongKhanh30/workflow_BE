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
        // optional la 1 kieu du lieu de tranh null pointer exception khi get data tu db
        if (boardOpt.isPresent()) { // neu board ton tai thi get all col cua board do
            List<Col> cols = colRepo.findAllByBoardOrderByPosition(boardOpt.get());
            // findAllByBoardOrderByPosition la ham tuong duong voi cau lenh sql: select * from col where board_id = ? order by position
            return cols.stream()
                    .map(l -> new ColResponse(l.getId(), l.getPosition(), l.getName(), buildColMini(l)))
                    .sorted(Comparator.comparing(ColResponse::getPosition))
                    .collect(Collectors.toList());
            // tra ve 1 list ColResponse sau khi da sort theo position,collection la 1 interface cua java de xu ly cac collection
            // map la 1 ham de convert tu 1 kieu du lieu sang kieu du lieu khac
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

    public void create(String name, int boardId) {
        Optional<Board> boardOpt = boardRepo.findById(boardId);
        Col col = new Col();
        if (boardOpt.isPresent()){
            col.setName(name);
            col.setBoard(boardOpt.get());
            col.setPosition(colRepo.findAllByBoardOrderByPosition(boardOpt.get()).size()+1);
            colRepo.save(col);
        }
    }
}
