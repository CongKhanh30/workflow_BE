package com.workflow.repository;

import com.workflow.model.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IListRepo extends JpaRepository<List, Integer> {
}
