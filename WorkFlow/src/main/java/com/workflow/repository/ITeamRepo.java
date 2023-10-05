package com.workflow.repository;

import com.workflow.model.Teams;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITeamRepo extends JpaRepository<Teams,Integer> {
    Teams getTeamsById(int id);
}
