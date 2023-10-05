package com.workflow.repository;

import com.workflow.model.Permission_Team;
import com.workflow.model.Teams;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPermissionTeamRepo extends JpaRepository<Permission_Team, Integer> {
    List<Permission_Team> findAllByAccount_Username(String username);
    List<Permission_Team> findAllByTeams(Teams teams);
}
