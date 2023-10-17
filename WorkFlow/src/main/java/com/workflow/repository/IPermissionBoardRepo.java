package com.workflow.repository;

import com.workflow.model.PermissionBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPermissionBoardRepo extends JpaRepository<PermissionBoard, Integer> {
    PermissionBoard findByAccount_UsernameAndBoard_Id(String username, int boardId);
}
