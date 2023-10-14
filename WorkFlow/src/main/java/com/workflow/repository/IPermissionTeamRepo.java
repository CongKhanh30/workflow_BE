package com.workflow.repository;

import com.workflow.model.PermissionTeam;
import com.workflow.model.Teams;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPermissionTeamRepo extends JpaRepository<PermissionTeam, Integer> {
    List<PermissionTeam> findAllByAccount_Username(String username);
    List<PermissionTeam> findAllByTeams(Teams teams);
    PermissionTeam findByAccount_UsernameAndTeamsId(String username, int teamId);
}
