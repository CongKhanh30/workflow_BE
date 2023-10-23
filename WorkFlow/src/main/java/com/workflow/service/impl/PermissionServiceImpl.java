package com.workflow.service.impl;

import com.workflow.model.Permission;
import com.workflow.repository.IPermissionRepo;
import com.workflow.service.IPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements IPermissionService {
    private final IPermissionRepo permissionRepo;

    @Override
    public List<Permission> findAll() {
        return permissionRepo.findAll();
    }

    @Override
    public void save(Permission permission) {
    }

    @Override
    public void edit(Permission permission) {
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Permission findByTeamId(int id) {
        return null;
    }
}
