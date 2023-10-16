package com.workflow.repository;

import com.workflow.model.Board;
import com.workflow.model.Col;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IColRepo extends JpaRepository<Col, Integer> {
    List<Col> findAllByBoardOrderByPosition(Board board);
}
