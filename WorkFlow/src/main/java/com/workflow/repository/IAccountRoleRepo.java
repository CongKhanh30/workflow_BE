package com.workflow.repository;

import com.workflow.model.Account;
import com.workflow.model.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAccountRoleRepo extends JpaRepository<AccountRole, Integer> {
    List<AccountRole> findAllByAccount(Account account);
}
