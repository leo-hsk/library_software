package de.frauas.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.frauas.library.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
