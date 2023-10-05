package com.workflow.repository;

import com.workflow.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBoardRepo extends JpaRepository<Board,Integer> {
}
