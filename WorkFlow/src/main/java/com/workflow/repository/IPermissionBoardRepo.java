package com.workflow.repository;

import com.workflow.model.PermissionBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPermissionBoardRepo extends JpaRepository<PermissionBoard, Integer> {
    PermissionBoard findByAccount_UsernameAndBoard_Id(String username, int boardId);
    List<PermissionBoard> findAllByBoard_Id(int boardId);
}
