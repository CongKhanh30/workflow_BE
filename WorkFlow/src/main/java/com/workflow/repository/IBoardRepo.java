package com.workflow.repository;

import com.workflow.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IBoardRepo extends JpaRepository<Board,Integer> {

    // tim kiem board theo id cua team
    @Query(value = "select * from board where team_id = ?1", nativeQuery = true)
    List<Board> findAllByTeam(int id);
}
