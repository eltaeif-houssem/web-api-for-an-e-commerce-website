package com.ecommerce.server;

import com.ecommerce.server.enums.RoleName;
import com.ecommerce.server.model.Role;
import com.ecommerce.server.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName(RoleName.ROLE_CLIENT).isEmpty()) {
				roleRepository.save(Role
						.builder()
						.name(RoleName.ROLE_CLIENT)
						.createdDate(LocalDateTime.now())
						.build());
			}

			if (roleRepository.findByName(RoleName.ROLE_ADMIN).isEmpty()) {
				roleRepository.save(Role
						.builder()
						.name(RoleName.ROLE_ADMIN)
						.createdDate(LocalDateTime.now())
						.build());
			}
		};
	}

}
