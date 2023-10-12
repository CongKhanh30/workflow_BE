package com.workflow.service.impl;

import com.workflow.model.Board;
import com.workflow.model.List;
import com.workflow.model.Teams;
import com.workflow.repository.IBoardRepo;
import com.workflow.repository.IListRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ListServiceImpl {
    private final IListRepo listRepo;
    private final PermissionTeamServiceImpl permissionTeamService;
    private final AccountServiceImpl accountService;
    private final IBoardRepo boardRepo;


    public Set<List> getList(int boardId){
        Optional<Board> boardOtp = boardRepo.findById(boardId);
        if (boardOtp.isPresent()){
            Teams teams = boardOtp.get().getTeam();
            if (permissionTeamService.isMember(teams.getId())){
                Set<List> lists = listRepo.findAllByBoardOrderByPosition(boardOtp.get());
                return lists.stream().map(l -> {
                    List list = new List();
                    list.setId(l.getId());
                    list.setName(l.getName());
                    list.setPosition(l.getPosition());
                    return list;
                }).sorted(Comparator.comparing(List::getPosition))
                        .collect(Collectors.toCollection(LinkedHashSet::new));

            }
        }
        return null;
    }
    public boolean rename(String name, int listId){
        Optional<List> listOtp = listRepo.findById(listId);
        if (listOtp.isPresent()
                && permissionTeamService.isMember(listOtp.get().getBoard().getTeam().getId())){
            List list = listOtp.get();
            list.setName(name);
            listRepo.save(list);
            return true;
        }
        return false;
    }
}
