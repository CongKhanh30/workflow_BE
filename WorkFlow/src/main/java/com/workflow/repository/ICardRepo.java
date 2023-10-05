package com.workflow.repository;

import com.workflow.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICardRepo extends JpaRepository<Card,Integer> {
}
