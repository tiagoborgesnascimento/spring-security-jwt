package com.borgestech.securityrest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.borgestech.securityrest.enums.EnumRole;
import com.borgestech.securityrest.model.RoleModel;
import com.borgestech.securityrest.repository.RoleRepository;

@Component
public class ScriptRunner implements CommandLineRunner{

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public void run(String... args) throws Exception {
		roleRepository.save(new RoleModel(EnumRole.ROLE_USER));
		roleRepository.save(new RoleModel(EnumRole.ROLE_ADMIN));
	}

}
