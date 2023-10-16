package com.workflow.repository;

import com.workflow.model.Card;
import com.workflow.model.Col;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ICardRepo extends JpaRepository<Card,Integer> {
    List<Card> findAllByCol(Col col);
}
