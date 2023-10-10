package com.workflow.repository;

import com.workflow.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPermissionRepo extends JpaRepository<Permission, Integer> {
}
