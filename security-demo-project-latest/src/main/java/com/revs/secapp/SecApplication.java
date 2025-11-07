package com.revs.secapp;

import com.revs.secapp.role.Role;
import com.revs.secapp.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class SecApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(SecApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Optional<Role> role = this.roleRepository.findByName("ROLE_USER");

		if(!role.isPresent()) {
			Role newRole = Role.builder()
				.name("ROLE_USER")
				.createdBy("APP")
				.build();
			this.roleRepository.save(newRole);
		}
	}
}
