package com.borgestech.securityrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.borgestech.securityrest.model.RoleModel;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long>{

}
