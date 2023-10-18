package com.workflow.service.impl;

import com.workflow.model.Permission;
import com.workflow.repository.IPermissionRepo;
import com.workflow.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    IPermissionRepo permissionRepo;

    @Override
    public List<Permission> findAll() {
       List<Permission> permissionList = (List<Permission>) permissionRepo.findAll();
        return permissionList;
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
