package com.workflow.repository;

import com.workflow.model.Board;
import com.workflow.model.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface IListRepo extends JpaRepository<List, Integer> {
    Set<List> findAllByBoardOrderByPosition(Board board);
}
