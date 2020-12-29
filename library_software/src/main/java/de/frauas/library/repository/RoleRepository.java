package de.frauas.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.frauas.library.model.Role;

/**
 * RoleRepository defines methods to retrieve role related data from the database.
 * @author Leonard
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long>{

}
